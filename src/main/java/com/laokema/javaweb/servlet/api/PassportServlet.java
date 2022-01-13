package com.laokema.javaweb.servlet.api;

import com.alibaba.fastjson.JSON;
import com.laokema.javaweb.model.index.Member;
import com.laokema.tool.Common;
import com.laokema.tool.DB;

import javax.servlet.annotation.*;
import java.util.*;

@WebServlet(name = "PassportServlet", value = "/passport/*")
public class PassportServlet extends CoreServlet {

	public void login() {
		this._clearsession();
		if (!Common.isPost()) {
			Common.success("ok");
		}
		String mobile = this.request.get("mobile");
		String password = this.request.get("password");
		String udid = this.request.get("udid");
		Member member = null;
		//String openid = this.request.get("openid"); //增加判断$_GET['openid']为了区分是否主动登录
		if (mobile.length() == 0) Common.error("手机号码不能为空");
		if (password.length() == 0) Common.error("密码不能为空");
		member = DB.share("member").where("name|mobile", mobile, mobile).find(Member.class);
		if (member == null) {
			Common.error("账号不存在");
			return;
		}
		String crypt_password = Common.crypt_password(password, member.salt);
		if (!crypt_password.equals(member.password)) {
			Common.error("账号或密码错误", -2);
			return;
		}
		if (member.status == 1) {
			//推送强制下线通知
			/*if (strlen($member->udid) && $member->udid!=$udid && $push_type!='nopush') {
				$push = p('push', $push_type);
				$push->send($member->udid, '账号已在其他设备登录', array('action'=>'login', 'state'=>-100));
			}*/

			Map<String, Object> data = new HashMap<>();
			if (udid.length() > 0) {
				//清除之前登录过有相同udid的账号的udid
				DB.share("member").where("udid=?", udid).update("udid", "");
			}
			data.put("udid", udid);

			//环信登录需要原始密码
			if (password.length() > 0) {
				data.put("origin_password", password);
			}
			data.put("last_time", this.now);
			data.put("last_ip", this.ip);
			data.put("logins", "+1");
			DB.share("member").where(member.id).update(data);
			this._after_passport(member, true, false);
		} else {
			Common.error("账号已经被冻结", -1);
		}
	}

	//处理登录或注册后的操作
	private void  _after_passport(Member member, boolean is_login, boolean is_register) {
		if (member == null) {
			Common.error("member is null");
			return;
		}

		//生成签名
		if (this.is_wx && is_login) {
			this.sign = member.sign;
		} else {
			//不理是否微信登录都更新一下sign会好点
			this.sign = Common.generate_sign();
			member.sign = this.sign;
			DB.share("member").where(member.id).update("sign", this.sign);
		}

		if (member.avatar != null && member.avatar.length() > 0) {
			member.avatar = Common.add_domain(member.avatar);
		} else {
			member.avatar = Common.add_domain("/images/avatar.png");
		}
		member.format_reg_time = Common.date("Y-m-d", member.reg_time);

		//总财富
		member.total_price = member.money + member.commission;

		//登录与注册都需要记录openid
		/*$openid = $this->request->session('openid');
		if (strlen($openid)) {
			if (!SQL::share('member_thirdparty')->where("mark='{$openid}'")->exist()) {
				SQL::share('member_thirdparty')->insert(array('member_id'=>$member->id, 'type'=>'wechat', 'mark'=>$openid));
			}
			$_SESSION['weixin_authed'] = 1;
		}*/

		//更新在线
		DB.share("member").where(member.id).update("session_id", this.session_id);

		//if ($is_login) $this->_check_login();

		//更新购物车
		DB.share("cart").where("session_id=?", this.session_id).update("member_id", member.id);

		//是否已绑定手机(账号)
		member.is_mobile = (member.name == null || member.name.length() == 0) ? 0 : 1;

		if (is_register) {
			//设置为最低等级
			if (Arrays.asList(this.function).contains("grade")) {
				/*$grade = SQL::share('grade')->where("status=1")->sort('score ASC, id ASC')->row('id, score');
				if ($grade) {
					SQL::share('member')->where($member->id)->update(array('grade_id'=>$grade->id, 'grade_score'=>$grade->score, 'grade_time'=>time()));
					$member->grade_id = $grade->id;
					$member->grade_score = $grade->score;
				}*/
			}
		}

		//获取当前等级的下个等级
		if (Arrays.asList(this.function).contains("grade")) {
			/*$score = 0;
			$grade = SQL::share('grade')->where("status=1 AND id>'{$member->grade_id}'")->sort('score ASC, id ASC')->row('score');
			if ($grade) $score = intval($grade->score);
			if ($score == 0) {
				$score = intval(SQL::share('grade')->where($member->grade_id)->value('score'));
			}
			$member->next_score = "{$score}";
			$grade = SQL::share('grade')->where($member->grade_id)->row();
			$member->grade = $grade;*/
		}

		member = this.get_member_from_sign(this.sign);
		member = Common.add_domain_deep(member, "avatar");
		member.password = null;
		member.salt = null;
		member.withdraw_password = null;
		member.withdraw_salt = null;

		this.removeSession("sms_code");
		this.removeSession("sms_mobile");
		this.removeSession("check_mobile_code");
		this.removeSession("check_mobile_mobile");
		this.removeSession("forget_sms_code");
		this.removeSession("forget_sms_mobile");

		int remember = this.request.get("remember", 0);
		if (is_login && remember != 0) {
			this.cookieAccount("member_token", member.name.length() > 0 ? member.name : member.mobile);
		}

		//微信端跳转回之前查看的页面
		if (this.is_wx && !this.is_mini && is_login && Common.isWeb()) {
			String url = this.request.session("weixin_url", "/");
			Common.location(url);
			return;
		}

		this.setSession("gourl", this.request.session("api_gourl"));
		this.removeSession("api_gourl");
		Common.success(member);
	}

	//清除session
	private void  _clearsession() {
		this.removeSession("member");
		this.member_id = 0;
	}

}
