package com.kurui.kums.base.message;

import com.kurui.kums.base.encrypt.MD5;
import com.kurui.kums.base.http.URLUtil;
import com.kurui.kums.base.util.RegularUtil;
import java.net.URLEncoder;

public class _SMUtil {

	private static String url = "http://http.asp.sh.cn/MT.do";
	private static String username = "vfasky";
	private static String password = "19851024";
	private static String keyword = "A75Y9B37S83M";
	
	public static void main(String args1[]) {
	}

	public _SMUtil() {
	}

	private static String process(String mobiles) {
		String temp = "";
		if (RegularUtil.isMobiles(mobiles)) {
			temp = (new StringBuilder(String.valueOf(mobiles.substring(0, 8))))
					.append(
							mobiles.substring(mobiles.length() - 10, mobiles
									.length())).append(keyword).toString();
			temp = MD5.encrypt(temp).toUpperCase();
			return temp;
		} else {
			return "";
		}
	}

	public static String send(String mobiles, String content) {
		String temp = process(mobiles);
		try {
			content = URLEncoder.encode(content, "GBK");
		} catch (Exception exception) {
		}
		if (temp.trim().equals("")) {
			return "-100";
		} else {
			StringBuffer str = URLUtil.getResponseBodyAsGet((new StringBuilder(
					String.valueOf(url))).append("?Username=").append(username)
					.append("&Password=").append(password).append("&Mobile=")
					.append(mobiles).append("&Content=").append(content)
					.append("&Keyword=").append(temp).toString());
			return str.toString();
		}
	}


	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		_SMUtil.url = url;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		_SMUtil.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		_SMUtil.password = password;
	}

	public static String getKeyword() {
		return keyword;
	}

	public static void setKeyword(String keyword) {
		_SMUtil.keyword = keyword;
	}



}
