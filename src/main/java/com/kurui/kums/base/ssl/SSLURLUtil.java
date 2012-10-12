package com.kurui.kums.base.ssl;

import com.kurui.kums.base.http.URLUtil;
import com.kurui.kums.base.encrypt.BASE64;
import com.kurui.kums.base.encrypt.MD5;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.protocol.Protocol;

public class SSLURLUtil {
	static Protocol myhttps;

	public SSLURLUtil() {
	}

	public static void loadKeyTrust(String keyStorePath, String keypass,
			String trustStorePath, String trustpass, String keyAlgorithm,
			String trustAlgorithm, String provider) {
		try {
			javax.net.ssl.KeyManager km[] = MyKeyManager.getKeyManager(
					keyStorePath, keypass, keyAlgorithm, provider);
			javax.net.ssl.TrustManager tm[] = MyX509TrustManager
					.getTrustManager(trustStorePath, trustpass, trustAlgorithm,
							provider);
			MySSLSocketFactory factory = new MySSLSocketFactory(km, tm);
			myhttps = new Protocol("https", factory, 443);
			Protocol.registerProtocol("https", myhttps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getResponseBodyAsPost(String strURL, HashMap params) {
		ArrayList list = new ArrayList();
		StringBuffer temp = new StringBuffer();
		Iterator it = params.entrySet().iterator();
		String key = "";
		String value = "";
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			key = entry.getKey().toString();
			value = entry.getValue().toString();
			if (key != null && !key.equals("") && value != null
					&& !value.equals("")) {
				NameValuePair nvp = new NameValuePair(key, value);
				list.add(nvp);
			}
		}
		NameValuePair data[] = new NameValuePair[list.size()];
		for (int i = 0; i < list.size(); i++)
			data[i] = (NameValuePair) list.get(i);

		String bankHost = getHostFromUrl(strURL);
		String str = URLUtil.getResponseBodyAsPost(bankHost, 443, strURL, data,
				myhttps);
		return str.toString();
	}

	public static String getResponseBodyAsPost(String strURL, HashMap params,
			Protocol https) {
		ArrayList list = new ArrayList();
		StringBuffer temp = new StringBuffer();
		Iterator it = params.entrySet().iterator();
		String key = "";
		String value = "";
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			key = entry.getKey().toString();
			value = entry.getValue().toString();
			if (key != null && !key.equals("") && value != null
					&& !value.equals("")) {
				NameValuePair nvp = new NameValuePair(key, value);
				list.add(nvp);
			}
		}
		NameValuePair data[] = new NameValuePair[list.size()];
		for (int i = 0; i < list.size(); i++)
			data[i] = (NameValuePair) list.get(i);

		String bankHost = getHostFromUrl(strURL);
		String str = URLUtil.getResponseBodyAsPost(bankHost, 443, strURL, data,
				https);
		return str.toString();
	}

	public static String getResponseBodyAsPost(String strURL,
			NameValuePair nvp[], Protocol https) {
		String bankHost = getHostFromUrl(strURL);
		return URLUtil.getResponseBodyAsPost(bankHost, 443, strURL, nvp, https);
	}

	public static String getResponseBodyAsGet(String strURL, Protocol https) {
		ArrayList list = new ArrayList();
		String bankHost = getHostFromUrl(strURL);
		String str = URLUtil.getResponseBodyAsGet(bankHost, 443, strURL, https);
		return str.toString();
	}

	public static InputStream getInputStreamAsPost(String strURL,
			HashMap params, Protocol https) {
		InputStream str;
		ArrayList list = new ArrayList();
		StringBuffer temp = new StringBuffer();
		Iterator it = params.entrySet().iterator();
		String key = "";
		String value = "";
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			key = entry.getKey().toString();
			value = entry.getValue().toString();
			if (key != null && !key.equals("") && value != null
					&& !value.equals("")) {
				NameValuePair nvp = new NameValuePair(key, value);
				list.add(nvp);
			}
		}
		NameValuePair data[] = new NameValuePair[list.size()];
		for (int i = 0; i < list.size(); i++)
			data[i] = (NameValuePair) list.get(i);

		String bankHost = getHostFromUrl(strURL);
		str = URLUtil.getInputStreamAsPost(bankHost, 443, strURL, data, https);
		return str;

	}

	public static String getHostFromUrl(String url) {
		int p1 = url.indexOf("://");
		int p2 = url.indexOf("/", 10);
		if (p1 > 0 && p2 > 0 && p2 - p1 > 5)
			return url.substring(p1 + 3, p2);
		else
			return "";
	}

	public static Map getSignUrl(String url, String param) {
		if (url == null || url == "")
			return null;
		String params[] = url.split("&");
		for (int i = 0; i < params.length; i++)
			System.out.println((new StringBuilder(String.valueOf(i))).append(
					"��").append(params[i]).toString());

		Map map = new HashMap();
		List list = new ArrayList();
		String element[] = (String[]) null;
		StringBuffer sb = new StringBuffer();
		String as[];
		int k = (as = params).length;
		for (int j = 0; j < k; j++) {
			String temp = as[j];
			if (temp != null) {
				element = temp.split("=");
				if (element != null && element.length == 2) {
					map.put(element[0], element[1]);
					list.add(element[0]);
				}
			}
		}

		Collections.sort(list);
		for (int i = 0; i < list.size(); i++)
			sb.append(list.get(i)).append("=").append(map.get(list.get(i)))
					.append("&");

		System.out.println(sb.toString());
		String singn_string = sb.toString();
		singn_string = singn_string.substring(0, singn_string.length() - 1);
		System.out.println((new StringBuilder("singn_string:")).append(
				singn_string).toString());
		String signQueryString = (new StringBuilder(String.valueOf(sb
				.toString()))).append("sign=").append(
				MD5.encrypt((new StringBuilder(String.valueOf(singn_string)))
						.append("djsakdh3245sdasd").toString())).toString();
		map
				.put("sign", MD5.encrypt((new StringBuilder(String
						.valueOf(singn_string))).append("djsakdh3245sdasd")
						.toString()));
		System.out.println(signQueryString);
		return map;
	}

	public static void main(String args[]) {
		String keyStorePath = "c:\\276628Keystore";
		String keypass = "1111111";
		String trustStorePath = "c:\\QMTrust";
		String trustpass = "changeit";
		String keyAlgorithm = "IBMX509";
		String trustAlgorithm = "IBMPKIX";
		String provider = "IBMJSSE";
		String strURL = "https://qm.qmpay.com/security/certificate.do?";
		String queryString = "thisAction=getRootCertInfo";
		try {
			String email = BASE64.encrypt("276628@qq.com", "UTF-8");
			email = URLEncoder.encode(
					(new StringBuilder(String.valueOf(email))).append("0")
							.toString(), "UTF-8");
			strURL = "https://qm.qmpay.com/security/certificate.do?";
			queryString = (new StringBuilder("thisAction=downloadP12&email="))
					.append(email).toString();
			HashMap map = (HashMap) getSignUrl(queryString, "thisAction");
			loadKeyTrust(keyStorePath, keypass, trustStorePath, trustpass,
					keyAlgorithm, trustAlgorithm, provider);
			System.out.println((new StringBuilder(String.valueOf(strURL)))
					.append(queryString).toString());
			String result = getResponseBodyAsPost((new StringBuilder(String
					.valueOf(strURL))).append(queryString).toString(), map);
			System.out.println((new StringBuilder("result=")).append(result)
					.toString());
		} catch (Exception exception) {
		}
	}
}
