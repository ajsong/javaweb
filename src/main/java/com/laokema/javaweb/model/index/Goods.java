package com.laokema.javaweb.model.index;

import com.laokema.javaweb.model.BaseModel;

public class Goods extends BaseModel {

	private Integer id;
	private Integer member_id;
	private String name;
	private String model;
	private String pic;
	private String ad_pic;
	private String poster_pic;
	private String description;
	private Integer type;
	private String keywords;
	private Double price;
	private Double market_price;
	private Double cost_price;
	private Double promote_price;
	private Integer promote_begin_time;
	private Integer promote_end_time;
	private Double groupbuy_price;
	private Integer groupbuy_begin_time;
	private Integer groupbuy_end_time;
	private Integer groupbuy_amount;
	private Integer groupbuy_count;
	private Integer groupbuy_number;
	private Integer groupbuy_time;
	private Integer groupbuy_limit;
	private Integer groupbuy_free_shipping;
	private Double purchase_price;
	private Integer purchase_begin_time;
	private Integer purchase_end_time;
	private Integer purchase_amount;
	private Integer purchase_count;
	private Integer purchase_limit;
	private Integer purchase_free_shipping;
	private Double chop_price;
	private Integer chop_begin_time;
	private Integer chop_end_time;
	private Integer chop_num;
	private Integer chop_amount;
	private Integer chop_count;
	private Integer chop_time;
	private Integer chop_free_shipping;
	private Integer category_id;
	private Integer shop_id;
	private Integer brand_id;
	private Integer country_id;
	private Integer integral;
	private Integer give_integral;
	private Integer sales;
	private Integer clicks;
	private Integer stocks;
	private Integer stock_alert_number;
	private Double longitude;
	private Double latitude;
	private Integer comments;
	private String params;
	private String ext_property;
	private Integer free_shipping;
	private Double shipping_fee;
	private Integer shipping_fee_id;
	private Double weight;
	private Integer free_shipping_count;
	private Integer commission_type;
	private String commissions;
	private Integer edit_time;
	private Integer add_time;
	private Integer release_time;
	private String seller_note;
	private Integer sort;
	private Integer status;
	private Integer in_shop;
	private Integer sale_method;
	private Double grade_price;
	private Integer groupbuy_show;
	private Integer groupbuy_now;
	private Integer purchase_show;
	private Integer purchase_now;
	private Integer chop_show;
	private Integer chop_now;
	private Double origin_price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMember_id() {
		return member_id;
	}

	public void setMember_id(Integer member_id) {
		this.member_id = member_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getAd_pic() {
		return ad_pic;
	}

	public void setAd_pic(String ad_pic) {
		this.ad_pic = ad_pic;
	}

	public String getPoster_pic() {
		return poster_pic;
	}

	public void setPoster_pic(String poster_pic) {
		this.poster_pic = poster_pic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getMarket_price() {
		return market_price;
	}

	public void setMarket_price(Double market_price) {
		this.market_price = market_price;
	}

	public Double getCost_price() {
		return cost_price;
	}

	public void setCost_price(Double cost_price) {
		this.cost_price = cost_price;
	}

	public Double getPromote_price() {
		return promote_price;
	}

	public void setPromote_price(Double promote_price) {
		this.promote_price = promote_price;
	}

	public Integer getPromote_begin_time() {
		return promote_begin_time;
	}

	public void setPromote_begin_time(Integer promote_begin_time) {
		this.promote_begin_time = promote_begin_time;
	}

	public Integer getPromote_end_time() {
		return promote_end_time;
	}

	public void setPromote_end_time(Integer promote_end_time) {
		this.promote_end_time = promote_end_time;
	}

	public Double getGroupbuy_price() {
		return groupbuy_price;
	}

	public void setGroupbuy_price(Double groupbuy_price) {
		this.groupbuy_price = groupbuy_price;
	}

	public Integer getGroupbuy_begin_time() {
		return groupbuy_begin_time;
	}

	public void setGroupbuy_begin_time(Integer groupbuy_begin_time) {
		this.groupbuy_begin_time = groupbuy_begin_time;
	}

	public Integer getGroupbuy_end_time() {
		return groupbuy_end_time;
	}

	public void setGroupbuy_end_time(Integer groupbuy_end_time) {
		this.groupbuy_end_time = groupbuy_end_time;
	}

	public Integer getGroupbuy_amount() {
		return groupbuy_amount;
	}

	public void setGroupbuy_amount(Integer groupbuy_amount) {
		this.groupbuy_amount = groupbuy_amount;
	}

	public Integer getGroupbuy_count() {
		return groupbuy_count;
	}

	public void setGroupbuy_count(Integer groupbuy_count) {
		this.groupbuy_count = groupbuy_count;
	}

	public Integer getGroupbuy_number() {
		return groupbuy_number;
	}

	public void setGroupbuy_number(Integer groupbuy_number) {
		this.groupbuy_number = groupbuy_number;
	}

	public Integer getGroupbuy_time() {
		return groupbuy_time;
	}

	public void setGroupbuy_time(Integer groupbuy_time) {
		this.groupbuy_time = groupbuy_time;
	}

	public Integer getGroupbuy_limit() {
		return groupbuy_limit;
	}

	public void setGroupbuy_limit(Integer groupbuy_limit) {
		this.groupbuy_limit = groupbuy_limit;
	}

	public Integer getGroupbuy_free_shipping() {
		return groupbuy_free_shipping;
	}

	public void setGroupbuy_free_shipping(Integer groupbuy_free_shipping) {
		this.groupbuy_free_shipping = groupbuy_free_shipping;
	}

	public Double getPurchase_price() {
		return purchase_price;
	}

	public void setPurchase_price(Double purchase_price) {
		this.purchase_price = purchase_price;
	}

	public Integer getPurchase_begin_time() {
		return purchase_begin_time;
	}

	public void setPurchase_begin_time(Integer purchase_begin_time) {
		this.purchase_begin_time = purchase_begin_time;
	}

	public Integer getPurchase_end_time() {
		return purchase_end_time;
	}

	public void setPurchase_end_time(Integer purchase_end_time) {
		this.purchase_end_time = purchase_end_time;
	}

	public Integer getPurchase_amount() {
		return purchase_amount;
	}

	public void setPurchase_amount(Integer purchase_amount) {
		this.purchase_amount = purchase_amount;
	}

	public Integer getPurchase_count() {
		return purchase_count;
	}

	public void setPurchase_count(Integer purchase_count) {
		this.purchase_count = purchase_count;
	}

	public Integer getPurchase_limit() {
		return purchase_limit;
	}

	public void setPurchase_limit(Integer purchase_limit) {
		this.purchase_limit = purchase_limit;
	}

	public Integer getPurchase_free_shipping() {
		return purchase_free_shipping;
	}

	public void setPurchase_free_shipping(Integer purchase_free_shipping) {
		this.purchase_free_shipping = purchase_free_shipping;
	}

	public Double getChop_price() {
		return chop_price;
	}

	public void setChop_price(Double chop_price) {
		this.chop_price = chop_price;
	}

	public Integer getChop_begin_time() {
		return chop_begin_time;
	}

	public void setChop_begin_time(Integer chop_begin_time) {
		this.chop_begin_time = chop_begin_time;
	}

	public Integer getChop_end_time() {
		return chop_end_time;
	}

	public void setChop_end_time(Integer chop_end_time) {
		this.chop_end_time = chop_end_time;
	}

	public Integer getChop_num() {
		return chop_num;
	}

	public void setChop_num(Integer chop_num) {
		this.chop_num = chop_num;
	}

	public Integer getChop_amount() {
		return chop_amount;
	}

	public void setChop_amount(Integer chop_amount) {
		this.chop_amount = chop_amount;
	}

	public Integer getChop_count() {
		return chop_count;
	}

	public void setChop_count(Integer chop_count) {
		this.chop_count = chop_count;
	}

	public Integer getChop_time() {
		return chop_time;
	}

	public void setChop_time(Integer chop_time) {
		this.chop_time = chop_time;
	}

	public Integer getChop_free_shipping() {
		return chop_free_shipping;
	}

	public void setChop_free_shipping(Integer chop_free_shipping) {
		this.chop_free_shipping = chop_free_shipping;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public Integer getShop_id() {
		return shop_id;
	}

	public void setShop_id(Integer shop_id) {
		this.shop_id = shop_id;
	}

	public Integer getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(Integer brand_id) {
		this.brand_id = brand_id;
	}

	public Integer getCountry_id() {
		return country_id;
	}

	public void setCountry_id(Integer country_id) {
		this.country_id = country_id;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getGive_integral() {
		return give_integral;
	}

	public void setGive_integral(Integer give_integral) {
		this.give_integral = give_integral;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public Integer getClicks() {
		return clicks;
	}

	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}

	public Integer getStocks() {
		return stocks;
	}

	public void setStocks(Integer stocks) {
		this.stocks = stocks;
	}

	public Integer getStock_alert_number() {
		return stock_alert_number;
	}

	public void setStock_alert_number(Integer stock_alert_number) {
		this.stock_alert_number = stock_alert_number;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getExt_property() {
		return ext_property;
	}

	public void setExt_property(String ext_property) {
		this.ext_property = ext_property;
	}

	public Integer getFree_shipping() {
		return free_shipping;
	}

	public void setFree_shipping(Integer free_shipping) {
		this.free_shipping = free_shipping;
	}

	public Double getShipping_fee() {
		return shipping_fee;
	}

	public void setShipping_fee(Double shipping_fee) {
		this.shipping_fee = shipping_fee;
	}

	public Integer getShipping_fee_id() {
		return shipping_fee_id;
	}

	public void setShipping_fee_id(Integer shipping_fee_id) {
		this.shipping_fee_id = shipping_fee_id;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getFree_shipping_count() {
		return free_shipping_count;
	}

	public void setFree_shipping_count(Integer free_shipping_count) {
		this.free_shipping_count = free_shipping_count;
	}

	public Integer getCommission_type() {
		return commission_type;
	}

	public void setCommission_type(Integer commission_type) {
		this.commission_type = commission_type;
	}

	public String getCommissions() {
		return commissions;
	}

	public void setCommissions(String commissions) {
		this.commissions = commissions;
	}

	public Integer getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(Integer edit_time) {
		this.edit_time = edit_time;
	}

	public Integer getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Integer add_time) {
		this.add_time = add_time;
	}

	public Integer getRelease_time() {
		return release_time;
	}

	public void setRelease_time(Integer release_time) {
		this.release_time = release_time;
	}

	public String getSeller_note() {
		return seller_note;
	}

	public void setSeller_note(String seller_note) {
		this.seller_note = seller_note;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIn_shop() {
		return in_shop;
	}

	public void setIn_shop(Integer in_shop) {
		this.in_shop = in_shop;
	}

	public Integer getSale_method() {
		return sale_method;
	}

	public void setSale_method(Integer sale_method) {
		this.sale_method = sale_method;
	}

	public Double getGrade_price() {
		return grade_price;
	}

	public void setGrade_price(Double grade_price) {
		this.grade_price = grade_price;
	}

	public Integer getGroupbuy_show() {
		return groupbuy_show;
	}

	public void setGroupbuy_show(Integer groupbuy_show) {
		this.groupbuy_show = groupbuy_show;
	}

	public Integer getGroupbuy_now() {
		return groupbuy_now;
	}

	public void setGroupbuy_now(Integer groupbuy_now) {
		this.groupbuy_now = groupbuy_now;
	}

	public Integer getPurchase_show() {
		return purchase_show;
	}

	public void setPurchase_show(Integer purchase_show) {
		this.purchase_show = purchase_show;
	}

	public Integer getPurchase_now() {
		return purchase_now;
	}

	public void setPurchase_now(Integer purchase_now) {
		this.purchase_now = purchase_now;
	}

	public Integer getChop_show() {
		return chop_show;
	}

	public void setChop_show(Integer chop_show) {
		this.chop_show = chop_show;
	}

	public Integer getChop_now() {
		return chop_now;
	}

	public void setChop_now(Integer chop_now) {
		this.chop_now = chop_now;
	}

	public Double getOrigin_price() {
		return origin_price;
	}

	public void setOrigin_price(Double origin_price) {
		this.origin_price = origin_price;
	}
}
