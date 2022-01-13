package com.laokema.javaweb.model.index;

import com.laokema.javaweb.model.BaseModel;

public class Coupon extends BaseModel {

	private Integer id;
	private Integer shop_id;
	private String name;
	private Double coupon_money;
	private Double coupon_discount;
	private Integer position;
	private Double min_price;
	private Integer begin_time;
	private Integer end_time;
	private Integer handy_time;
	private Integer quantity;
	private Integer auto_add;
	private Integer num_per_person;
	private Integer times;
	private Integer day_times;
	private Integer status;
	private Integer offline_use;
	private Integer permit_goods;
	private Integer type;
	private String pic;
	private Integer add_time;
	private String min_price_memo;
	private String memo;
	private String time_memo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShop_id() {
		return shop_id;
	}

	public void setShop_id(Integer shop_id) {
		this.shop_id = shop_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCoupon_money() {
		return coupon_money;
	}

	public void setCoupon_money(Double coupon_money) {
		this.coupon_money = coupon_money;
	}

	public Double getCoupon_discount() {
		return coupon_discount;
	}

	public void setCoupon_discount(Double coupon_discount) {
		this.coupon_discount = coupon_discount;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Double getMin_price() {
		return min_price;
	}

	public void setMin_price(Double min_price) {
		this.min_price = min_price;
	}

	public Integer getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(Integer begin_time) {
		this.begin_time = begin_time;
	}

	public Integer getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Integer end_time) {
		this.end_time = end_time;
	}

	public Integer getHandy_time() {
		return handy_time;
	}

	public void setHandy_time(Integer handy_time) {
		this.handy_time = handy_time;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getAuto_add() {
		return auto_add;
	}

	public void setAuto_add(Integer auto_add) {
		this.auto_add = auto_add;
	}

	public Integer getNum_per_person() {
		return num_per_person;
	}

	public void setNum_per_person(Integer num_per_person) {
		this.num_per_person = num_per_person;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getDay_times() {
		return day_times;
	}

	public void setDay_times(Integer day_times) {
		this.day_times = day_times;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOffline_use() {
		return offline_use;
	}

	public void setOffline_use(Integer offline_use) {
		this.offline_use = offline_use;
	}

	public Integer getPermit_goods() {
		return permit_goods;
	}

	public void setPermit_goods(Integer permit_goods) {
		this.permit_goods = permit_goods;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Integer add_time) {
		this.add_time = add_time;
	}

	public String getMin_price_memo() {
		return min_price_memo;
	}

	public void setMin_price_memo(String min_price_memo) {
		this.min_price_memo = min_price_memo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTime_memo() {
		return time_memo;
	}

	public void setTime_memo(String time_memo) {
		this.time_memo = time_memo;
	}
}
