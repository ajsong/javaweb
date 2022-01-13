package com.laokema.javaweb.kernel;

import com.laokema.javaweb.model.BaseModel;
import com.laokema.tool.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KernelServlet extends HttpServlet {
	public HttpServletRequest servletRequest;
	public HttpServletResponse servletResponse;
	public String session_id;
	public Request request;
	public long now;
	public String ip;
	public Map<String, String> headers;
	public Map<String, String> configs;
	public boolean is_wx;
	public boolean is_mini;
	public boolean is_web;
	public boolean is_wap;
	public String app;
	public String act;
	public static Client client;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Common.setServlet(request, response);
		this.servletRequest = request;
		this.servletResponse = response;
		this.session_id = request.getSession().getId();
		this.request = new Request(request, response);
		this.now = Common.time();
		this.ip = Common.ip();
		this.headers = getHeaders();
		this.is_wx = Common.isWX();
		this.is_mini = Common.isMini();
		this.is_wap = Common.isWap();
		this.is_web = Common.isWeb();
		this.app = "home";
		this.act = "index";
		Matcher matcher = Pattern.compile("^/(\\w+)(/(\\w+))?").matcher(request.getRequestURI().replaceAll("/" + Common.get_display_name(), ""));
		if (matcher.find()) {
			this.app = matcher.group(1);
			if (matcher.group(3) != null) this.act = matcher.group(3);
		}
	}

	//反射执行实例方法
	public void doMethod() {
		try {
			Method method = this.getClass().getMethod(this.act);
			method.invoke(this);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	//获取主机头信息
	public Map<String, String> getHeaders() {
		return Common.get_headers();
	}

	//获取/设置Session
	public Object getSession(String key) {
		return this.servletRequest.getSession().getAttribute(key);
	}
	@SuppressWarnings("unchecked")
	public <T> T getSession(String key, Class<T> clazz) {
		Object value = this.getSession(key);
		if (value == null) return null;
		if (value instanceof Map) value = Common.mapToInstance((Map<String, Object>) value, clazz);
		if (value != null && value.getClass() != clazz) return null;
		return (T) value;
	}
	public void setSession(String key, Object value) {
		if (value == null) {
			removeSession(key);
		} else {
			this.servletRequest.getSession().setAttribute(key, value);
		}
	}
	public void removeSession(String key) {
		this.servletRequest.getSession().removeAttribute(key);
	}

	//获取/设置Cookie
	public String getCookie(String key) {
		try {
			Cookie[] cookies = this.servletRequest.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(key)) return URLDecoder.decode(cookie.getValue(), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void setCookie(String key, String value) {
		setCookie(key, value, -1);
	}
	public void setCookie(String key, String value, int expiry) {
		if (value == null) {
			removeCookie(key);
			return;
		}
		try {
			Cookie cookie = new Cookie(key, URLEncoder.encode(value, "UTF-8"));
			if (expiry > 0) cookie.setMaxAge(expiry); //有效时长(单位秒), 默认为-1, 页面关闭就失效
			cookie.setPath("/"); //设置访问该域名下某个路径时生效
			this.servletResponse.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public void removeCookie(String key) {
		Cookie cookie = new Cookie(key, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		this.servletResponse.addCookie(cookie);
	}

	//加载配置参数
	public void setConfigs() {
		this.configs = new HashMap<>();
		List<Config> CONFIG = DB.share("op_config").field("name, content").cached(60*60*24*3).select(Config.class);
		for (Config g : CONFIG) this.configs.put(g.getName(), g.getContent());
		CONFIG = DB.share("config").field("name, content").cached(60*60*24*3).select(Config.class);
		for (Config g : CONFIG) this.configs.put(g.getName(), g.getContent());
	}

	//通过COOKIE获取账号资料,token为空字符串时插入记录,为null时删除记录, 需创建对应token表, 表_token, name:16, token:32
	public <T> T cookieAccount(String table, String name) {
		return cookieAccount(table, name, "");
	}
	public <T> T cookieAccount(String table, String name, String token) {
		return cookieAccount(table, name, token, "m.*");
	}
	@SuppressWarnings("unchecked")
	public <T> T cookieAccount(String table, String name, String token, String field) {
		try {
			String[] tables = table.split("_");
			String master = tables[0];
			if (token != null) {
				if (token.length() > 0) {
					return (T) DB.share(master + " m").left(table + " t", "m.name=t.name").where("t.name&t.token", name, token).cached(60*60*24*7).field(field)
							.find(Class.forName("com.laokema.javaweb.model.index." + Character.toUpperCase(master.charAt(0)) + master.substring(1)));
				} else {
					token = Common.md5(UUID.randomUUID().toString());
					DB.share(table).delete("name=?", name);
					DB.share(table).insert(new String[]{"name", "token"}, name, token);
					this.setCookie(master + "_name", name, (int)(this.now+60*60*24*365));
					this.setCookie(master + "_token", token, (int)(this.now+60*60*24*365));
				}
			} else {
				DB.share(table).delete("name=?", name);
				this.removeCookie(master + "_name");
				this.removeCookie(master + "_token");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//是否微信端打开
	public boolean is_weixin() {
		return this.is_wx || this.is_mini;
	}

	public static class Config {
		public String name;
		public String content;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}

	public static class Client extends BaseModel {
		public String name;
		public String host;
		public String domain;
		public String mobile;
		public Integer edition;
		public String function;
		public String baidu_ak;
		public String mini_qrcode;
		public String push_type;
		public String push_fields;
		public String upload_type;
		public String upload_fields;
		public String sms_type;
		public String sms_fields;
		public Integer sms_max_ip_time;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public String getDomain() {
			return domain;
		}
		public void setDomain(String domain) {
			this.domain = domain;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public Integer getEdition() {
			return edition;
		}
		public void setEdition(Integer edition) {
			this.edition = edition;
		}
		public String getFunction() {
			return function;
		}
		public void setFunction(String function) {
			this.function = function;
		}
		public String getBaidu_ak() {
			return baidu_ak;
		}
		public void setBaidu_ak(String baidu_ak) {
			this.baidu_ak = baidu_ak;
		}
		public String getMini_qrcode() {
			return mini_qrcode;
		}
		public void setMini_qrcode(String mini_qrcode) {
			this.mini_qrcode = mini_qrcode;
		}
		public String getPush_type() {
			return push_type;
		}
		public void setPush_type(String push_type) {
			this.push_type = push_type;
		}
		public String getPush_fields() {
			return push_fields;
		}
		public void setPush_fields(String push_fields) {
			this.push_fields = push_fields;
		}
		public String getUpload_type() {
			return upload_type;
		}
		public void setUpload_type(String upload_type) {
			this.upload_type = upload_type;
		}
		public String getUpload_fields() {
			return upload_fields;
		}
		public void setUpload_fields(String upload_fields) {
			this.upload_fields = upload_fields;
		}
		public String getSms_type() {
			return sms_type;
		}
		public void setSms_type(String sms_type) {
			this.sms_type = sms_type;
		}
		public String getSms_fields() {
			return sms_fields;
		}
		public void setSms_fields(String sms_fields) {
			this.sms_fields = sms_fields;
		}
		public Integer getSms_max_ip_time() {
			return sms_max_ip_time;
		}
		public void setSms_max_ip_time(Integer sms_max_ip_time) {
			this.sms_max_ip_time = sms_max_ip_time;
		}
	}
}
