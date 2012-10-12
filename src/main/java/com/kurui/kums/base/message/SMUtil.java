package com.kurui.kums.base.message;

import com.kurui.kums.base.http.URLUtil;

import java.net.URLEncoder;

public class SMUtil {

	private static String url = "http://chineseserver.net:3388/CellServer/SmsAPI2/SendMessage.jsp";
	private static String username = "7696";
	private static String password = "an654321";

	public SMUtil() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		password = password;
	}

	public static void testSendMessage() {
		String retinfo = "";
		String userid = "7696";
		String password = "an654321";
		String destnumbers = "13192229256";
		String msg = "test3��Ǯ�ŵ���֧��ƽ̨��";
		String sendtime = "";
		try {
			if (userid.length() > 0 && password.length() > 0
					&& destnumbers.length() > 0 && msg.length() > 0) {
				String qurl = (new StringBuilder(
						"http://chineseserver.net:3388/CellServer/SmsAPI2/SendMessage.jsp?userid="))
						.append(userid).append("&password=").append(password)
						.append("&destnumbers=").append(destnumbers).append(
								"&msg=")
						.append(URLEncoder.encode(msg, "UTF-8")).append(
								"&sendtime=").append(sendtime).toString();
				System.out.println(qurl);
				String s = URLUtil.getResponseBodyAsGet(qurl).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String send(String mobiles, String content) {
		String result;
		result = "";
		content = (new StringBuilder(String.valueOf(content))).append("--酷睿天下")
				.toString();
		if (username.length() <= 0 || password.length() <= 0
				|| mobiles.length() <= 0 || content.length() <= 0)
			return "0";
		String sendtime = "";
		String qurl;
		try {
			qurl = (new StringBuilder(String.valueOf(url))).append("?userid=")
					.append(username).append("&password=").append(password)
					.append("&destnumbers=").append(mobiles).append("&msg=")
					.append(URLEncoder.encode(content, "UTF-8")).append(
							"&sendtime=").append(sendtime).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		result = URLUtil.getResponseBodyAsGet(qurl).toString();

		System.out.println(result);
		if (result.indexOf("成功") > 0) {
			return "1";
		} else {
			return "0";
		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		url = url;
	}

	public static void main(String args[]) {
		send("15018863259", "�й������и��¼����ҵ԰��������18¥ 0755-��");
	}

}
