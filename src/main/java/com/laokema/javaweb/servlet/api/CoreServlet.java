package com.laokema.javaweb.servlet.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.laokema.javaweb.kernel.KernelServlet;
import com.laokema.javaweb.model.index.Member;
import com.laokema.tool.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "CoreServlet", value = "/core/*")
public class CoreServlet extends KernelServlet {
	public Integer edition;
	public String[] function;
	public Integer member_id;
	public String member_name;
	public Integer shop_id;
	public String sign;
	public Member memberObj;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);

		if (client == null) {
			client = DB.share("client").cached(60*60*24*3).find(Client.class);
		}
		this.edition = client.edition;
		String function = client.function;
		if (function != null && function.length() > 0) this.function = function.split(",");
		request.setAttribute("edition", this.edition);
		request.setAttribute("function", this.function);

		setConfigs();
		request.setAttribute("config", this.configs);

		this.member_id = 0;
		this.member_name = "";
		this.shop_id = 0;
		this.sign = this.request.get("sign");
		if (this.sign == null || this.sign.length() == 0) this.sign = this.headers.get("sign");
		if (this.sign == null || this.sign.length() == 0) this.sign = this.headers.get("Sign");
		if (this.sign == null || this.sign.length() == 0) this.sign = this.headers.get("token");
		if (this.sign == null || this.sign.length() == 0) this.sign = this.headers.get("Token");
		if (this.sign == null) this.sign = "";
		if (this.sign.length() > 0) this._check_login();

		Member member = this.getSession("member", Member.class);
		if (member != null) {
			this.member_id = member.id;
			this.member_name = member.name;
			this.shop_id = member.shop_id;
			this.sign = member.sign;
		}

		if (this.member_id <= 0) {
			JSONObject not_check_login = Common.get_json_property("not_check_login");
			if ( this.is_wap && !not_check_login.isEmpty() && not_check_login.getJSONObject("wap") != null && !not_check_login.getJSONObject("wap").isEmpty() ) {
				JSONObject obj = not_check_login.getJSONObject("wap");
				JSONObject global = not_check_login.getJSONObject("global");
				if ( global != null && !global.isEmpty() ) {
					for (String key : global.keySet()) obj.put(key, global.get(key));
				}
				JSONArray param = obj.getJSONArray(this.app);
				if ( param == null || param.isEmpty() ) {
					if (!this.check_login()) return;
				} else {
					if ( !param.contains("*") && !param.contains(this.act) ) {
						if (!this.check_login()) return;
					} else if ( this.headers.get("Authorization") != null && this.headers.get("Authorization").length() > 0 ) {
						if (!this.check_login()) return;
					}
				}
			}
			if ( this.is_web && !not_check_login.isEmpty() && not_check_login.getJSONObject("web") != null && !not_check_login.getJSONObject("web").isEmpty() ) {
				JSONObject obj = not_check_login.getJSONObject("web");
				JSONObject global = not_check_login.getJSONObject("global");
				if ( global != null && !global.isEmpty() ) {
					for (String key : global.keySet()) obj.put(key, global.get(key));
				}
				JSONArray param = obj.getJSONArray(this.app);
				if ( param == null || param.isEmpty() ) {
					if (!this.check_login()) return;
				} else {
					if ( !param.contains("*") && !param.contains(this.act) ) {
						if (!this.check_login()) return;
					} else if ( this.headers.get("Authorization") != null && this.headers.get("Authorization").length() > 0 ) {
						if (!this.check_login()) return;
					}
				}
			}
			if ( this.is_mini && !not_check_login.isEmpty() && not_check_login.getJSONObject("mini") != null && !not_check_login.getJSONObject("mini").isEmpty() ) {
				JSONObject obj = not_check_login.getJSONObject("mini");
				JSONObject global = not_check_login.getJSONObject("global");
				if ( global != null && !global.isEmpty() ) {
					for (String key : global.keySet()) obj.put(key, global.get(key));
				}
				JSONArray param = obj.getJSONArray(this.app);
				if ( param == null || param.isEmpty() ) {
					if (!this.check_login()) return;
				} else {
					if ( !param.contains("*") && !param.contains(this.act) ) {
						if (!this.check_login()) return;
					} else if ( this.headers.get("Authorization") != null && this.headers.get("Authorization").length() > 0 ) {
						if (!this.check_login()) return;
					}
				}
			}
			if ( !not_check_login.isEmpty() && not_check_login.getJSONObject("global") != null && !not_check_login.getJSONObject("global").isEmpty() ) {
				JSONArray param = not_check_login.getJSONObject("global").getJSONArray(this.app);
				if ( param == null || param.isEmpty() ) {
					if (!this.check_login()) return;
				} else {
					if ( !param.contains("*") && !param.contains(this.act) ) {
						if (!this.check_login()) return;
					} else if ( this.headers.get("Authorization") != null && this.headers.get("Authorization").length() > 0 ) {
						if (!this.check_login()) return;
					}
				}
			}
		}

		this.doMethod();
	}

	//get member info from sign
	public Member get_member_from_sign(String sign) {
		return get_member_from_sign(sign, false);
	}
	public Member get_member_from_sign(String sign, boolean is_session) {
		if (sign == null || sign.length() == 0) return null;
		if (this.memberObj == null || is_session) {
			Member member = DB.share("member").where("sign='" + sign + "'").field("*, 0 as shop_id, null as shop, null as grade").find(Member.class);
			if (member == null) {
				member = (Member) this.getSession("member");
				if (member == null) {
					if (is_session) {
						Common.error("该账号已在其他设备登录", -9);
					}
					return null;
				}
			}
			if (Arrays.asList(this.function).contains("shop")) {
				Member shop = DB.share("shop s").left("member m", "s.member_id=m.id").where("m.id='" + member.id + "'").field("s.*").find(Member.class);
				if (shop != null) {
					member.shop_id = shop.id;
					member.shop = shop;
				}
			}
			if (Arrays.asList(this.function).contains("grade")) {
				Member grade = DB.share("grade").where(member.grade_id).find(Member.class);
				if (grade != null) {
					member.grade = grade;
				}
			}
			/*$thirdparty = SQL::share('member_thirdparty')->where($member->id)->find();
			if ($thirdparty) {
				foreach ($thirdparty as $t) {
					$type = "{$t->type}_openid";
					$member->{$type} = $t->mark;
					if ($t->type=='wechat') $member->openid = $t->mark;
				}
			}*/
			this.member_id = member.id;
			this.member_name = member.name;
			this.shop_id = member.shop_id;
			this.sign = member.sign;
			member.total_price = member.money + member.commission; //总财富
			member = Common.add_domain_deep(member, new String[]{"avatar", "pic"});
			member.origin_password = null;
			member.salt = null;
			member.withdraw_salt = null;
			this.setSession("member", member);
			this.memberObj = member;
		} else {
			Member member = this.memberObj;
			this.member_id = member.id;
			this.member_name = member.name;
			this.shop_id = member.shop_id;
			this.sign = member.sign;
		}
		return this.memberObj;
	}

	//是否登录
	public boolean _check_login() {
		Member member = this.getSession("member", Member.class);
		if (member != null && member.id > 0 && this.sign.length() == 0) {
			return this.get_member_from_sign(member.sign, true) != null;
		} else if (this.sign.length() > 0) {
			return this.get_member_from_sign(this.sign) != null;
		} else if (this.getCookie("member_name") != null && this.getCookie("member_token") != null) {
			member = this.cookieAccount("member_token", this.getCookie("member_name"), this.getCookie("member_token"), "sign");
			if (member != null) return this.get_member_from_sign(member.sign) != null;
		} else if (this.headers.get("Authorization") != null && this.headers.get("Authorization").length() > 0) {
			if (this.headers.get("Authorization").toLowerCase().contains("basic")) {
				String sign = Common.base64_decode(this.headers.get("Authorization").substring(6));
				if (sign.length() > 0) return this.get_member_from_sign(sign) != null;
			}
		}
		return false;
	}

	//对是否登录函数的封装，如果登录了，则返回true，
	//否则，返回错误信息：-100，APP需检查此返回值，判断是否需要重新登录
	public boolean check_login(){
		if (!this._check_login()) {
			String queryString = this.servletRequest.getQueryString();
			queryString = (queryString != null && queryString.length() > 0) ? "?" + queryString : "";
			this.setSession("api_gourl", this.servletRequest.getRequestURI() + queryString);
			Common.error("请登录", -100);
			return false;
		}
		return true;
	}

}
