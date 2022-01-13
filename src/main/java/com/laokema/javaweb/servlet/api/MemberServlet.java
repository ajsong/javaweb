package com.laokema.javaweb.servlet.api;

import com.laokema.javaweb.model.index.Member;
import com.laokema.tool.*;

import javax.servlet.annotation.*;

@WebServlet(name = "MemberServlet", value = "/member/*")
public class MemberServlet extends CoreServlet {

	public void index() {
		int not_pay = 0, not_shipping = 0, not_confirm = 0, not_comment = 0, notify = 0, coupon_count = 0;
		Member member = null;
		if (this.member_id > 0) {
			not_pay = _get_status_order_count(0);
			not_shipping = _get_status_order_count(1);
			not_confirm = _get_status_order_count(2);
			not_comment = _get_status_order_count(3);
			notify = _get_message_count();
			coupon_count = _get_coupon_count();
			//获取会员所有信息
			member = this.get_member_from_sign(this.sign);
			if (member != null) {
				//获取当前等级的下个等级
				int score = 0;
				Member row = DB.share("grade").where("status=1 AND id>'"+member.grade_id+"'").order("sort ASC, id ASC").field("score").find(Member.class);
				if (row != null) score = row.get("grade_score", Integer.class);
				if (score == 0) {
					score = DB.share("grade").where(member.grade_id).value("score", Member.class, Integer.class);
				}
				member.next_score = score;
				this.setSession("member", member);
			}
		}
		int cart_total = _get_cart_count();
		request.setAttribute("member_id", this.member_id);
		request.setAttribute("cart_total", cart_total);
		request.setAttribute("coupon_count", coupon_count);
		request.setAttribute("not_pay", not_pay);
		request.setAttribute("not_shipping", not_shipping);
		request.setAttribute("not_confirm", not_confirm);
		request.setAttribute("not_comment", not_comment);
		request.setAttribute("notify", notify);
		request.setAttribute("member", member);
		Common.success("/api/member.jsp");
	}

	//获取购物车商品总数
	public int _get_cart_count() {
		String where;
		if (this.member_id > 0) {
			where = " AND (member_id='"+this.member_id+"' OR session_id='"+this.session_id+"')";
		} else {
			where = " AND session_id='"+this.session_id+"'";
		}
		return DB.share("cart").where(where).sum("quantity");
	}

	//获取指定状态未读订单总数
	public int _get_status_order_count(int status) {
		return DB.share("order").where("status='"+status+"' AND member_id='"+this.member_id+"' AND readed=0").count();
	}

	//获取未读站内信息总数
	public int _get_message_count() {
		return DB.share("message").where("member_id='"+this.member_id+"' AND readed=0").count();
	}

	//获取优惠券总数
	public int _get_coupon_count() {
		return DB.share("coupon_sn").where("status='1' AND member_id='"+this.member_id+"' AND member_id>0").count();
	}

	//设置
	public void set() {
		Common.success("ok", "/api/member.set.jsp");
	}
}
