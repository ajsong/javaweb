package com.laokema.javaweb.model.index;

import com.laokema.javaweb.model.BaseModel;

public class Member extends BaseModel {

	public Integer id;
	public String name;
	public String password;
	public String origin_password;
	public String salt;
	public String sign;
	public String session_id;
	public String withdraw_password;
	public String withdraw_salt;
	public String real_name;
	public String mobile;
	public String code;
	public Integer reg_time;
	public String reg_ip;
	public Integer last_time;
	public String last_ip;
	public Integer logins;
	public Integer member_type;
	public String card_sn;
	public Integer experience;
	public String avatar;
	public String udid;
	public Integer badge;
	public String wechat;
	public String qq;
	public String idcard;
	public String alipay;
	public Integer status;
	public Double money;
	public Double commission;
	public Double commission_total;
	public Integer integral;
	public String invite_code;
	public String nick_name;
	public String sex;
	public String province;
	public String city;
	public String district;
	public String town;
	public Integer birth_year;
	public Integer birth_month;
	public Integer birth_day;
	public String remark;
	public Integer parent_id;
	public Integer grade_id;
	public Integer grade_score;
	public Integer grade_time;
	public Integer grade_endtime;
	public Integer shopowner_id;
	public Integer belong_shop_id;
	public Integer from_shop_id;

	public Integer shop_id;
	public Object shop;
	public Object grade;
	public Double total_price;
	public Integer next_score;

	public Integer getIs_mobile() {
		return is_mobile;
	}

	public void setIs_mobile(Integer is_mobile) {
		this.is_mobile = is_mobile;
	}

	public Integer is_mobile;
	public String format_reg_time;

	public String getFormat_reg_time() {
		return format_reg_time;
	}

	public void setFormat_reg_time(String format_reg_time) {
		this.format_reg_time = format_reg_time;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getOrigin_password() {
		return origin_password;
	}
	public void setOrigin_password(String origin_password) {
		this.origin_password = origin_password;
	}

	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getWithdraw_password() {
		return withdraw_password;
	}
	public void setWithdraw_password(String withdraw_password) {
		this.withdraw_password = withdraw_password;
	}

	public String getWithdraw_salt() {
		return withdraw_salt;
	}
	public void setWithdraw_salt(String withdraw_salt) {
		this.withdraw_salt = withdraw_salt;
	}

	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public Integer getReg_time() {
		return reg_time;
	}
	public void setReg_time(Integer reg_time) {
		this.reg_time = reg_time;
	}

	public String getReg_ip() {
		return reg_ip;
	}
	public void setReg_ip(String reg_ip) {
		this.reg_ip = reg_ip;
	}

	public Integer getLast_time() {
		return last_time;
	}
	public void setLast_time(Integer last_time) {
		this.last_time = last_time;
	}

	public String getLast_ip() {
		return last_ip;
	}
	public void setLast_ip(String last_ip) {
		this.last_ip = last_ip;
	}

	public Integer getLogins() {
		return logins;
	}
	public void setLogins(Integer logins) {
		this.logins = logins;
	}

	public Integer getMember_type() {
		return member_type;
	}
	public void setMember_type(Integer member_type) {
		this.member_type = member_type;
	}

	public String getCard_sn() {
		return card_sn;
	}
	public void setCard_sn(String card_sn) {
		this.card_sn = card_sn;
	}

	public Integer getExperience() {
		return experience;
	}
	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}

	public Integer getBadge() {
		return badge;
	}
	public void setBadge(Integer badge) {
		this.badge = badge;
	}

	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getAlipay() {
		return alipay;
	}
	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getCommission_total() {
		return commission_total;
	}
	public void setCommission_total(Double commission_total) {
		this.commission_total = commission_total;
	}

	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getInvite_code() {
		return invite_code;
	}
	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}

	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}

	public Integer getBirth_year() {
		return birth_year;
	}
	public void setBirth_year(Integer birth_year) {
		this.birth_year = birth_year;
	}

	public Integer getBirth_month() {
		return birth_month;
	}
	public void setBirth_month(Integer birth_month) {
		this.birth_month = birth_month;
	}

	public Integer getBirth_day() {
		return birth_day;
	}
	public void setBirth_day(Integer birth_day) {
		this.birth_day = birth_day;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public Integer getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(Integer grade_id) {
		this.grade_id = grade_id;
	}

	public Integer getGrade_score() {
		return grade_score;
	}
	public void setGrade_score(Integer grade_score) {
		this.grade_score = grade_score;
	}

	public Integer getGrade_time() {
		return grade_time;
	}
	public void setGrade_time(Integer grade_time) {
		this.grade_time = grade_time;
	}

	public Integer getGrade_endtime() {
		return grade_endtime;
	}
	public void setGrade_endtime(Integer grade_endtime) {
		this.grade_endtime = grade_endtime;
	}

	public Integer getShopowner_id() {
		return shopowner_id;
	}
	public void setShopowner_id(Integer shopowner_id) {
		this.shopowner_id = shopowner_id;
	}

	public Integer getBelong_shop_id() {
		return belong_shop_id;
	}
	public void setBelong_shop_id(Integer belong_shop_id) {
		this.belong_shop_id = belong_shop_id;
	}

	public Integer getFrom_shop_id() {
		return from_shop_id;
	}
	public void setFrom_shop_id(Integer from_shop_id) {
		this.from_shop_id = from_shop_id;
	}

	public Integer getShop_id() {
		return shop_id;
	}
	public void setShop_id(Integer shop_id) {
		this.shop_id = shop_id;
	}

	public Object getShop() {
		return shop;
	}
	public void setShop(Object shop) {
		this.shop = shop;
	}

	public Object getGrade() {
		return grade;
	}
	public void setGrade(Object grade) {
		this.grade = grade;
	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public Integer getNext_score() {
		return next_score;
	}

	public void setNext_score(Integer next_score) {
		this.next_score = next_score;
	}
}