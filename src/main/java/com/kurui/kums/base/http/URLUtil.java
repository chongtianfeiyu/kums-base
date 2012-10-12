package com.kurui.kums.base.http;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;

public class URLUtil {

	public URLUtil() {
	}

	public static StringBuffer getResponseBodyAsGet(String urlStr) {
		StringBuffer sb = new StringBuffer();
		StringBuffer destStr;
		try {
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(50000);
			InputStream is = con.getInputStream();
			BufferedReader in = null;
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			destStr = new StringBuffer();
			for (String inputLin = ""; (inputLin = in.readLine()) != null;)
				sb.append(inputLin);

			destStr.append(sb.toString());
			return destStr;
		} catch (Exception e) {
			e.printStackTrace();
			return sb;
		}

	}

	public static InputStream getInputStreamAsGet(String urlStr) {
		InputStream is;
		try {
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(50000);
			is = con.getInputStream();
			return is;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getResponseBodyAsPost(String strURL,
			NameValuePair nvp[]) {
		HttpClient client;
		PostMethod post;
		client = new HttpClient();
		post = new PostMethod(strURL);
		client.getParams().setSoTimeout(50000);
		post.setRequestBody(nvp);
		try {
			int statusCode = client.executeMethod(post);
			if (statusCode != 200)
				return (new StringBuilder("post.getStatusLine():   ")).append(
						post.getStatusLine()).toString();
			byte in[];
			in = post.getResponseBody();
			post.releaseConnection();
			if (in.length == 0)
				return "";
			return new String(in);
		} catch (Exception e) {
			post.releaseConnection();
			System.err.println((new StringBuilder("Failed:")).append(
					e.getMessage()).toString());
			e.printStackTrace();
			return e.getMessage();
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

		String str = getResponseBodyAsPost(strURL, data);
		return str.toString();
	}

	public static String getResponseBodyAsPost(String host, int port,
			String path, NameValuePair nvp[], Protocol protocol) {
		HttpClient client;
		PostMethod post;
		client = new HttpClient();
		client.getHostConfiguration().setHost(host, port, protocol);
		post = new PostMethod(path);
		client.getParams().setSoTimeout(0x186a0);
		post.setRequestBody(nvp);
		try {
			int statusCode = client.executeMethod(post);
			if (statusCode != 200)
				return (new StringBuilder("post.getStatusLine():   ")).append(
						post.getStatusLine()).toString();
			byte in[];
			in = post.getResponseBody();
			post.releaseConnection();
			if (in.length == 0)
				return "";
			return new String(in);
		} catch (Exception e) {
			post.releaseConnection();
			System.err.println((new StringBuilder("Failed:")).append(
					e.getMessage()).toString());
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public static String getResponseBodyAsGet(String host, int port,
			String path, Protocol protocol) {
		HttpClient client;
		PostMethod post;
		client = new HttpClient();
		client.getHostConfiguration().setHost(host, port, protocol);
		post = new PostMethod(path);
		client.getParams().setSoTimeout(0x186a0);
		try {
			int statusCode = client.executeMethod(post);
			if (statusCode != 200)
				return (new StringBuilder("post.getStatusLine():   ")).append(
						post.getStatusLine()).toString();
			byte in[];
			in = post.getResponseBody();
			post.releaseConnection();
			if (in.length == 0)
				return "";
			return new String(in);
		} catch (Exception e) {
			post.releaseConnection();
			System.err.println((new StringBuilder("Failed:")).append(
					e.getMessage()).toString());
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public static InputStream getInputStreamAsPost(String host, int port,
			String path, NameValuePair nvp[], Protocol protocol) {
		HttpClient client;
		PostMethod post;
		client = new HttpClient();
		client.getHostConfiguration().setHost(host, port, protocol);
		post = new PostMethod(path);
		client.getParams().setSoTimeout(0x186a0);
		post.setRequestBody(nvp);
		try {
			int statusCode = client.executeMethod(post);
			if (statusCode != 200)
				return null;
			InputStream is = post.getResponseBodyAsStream();
			return is;
		} catch (Exception e) {
			post.releaseConnection();
			System.err.println((new StringBuilder("Failed:")).append(
					e.getMessage()).toString());
			e.printStackTrace();
			return null;
		}
	}

	public static InputStream getInputStreamAsPost(String strURL,
			NameValuePair nvp[]) {
		HttpClient client;
		PostMethod post;
		client = new HttpClient();
		client.getParams().setSoTimeout(50000);
		post = new PostMethod(strURL);
		post.setRequestBody(nvp);
		InputStream in;
		try {
			client.executeMethod(post);
			in = post.getResponseBodyAsStream();
			post.releaseConnection();
			return in;
		} catch (Exception e) {
			post.releaseConnection();
			System.err.println((new StringBuilder("Failed to download file."))
					.append(e.getMessage()).toString());
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String args[]) {
		BigDecimal a = new BigDecimal("19");
		BigDecimal b = new BigDecimal("7");
		BigDecimal c = a.divide(b, 4, 2);
		System.out.println((new StringBuilder("--------a---=")).append(c)
				.toString());
		System.out.println((new StringBuilder("-------b----=")).append(
				c.doubleValue()).toString());
		System.out.println((new StringBuilder("-------t----=")).append(
				c.toString()).toString());
		BigDecimal g = new BigDecimal("19");
		BigDecimal h = new BigDecimal("3");
		BigDecimal i = g.divide(h, 2, 2);
		System.out.println((new StringBuilder("--------a---=")).append(i)
				.toString());
		System.out.println((new StringBuilder("-------b----=")).append(
				i.doubleValue()).toString());
		System.out.println((new StringBuilder("-------t----=")).append(
				i.toString()).toString());
	}
}
