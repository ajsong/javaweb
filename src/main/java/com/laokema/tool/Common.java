//Developed by @mario 1.0.20220113
package com.laokema.tool;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.laokema.javaweb.model.BaseModel;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.*;
import java.io.*;
import java.lang.reflect.*;
import java.math.BigInteger;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.text.*;
import java.util.*;
import java.lang.*;
import java.util.regex.*;

public class Common {
	static String rootPath;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static String imgDomain;
	static ClientDefine clientDefine;

	//设置全局Request、Response
	public static void setServlet(HttpServletRequest req, HttpServletResponse res) {
		request = req;
		response = res;
		response.setContentType("text/html; charset=utf-8");
	}

	//获取根目录路径
	public static String get_root_path() {
		if (rootPath == null || rootPath.length() == 0) {
			String path = Objects.requireNonNull(Common.class.getResource("../../../../../WEB-INF")).getPath();
			rootPath = new File(path).getParent();
		}
		return rootPath;
	}

	//获取web.xml的display-name
	public static String get_display_name() {
		return request.getServletContext().getServletContextName();
	}

	//读取配置文件
	public static Map<String, String> get_properties() {
		return get_properties("config.properties");
	}
	public static Map<String, String> get_properties(String filename) {
		Map<String, String> map = new HashMap<>();
		try {
			Properties properties = new Properties();
			properties.load(Common.class.getClassLoader().getResourceAsStream(filename));
			for (String key: properties.stringPropertyNames()) {
				map.put(key, properties.getProperty(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	//解析配置文件参数值(json类型)
	@SuppressWarnings("unchecked")
	public static <T> T get_json_property(String param) {
		Map<String, String> properties = get_properties();
		String value = properties.get(param);
		if (value != null && value.length() > 0) {
			if (value.startsWith("[")) {
				return (T) JSONArray.parseArray(value);
			} else if (value.startsWith("{")) {
				return (T) JSON.parseObject(value);
			}
		}
		return (T) value;
	}

	//读取自定义配置文件
	public static Map<String, Object> get_my_property(String filepath) {
		Map<String, Object> map = new HashMap<>();
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(filepath));
			for (String key: properties.stringPropertyNames()) {
				String value = properties.getProperty(key);
				if (value.startsWith("[")) {
					List<Object> list = JSONObject.parseArray(JSON.parseArray(value).toJSONString(), Object.class);
					map.put(key, list);
				} else if (value.startsWith("{")) {
					JSONObject obj = JSON.parseObject(value);
					Map<String, Object> subMap = new HashMap<>(obj);
					map.put(key, subMap);
				} else {
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	//生成自定义配置文件
	public static void save_my_property(Map<String, Object> params, String filepath) {
		StringBuilder content = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			Object value = param.getValue();
			content.append(param.getKey()).append(" = ").append((value instanceof String) ? value : JSON.toJSONString(value)).append("\n");
		}
		try {
			FileWriter fileWritter = new FileWriter(get_root_path() + "/" + trim(filepath.replaceAll(get_root_path(), ""), "/"));
			fileWritter.write(content.toString());
			fileWritter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//生成序列号
	public static String generate_sn() {
		return date("yyyyMMddHHmmss") + rand(10000, 99999);
		/*
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		int week = c.get(Calendar.DAY_OF_WEEK);
		*/
	}

	//生成sign
	public static String generate_sign() {
		return md5(md5(String.valueOf(rand(100000, 999999))) + time());
	}

	//生成密码盐值salt
	public static String generate_salt() {
		return String.valueOf(rand(100000, 999999));
	}

	//根据盐值生成加密密码
	public static String crypt_password(String password, String salt) {
		if (password == null || password.length() == 0 || salt == null || salt.length() == 0) return "";
		return md5(md5(password) + salt);
	}

	//清除两端字符串
	public static String trim(String str) {
		return trim(str, " ");
	}
	public static String trim(String str, String symbol) {
		return str.replaceAll("(^" + symbol + "|" + symbol + "$)", "");
	}

	//是否POST请求
	public static boolean isPost() {
		return request.getMethod().equalsIgnoreCase("POST");
	}

	//是否PUT请求
	public static boolean isPut() {
		return request.getMethod().equalsIgnoreCase("PUT");
	}

	//是否DELETE请求
	public static boolean isDelete() {
		return request.getMethod().equalsIgnoreCase("DELETE");
	}

	//是否WAP
	public static boolean isWap() {
		return is_mobile_web();
	}

	//是否WEB
	public static boolean isWeb() {
		return (!isAjax() && !isWap());
	}

	//是否微信端打开
	public static boolean isWX() {
		return StringUtils.containsIgnoreCase(get_headers().get("user-agent"), "MicroMessenger");
	}

	//是否微信小程序打开
	public static boolean isMini() {
		return (StringUtils.containsIgnoreCase(get_headers().get("referer"), "https://servicewechat.com/wx") && isWX());
	}

	//是否微信开发者工具打开
	public static boolean isDevTools() {
		return StringUtils.containsIgnoreCase(get_headers().get("user-agent"), "wechatdevtools");
	}

	//是否AJAX
	public static boolean isAjax() {
		return ((request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equals("XMLHttpRequest")) || request.getHeader("user-agent").contains("RequestType/api"));
	}

	//判断移动端浏览器打开
	public static boolean is_mobile_web() {
		String[] keywords = new String[]{
			"nokia", "sony", "ericsson", "mot", "samsung", "htc", "sgh", "lg", "sharp", "sie-", "philips", "panasonic", "alcatel", "lenovo", "blackberry",
			"meizu", "netfront", "symbian", "ucweb", "windowsce", "palm", "operamini", "operamobi", "openwave", "nexusone", "cldc", "midp", "wap", "mobile",
			"smartphone", "windows ce", "windows phone", "ipod", "iphone", "ipad", "android"
		};
		return get_headers().get("user-agent").matches("(" + StringUtils.join(keywords, "|") + ")");
	}

	//获取主机头信息
	public static Map<String, String> get_headers() {
		Map<String, String> headers = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			String value = request.getHeader(key);
			headers.put(key, value);
		}
		return headers;
	}

	//MD5
	public static String md5(String str) {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(str.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			//一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//base64 encode
	public static String base64_encode(String str) {
		return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
	}

	//base64 decode
	public static String base64_decode(String str) {
		byte[] bytes = Base64.getDecoder().decode(str);
		return new String(bytes, StandardCharsets.UTF_8);
	}

	//json_encode
	public static String json_encode(Object obj) {
		return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
	}

	//json_decode
	public static Map<String, Object> json_decode(String str) {
		return JSONObject.parseObject(str);
	}

	//时间戳
	public static long time() {
		return time(date("yyyy-MM-dd HH:mm:ss"));
	}
	public static long time(String date) {
		try {
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return time(dateformat.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	public static long time(Date date) {
		return date.getTime() / 1000;
	}

	//日期格式化
	public static String date(String format) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		return dateformat.format(new Date());
	}
	public static String date(String format, long timestamp) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		return dateformat.format(new Date(timestamp));
	}

	//指定范围随机数
	public static int rand(int min, int max) {
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
		//UUID.randomUUID().toString(); //利用UUID生成伪随机字符串
	}

	//指定长度随机字符串
	public static String random_str(int length) {
		ArrayList<String> list = new ArrayList<>();
		int begin = 97;
		//生成小写字母,并加入集合
		for(int i = begin; i < begin + 26; i++) {
			list.add((char)i + "");
		}
		//生成大写字母,并加入集合
		begin = 65;
		for(int i = begin; i < begin + 26; i++) {
			list.add((char)i + "");
		}
		//将0-9的数字加入集合
		for(int i = 0; i < 10; i++) {
			list.add(i + "");
		}
		Random random = new Random();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < length; i++) {
			res.append(list.get(random.nextInt(list.size())));
		}
		return res.toString();
	}

	//将时间转换成刚刚、分钟、小时
	public static String get_time_word(String date) {
		return get_time_word(time(date));
	}
	public static String get_time_word(Date date) {
		return get_time_word(time(date));
	}
	public static String get_time_word(long timestamp) {
		long between = time() - timestamp;
		if (between < 60) return "刚刚";
		if (between < 3600) return Math.floor((double) (between / 60)) + "分钟前";
		if (between < 86400) return Math.floor((double) (between/3600)) + "小时前";
		if (between <= 864000) return Math.floor((double) (between/86400)) + "天前";
		return date("Y-m-d", timestamp);
	}

	//获取ip
	public static String ip() {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	//返回http协议
	public static String https() {
		return request.getScheme().equals("https") ? "https://" : "http://";
	}

	//当前网址
	public static String domain() {
		String path = request.getContextPath();
		return request.getScheme() + "://" + request.getServerName() + (request.getServerPort() != 80 ? ":" + request.getServerPort() : "") + path;
	}

	//格式化URL,suffix增加网址后缀, 如七牛?imageMogr2/thumbnail/200x200, 又拍云(需自定义)!logo
	public static class Client extends BaseModel {
		public String domain;
		public String getDomain() {
			return domain;
		}
		public void setDomain(String domain) {
			this.domain = domain;
		}
	}
	public static String add_domain(String url) {
		return add_domain(url, "");
	}
	public static String add_domain(String url, String suffix) {
		if (imgDomain == null || imgDomain.length() == 0) {
			Client client = DB.share("client").cached(60*60*24*3).find(Client.class);
			imgDomain = client.domain;
		}
		String server = https() + request.getServerName();
		if (url != null && url.length() > 0 && !url.startsWith("http://") && !url.startsWith("https://")) {
			if (url.startsWith("//")) {
				url = https() + url.substring(2);
			} else {
				if (url.contains("%domain%") && !url.contains(server)) {
					url = url.replaceAll("%domain%", server);
				} else {
					url = url.replaceAll("%domain%", "");
					if (url.charAt(0) == '/') {
						url = (imgDomain.length() > 0 ? imgDomain : server) + url;
					} else {
						if (Pattern.matches("^((http|https|ftp)://)?[\\w-_]+(\\.[\\w\\-_]+)+([\\w\\-.,@?^=%&:/~+#]*[\\w\\-@?^=%&/~+#])?$", server + "/" + url)) {
							url = (imgDomain.length() > 0 ? imgDomain : server) + "/" + url;
						} else {
							url = url.replaceAll("\"/uploads/", "\"" + server + "/uploads/");
						}
					}
				}
			}
		}
		if (url != null && url.length() > 0 && !url.contains("/images/") && suffix.length() > 0 && !url.contains(suffix)) url += suffix;
		if (url != null) url = url.replaceAll("%domain%", "");
		return url;
	}

	//递归一个数组/对象的属性加上域名
	public static <T> T add_domain_deep(T obj, String field) {
		return add_domain_deep(obj, new String[]{field});
	}
	@SuppressWarnings("unchecked")
	public static <T> T add_domain_deep(T obj, String[] fields) {
		if (obj instanceof List) {
			List<Object> o = (List<Object>)obj;
			for (int i = 0; i < o.size(); i++) {
				Object e = add_domain_deep(o.get(i), fields);
				o.set(i, e);
			}
		} else if (obj instanceof Map) {
			Map<String, String> map = new HashMap<>((Map<String, String>) obj);
			for (String key : map.keySet()) {
				if (!Arrays.asList(fields).contains(key)) continue;
				map.put(key, add_domain(map.get(key)));
			}
			obj = (T) map;
		} else if (obj instanceof String) {
			obj = (T) add_domain((String) obj);
		} else if (obj != null) {
			try {
				Class<?> clazz = obj.getClass();
				Field[] _fields = clazz.getDeclaredFields();
				for (Field f : _fields) {
					if (!Arrays.asList(fields).contains(f.getName())) continue;
					String getterName = "get" + Character.toUpperCase(f.getName().charAt(0)) + f.getName().substring(1);
					Method getter = clazz.getMethod(getterName);
					String url = (String) getter.invoke(obj);
					String setterName = "set" + Character.toUpperCase(f.getName().charAt(0)) + f.getName().substring(1);
					Method setter = clazz.getMethod(setterName, f.getType());
					setter.invoke(obj, add_domain(url));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	//输出script
	public static void script(String msg) {
		script(msg, "");
	}
	public static void script(String msg, String url) {
		try {
			String html = "<meta charset=\"UTF-8\"><script>";
			if (msg != null && msg.length() > 0) html += "alert('" + msg + "');";
			if (url != null && url.length() > 0) {
				if (url.startsWith("javascript:")) {
					html += url.substring(11);
				} else if (url.startsWith("js:")) {
					html += url.substring(3);
				} else {
					html += "location.href = '" + url + "';";
				}
			}
			html += "</script>";
			PrintWriter out = response.getWriter();
			out.write(html);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void historyBack() {
		historyBack("");
	}
	public static void historyBack(String msg) {
		script(msg, "javascript:history.back()");
	}

	//跳转网址
	public static void location(String url) {
		try {
			response.sendRedirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//写log
	public static void write_log(String content) {
		//String path = request.getSession().getServletContext().getRealPath("/temp");
		String path = get_root_path() + "/temp";
		File filePath = new File(path);
		if (!filePath.exists()) {
			if (!filePath.mkdirs()) throw new IllegalArgumentException("File path create fail: " + path);
		}
		write_log(content, path + "/log.txt");
	}
	public static void write_log(String content, String file) {
		try {
			FileWriter fileWritter = new FileWriter(file, true);
			fileWritter.write(content);
			fileWritter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void write_error(String content) {
		String path = get_root_path() + "/temp";
		File filePath = new File(path);
		if (!filePath.exists()) {
			if (!filePath.mkdirs()) throw new IllegalArgumentException("File path create fail: " + path);
		}
		write_log(content, path + "/error.txt");
	}

	//上传文件
	public static String upload_file(String key) {
		return upload_file(key, "");
	}
	public static String upload_file(String key, String dir) {
		return upload_file(key, dir, "jpg,png,gif,bmp");
	}
	public static String upload_file(String key, String dir, String fileType) {
		Map<String, Object> files = upload_file(dir, fileType, false);
		if (files == null || files.keySet().size() == 0) return "";
		return (String) files.get(key);
	}
	public static Map<String, Object> upload_file(String dir, String fileType, boolean returnDetail) {
		Upload upload = new Upload(request, response);
		return upload.file(dir, fileType, returnDetail);
	}

	//Map转对象
	public static <T> T mapToInstance(Map<String, Object> map, Class<T> clazz) {
		try {
			T obj = clazz.getConstructor().newInstance();
			for (String key : map.keySet()) {
				Object value = map.get(key);
				try {
					Field f = obj.getClass().getDeclaredField(key);
					String setterName = "set" + Character.toUpperCase(key.charAt(0)) + key.substring(1);
					Method setter = clazz.getMethod(setterName, f.getType());
					if (value != null && !f.getType().equals(value.getClass()) && !f.getType().getName().equals("java.lang.Object")) {
						if (f.getType() == Integer.class) {
							value = Integer.parseInt(String.valueOf(value));
						} else if (f.getType() == Long.class) {
							value = Long.parseLong(String.valueOf(value));
						} else if (f.getType() == Float.class) {
							value = Float.parseFloat(String.valueOf(value));
						} else if (f.getType() == Double.class) {
							value = Double.parseDouble(String.valueOf(value));
						} else if (f.getType() == String.class) {
							value = String.valueOf(value);
						} else {
							System.out.println("Common.mapToInstance");
							System.out.println(clazz.getName()+"     "+setterName+"      "+f.getName()+" = "+f.getType().getName()+"        data = "+value.getClass().getName());
						}
					}
					if (value != null) setter.invoke(obj, value);
				} catch (NoSuchFieldException e) {
					String packageName = Common.class.getPackage().getName();
					if (clazz.getSuperclass().getName().equals(packageName.substring(0, packageName.lastIndexOf(".") + 1) + "model.BaseModel")) {
						Method getter = clazz.getMethod("set", String.class, Object.class);
						getter.invoke(obj, key, value);
					}
				}
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//对象转Map
	public static Map<String, Object> instanceToMap(Object obj) {
		JSONObject map = JSON.parseObject(JSON.toJSONString(obj));
		return new HashMap<>(map);
	}

	//请求
	public static String requestUrl(String method, String url) {
		return requestUrl(method, url, "", false, null);
	}
	public static String requestUrl(String method, String url, String data) {
		return requestUrl(method, url, data, false, null);
	}
	public static JSONObject requestUrl(String method, String url, String data, boolean returnJson) {
		Map<String, String> map = new HashMap<>();
		if (data != null && data.length() > 0) {
			String[] _params = data.split("&");
			for (String param : _params) {
				String[] p = param.split("=");
				map.put(p[0], p.length > 1 ? p[1] : "");
			}
		}
		return requestUrl(method, url, map, returnJson);
	}
	public static JSONObject requestUrl(String method, String url, Map<String, String> data, boolean returnJson) {
		String res = requestUrl(method, url, data, false, null);
		return JSON.parseObject(res);
	}
	public static String requestUrl(String method, String url, Map<String, String> data) {
		return requestUrl(method, url, data, false, null);
	}
	public static String requestUrl(String method, String url, String data, boolean postJson, Map<String, String> headers) {
		Map<String, String> map = new HashMap<>();
		if (data != null && data.length() > 0) {
			String[] _params = data.split("&");
			for (String param : _params) {
				String[] p = param.split("=");
				map.put(p[0], p.length > 1 ? p[1] : "");
			}
		}
		return requestUrl(method, url, map, postJson, headers);
	}
	public static String requestUrl(String method, String url, Map<String, String> data, boolean postJson, Map<String, String> headers) {
		method = method.toUpperCase();
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		StringBuilder res = new StringBuilder();
		try {
			if (url.startsWith("/")) url = domain() + url;
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setConnectTimeout(5000);
			conn.setUseCaches(false);
			conn.setRequestProperty("User-Agent", request.getHeader("user-agent") + " RequestType/api");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			if (headers != null) {
				for (String key : headers.keySet()) {
					conn.setRequestProperty(key, headers.get(key));
				}
			}
			if (method.equalsIgnoreCase("POST")) {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true); //发送POST请求必须设置
				conn.setDoInput(true);
				boolean isMultipart = false;
				for (String key : data.keySet()) {
					if (data.get(key).startsWith("@")) {
						isMultipart = true;
						break;
					}
				}
				if (isMultipart) {
					String boundary = "WebKitFormBoundary" + random_str(16);
					conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
					OutputStream stream = new DataOutputStream(conn.getOutputStream());
					for (String key : data.keySet()) {
						String value = data.get(key);
						if (value == null) continue;
						StringBuilder strBuf = new StringBuilder();
						if (data.get(key).startsWith("@")) { //file
							File file = new File(value.substring(1));
							String filename = file.getName();
							String mimeType = "";
							try {
								Path path = Paths.get(filename);
								mimeType = Files.probeContentType(path);
							} catch (Exception e) {
								//System.out.println("文件 " + value.substring(1) + " 获取 MIME 失败");
								mimeType = "application/octet-stream";
							}
							strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
							strBuf.append("Content-Disposition: form-data; name=\"").append(key).append("\"; filename=\"").append(filename).append("\"\r\n");
							strBuf.append("Content-Type: ").append(mimeType).append("\r\n\r\n");
							stream.write(strBuf.toString().getBytes());
							DataInputStream in = new DataInputStream(new FileInputStream(file));
							int bytes;
							byte[] bufferOut = new byte[1024];
							while ((bytes = in.read(bufferOut)) != -1) {
								stream.write(bufferOut, 0, bytes);
							}
							in.close();
						} else { //input
							strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
							strBuf.append("Content-Disposition: form-data; name=\"").append(key).append("\"\r\n\r\n");
							strBuf.append(value);
							stream.write(strBuf.toString().getBytes());
						}
					}
					byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
					stream.write(endData);
					stream.flush();
					stream.close();
				} else {
					StringBuilder postData = new StringBuilder();
					//使用JSON提交
					if (postJson) {
						conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
						postData = new StringBuilder(JSONObject.toJSONString(data));
					} else {
						for (String key : data.keySet()) {
							postData.append("&").append(key).append("=").append(data.get(key));
						}
					}
					byte[] bytes = trim(postData.toString(), "&").getBytes(StandardCharsets.UTF_8);
					conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
					conn.getOutputStream().write(bytes);
				}
			} else {
				conn.connect();
			}
			/*// 获取所有响应头字段
			Map<String, List<String>> fields = conn.getHeaderFields();
			for (String key : fields.keySet()) {
				System.out.println(key + ": " + fields.get(key));
			}*/
			int status = conn.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
				String line;
				while ((line = reader.readLine()) != null) {
					res.append(line).append("\n");
				}
			}
		} catch (Exception e) {
			System.out.println("发送 " + method + " 请求异常");
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) reader.close();
				if (conn != null) conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res.toString();
	}

	//display
	public static class ClientDefine extends BaseModel {
		public String WEB_NAME;
		public String WEB_TITLE;
		public String getWEB_NAME() {
			return WEB_NAME;
		}
		public void setWEB_NAME(String WEB_NAME) {
			this.WEB_NAME = WEB_NAME;
		}
		public String getWEB_TITLE() {
			return WEB_TITLE;
		}
		public void setWEB_TITLE(String WEB_TITLE) {
			this.WEB_TITLE = WEB_TITLE;
		}
	}
	public static void display(String jspPath) {
		display(jspPath, null);
	}
	public static void display(String jspPath, Map<String, Object> element) {
		try {
			if (clientDefine == null) {
				clientDefine = DB.share("client_define").field("WEB_NAME, WEB_TITLE").cached(60*60*24*3).find(ClientDefine.class);
			}
			Object memberObj = request.getSession().getAttribute("member");
			if (memberObj != null) {
				JSONObject json = JSON.parseObject(JSON.toJSONString(memberObj));
				Map<String, Object> member = new HashMap<>(json);
				if (member.get("id") != null && ((int)member.get("id")) > 0) {
					member = add_domain_deep(member, "avatar");
					member.put("reg_time_word", date("Y-m-d", Long.parseLong(String.valueOf(member.get("reg_time")))));
					memberObj = mapToInstance(member, Class.forName("com.laokema.javaweb.model.index.Member"));
					request.setAttribute("member", memberObj);
					request.setAttribute("logined", 1);
				}
			} else {
				Map<String, Object> member = DB.createInstanceMap("member");
				member.put("id", 0);
				member.put("avatar", add_domain("/images/avatar.png"));
				memberObj = mapToInstance(member, Class.forName("com.laokema.javaweb.model.index.Member"));
				request.setAttribute("member", memberObj);
				request.setAttribute("logined", 0);
			}
			String app = "home";
			String act = "index";
			Matcher matcher = Pattern.compile("^/(\\w+)(/(\\w+))?").matcher(request.getRequestURI().replaceAll("/" + get_display_name(), ""));
			if (matcher.find()) {
				app = matcher.group(1);
				if (matcher.group(3) != null) act = matcher.group(3);
			}
			request.setAttribute("app", app);
			request.setAttribute("act", act);
			request.setAttribute("domain", domain());
			if (request.getAttribute("WEB_TITLE") == null || ((String)request.getAttribute("WEB_TITLE")).length() == 0) request.setAttribute("WEB_TITLE", clientDefine.WEB_TITLE);
			request.setAttribute("WEB_NAME", clientDefine.WEB_NAME);
			Map<String, String[]> map = request.getParameterMap();
			if (map != null) {
				for (String key : map.keySet()) {
					String[] values = map.get(key);
					if (values != null && values.length > 0) request.setAttribute(key, values.length == 1 ? values[0] : values);
				}
			}
			if (element != null) {
				for (String key : element.keySet()) request.setAttribute(key, element.get(key));
			}
			String output = request.getParameter("output");
			if (output == null || !output.equals("json")) {
				request.getRequestDispatcher(jspPath).forward(request, response);
			} else {
				request.removeAttribute("output");
				Map<String, Object> data = new HashMap<>();
				Enumeration<String> attribute = request.getAttributeNames();
				while (attribute.hasMoreElements()) {
					Object obj = attribute.nextElement();
					data.put(obj.toString(), request.getAttribute(obj.toString()));
				}
				PrintWriter out = response.getWriter();
				out.write(JSON.toJSONString(data, SerializerFeature.WriteMapNullValue));
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//success
	public static void success() {
		success(null);
	}
	public static void success(Object data) {
		success(data, "SUCCESS");
	}
	public static void success(Object data, String msg) {
		success(data, msg, 0, null);
	}
	public static void success(Object data, String msg, int msg_type) {
		success(data, msg, msg_type, null);
	}
	public static void success(Object data, String msg, int msg_type, Map<String, Object> element) {
		String gourl = request.getParameter("gourl");
		String goalert = request.getParameter("goalert");
		if (gourl == null || gourl.length() == 0) gourl = (String) request.getSession().getAttribute("gourl");
		request.getSession().removeAttribute("gourl");
		if (gourl != null && gourl.length() > 0) {
			script(goalert, gourl);
			return;
		}
		if ((data instanceof String) && ((String)data).endsWith(".jsp")) {
			msg = (String) data;
			data = null;
		}
		if (!isAjax() && msg.endsWith(".jsp")) {
			display(msg, element);
		} else {
			try {
				Map<String, Object> json = new HashMap<>();
				if (data == null) {
					request.removeAttribute("edition");
					request.removeAttribute("function");
					request.removeAttribute("config");
					Map<String, Object> datas = new HashMap<>();
					Enumeration<String> attribute = request.getAttributeNames();
					while (attribute.hasMoreElements()) {
						Object obj = attribute.nextElement();
						datas.put(obj.toString(), request.getAttribute(obj.toString()));
					}
					json.put("data", datas);
				} else {
					json.put("data", data);
				}
				json.put("msg_type", msg_type);
				json.put("msg", msg.endsWith(".jsp") ? "SUCCESS" : msg);
				json.put("error", 0);
				if (element != null) json.putAll(element);
				PrintWriter out = response.getWriter();
				out.write(JSON.toJSONString(json, SerializerFeature.WriteMapNullValue));
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//error
	public static void error() {
		error("DATA ERROR");
	}
	public static void error(String msg) {
		error(msg, 0);
	}
	public static void error(String msg, String url) {
		script(msg, url);
	}
	public static void error(String msg, int msg_type) {
		String gourl = request.getParameter("gourl");
		if (gourl != null && gourl.length() > 0) {
			historyBack(msg);
			return;
		}
		if (!isAjax()) {
			try {
				switch (msg_type) {
					case -100:case -9:response.sendRedirect("/login");break;
					case -1:response.sendRedirect("/");break;
					default:script(msg, "javascript:history.back()");
				}
				//request.setAttribute("tips", "THIS PAGE MAY BE ON MARS.");
				//request.getRequestDispatcher("/api/error.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (msg_type == -9 || msg_type == -100) msg_type = -10;
			Map<String, Object> json = new HashMap<>();
			json.put("msg_type", msg_type);
			json.put("msg", msg);
			json.put("error", 1);
			try {
				PrintWriter out = response.getWriter();
				out.write(JSON.toJSONString(json, SerializerFeature.WriteMapNullValue));
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

/*
//列出系统全部属性
Properties p = System.getProperties();
Set<Object> set = p.keySet();
for (Object key : set) { //利用增强for遍历
	System.out.println(key + ":" + p.get(key));
}
*/

/*
Class<? extends Object> tClass = t.getClass();
//得到所有属性
Field[] field = tClass.getDeclaredFields();
/**
 * 这里只需要 id 这个属性，所以直接取 field[0] 这
 * 一个，如果id不是排在第一位，自己取相应的位置，
 * 如果有需要，可以写成for循环，遍历全部属性
 *
//设置可以访问私有变量
field[0].setAccessible(true);
//获取属性的名字
String name = field[0].getName();
//将属性名字的首字母大写
name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
//整合出 getId() 属性这个方法
Method m = tClass.getMethod("get"+name);
//获取属性类型
String type = field[i].getGenericType().toString();
if (type.equals("class java.lang.String")) {
	String value = (String)m.invoke(t);
}
if (type.equals("class java.lang.Integer")) {
	Integer value = (Integer)m.invoke(t);
}
if (type.equals("class java.util.Date")) {
	Date value = (Date)m.invoke(t);
}
*/

/*
// 循环遍历Map的4中方法
Map<Integer, Integer> map = new HashMap<Integer, Integer>();
map.put(1, 2);
// 1. entrySet遍历，在键和值都需要时使用（最常用）
for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
	System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
}
// 2. 通过keySet或values来实现遍历,性能略低于第一种方式
// 遍历map中的键
for (Integer key : map.keySet()) {
	System.out.println("key = " + key);
}
// 遍历map中的值
for (Integer value : map.values()) {
	System.out.println("key = " + value);
}
// 3. 使用Iterator遍历
Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
while (it.hasNext()) {
	Map.Entry<Integer, Integer> entry = it.next();
	System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
}
*/

/*
//读取文件
try {
	File file = new File("D:/test/testIO.java");
	// 如果文件存在，读取文件中的内容，并在控制台输出
	if (file.exists()) {
		InputStream in = new FileInputStream(file);
		int a = 0;
		while ((a = in.read()) != -1) {
			System.out.print((char) a);
		}
		in.close();
	} else {
		// 如果文件不存在返回文件不存在
		System.out.println("文件不存在");
	}
} catch (FileNotFoundException e) {
	e.printStackTrace();
} catch (IOException e) {
	e.printStackTrace();
}
*/

