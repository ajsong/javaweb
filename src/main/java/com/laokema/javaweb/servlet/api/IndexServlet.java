package com.laokema.javaweb.servlet.api;

import com.laokema.javaweb.model.index.*;
import com.laokema.tool.Common;
import com.laokema.tool.DB;

import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet("/index")
public class IndexServlet extends CoreServlet {
	//首页
	public void index() {
		List<Ad> flashes = _flashes();
		List<Category> categories = _categories();
		List<Coupon> coupons = _coupons();
		List<Goods> recommend = _goods(1); //推荐
		List<Goods> hotsale = _goods(2); //热销
		List<Goods> boutique = _goods(3); //精品
		List<Goods> newgoods = _goods(4); //新品
		List<Goods> discount = _goods(5); //折扣

		request.setAttribute("flashes", flashes);
		request.setAttribute("categories", categories);
		request.setAttribute("coupons", coupons);
		request.setAttribute("recommend", recommend);
		request.setAttribute("hotsale", hotsale);
		request.setAttribute("boutique", boutique);
		request.setAttribute("newgoods", newgoods);
		request.setAttribute("discount", discount);
		Common.success("/api/index.jsp");
	}

	//幻灯广告
	private List<Ad> _flashes() {
		List<Ad> rs = DB.share("ad").where("(begin_time=0 OR begin_time<='"+this.now+"') AND (end_time=0 OR end_time>='"+this.now+"') AND status='1' AND position='flash'")
				.order("sort ASC, id DESC").pagesize(5).select(Ad.class);
		rs = Common.add_domain_deep(rs, "pic");
		return rs;
	}

	//商品分类
	private List<Category> _categories() {
		List<Category> rs = DB.share("goods_category").where("status='1' AND parent_id=0")
				.field("id, name, pic").order("sort ASC, id ASC").cached(60*2).select(Category.class);
		rs = Common.add_domain_deep(rs, "pic");
		return rs;
	}

	//优惠券
	private List<Coupon> _coupons() {
		List<Coupon> rs = DB.share("coupon").where("status='1'").order("id DESC").pagesize(10).select(Coupon.class);
		/*if ($rs) {
			$coupon_mod = m('coupon');
			foreach ($rs as $k=>$g) {
				$rs[$k] = $coupon_mod->get_coupon_info($g);
			}
		}*/
		return rs;
	}

	//商品
	public List<Goods> _goods(int ext_property) {
		return _goods(ext_property, "");
	}
	public List<Goods> _goods(int ext_property, String not_in) {
		int offset = 0;
		int pagesize = 6;
		if (ext_property == 1) {
			offset = this.request.get("offset", 0);
			pagesize = this.request.get("pagesize", 12);
		}
		String where = "";
		if (not_in.length() > 0) where = " AND g.id NOT IN (" + not_in + ")";
		List<Goods> rs = DB.share("goods g").where("g.status=1 AND LOCATE(',"+ext_property+",', CONCAT(',',ext_property,','))>0"+where)
				.order("g.sort ASC, g.id DESC").limit(offset, pagesize).cached(60*2).field("g.*, 0.0 as grade_price").select(Goods.class);
		/*if (rs != null) {
			foreach ($rs as $k=>$g) {
				unset($rs[$k]->content);
				if (in_array('grade', $this->function)) {
					if ($this->member_id) {
						$rs[$k]->grade_price = floatval(SQL::share('goods_grade_price')->where("goods_id='{$g->id}' AND grade_id='{$this->member_grade_id}'")->value('price'));
					} else {
						$grade_id = intval(SQL::share('goods_grade_price')->where("goods_id='{$g->id}'")->value('MIN(grade_id)'));
						$rs[$k]->grade_price = floatval(SQL::share('goods_grade_price')->where("goods_id='{$g->id}' AND grade_id='{$grade_id}'")->value('price'));
					}
				}
			}
		}*/
		//$rs = $this->goods_mod->set_min_prices($rs);
		rs = Common.add_domain_deep(rs, "pic");
		return rs;
	}
}
