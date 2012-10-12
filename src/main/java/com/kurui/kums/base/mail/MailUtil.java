package com.kurui.kums.base.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import com.kurui.kums.base.http.URLUtil;
import com.kurui.kums.base.encrypt.BASE64;
import com.kurui.kums.base.ssl.SSLURLUtil;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.protocol.Protocol;

public class MailUtil
{

	private static String url = "";
	private String mailCode;
	private String mails;

	public MailUtil()
	{
		mailCode = "";
		mails = "";
	}

	public void setMailCode(String mailCode)
	{
		this.mailCode = mailCode;
	}

	public void setMails(String mails)
	{
		this.mails = mails;
	}

	public static String send(String mailCode, String mails, HashMap params)
	{
		ArrayList list = new ArrayList();
		StringBuffer temp = new StringBuffer();
		Iterator it = params.entrySet().iterator();
		list.add(new NameValuePair("thisAction", "send"));
		list.add(new NameValuePair("code", mailCode));
		list.add(new NameValuePair("mails", mails));
		String key = "";
		String value = "";
		while (it.hasNext()) 
		{
			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
			key = entry.getKey().toString();
			value = entry.getValue().toString();
			if (key != null && !key.equals("") && value != null && !value.equals(""))
			{
				NameValuePair nvp = new NameValuePair(key, BASE64.encrypt(value));
				list.add(nvp);
			}
		}
		NameValuePair data[] = new NameValuePair[list.size()];
		for (int i = 0; i < list.size(); i++)
			data[i] = (NameValuePair)list.get(i);

		String str = URLUtil.getResponseBodyAsPost(url, data);
		return str.toString();
	}

	public static String send(String subject, String mailCode, String mails, HashMap params)
	{
		ArrayList list = new ArrayList();
		StringBuffer temp = new StringBuffer();
		Iterator it = params.entrySet().iterator();
		list.add(new NameValuePair("thisAction", "send"));
		list.add(new NameValuePair("subject", BASE64.encrypt(subject)));
		list.add(new NameValuePair("code", mailCode));
		list.add(new NameValuePair("mails", mails));
		String key = "";
		String value = "";
		while (it.hasNext()) 
		{
			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
			key = entry.getKey().toString();
			value = entry.getValue().toString();
			if (key != null && !key.equals("") && value != null && !value.equals(""))
			{
				NameValuePair nvp = new NameValuePair(key, BASE64.encrypt(value));
				list.add(nvp);
			}
		}
		NameValuePair data[] = new NameValuePair[list.size()];
		for (int i = 0; i < list.size(); i++)
			data[i] = (NameValuePair)list.get(i);

		String str = URLUtil.getResponseBodyAsPost(url, data);
		return str.toString();
	}

	public static String sslSend(String subject, String mailCode, String mails, HashMap params, Protocol protocol)
	{
		ArrayList list = new ArrayList();
		StringBuffer temp = new StringBuffer();
		Iterator it = params.entrySet().iterator();
		list.add(new NameValuePair("thisAction", "send"));
		list.add(new NameValuePair("subject", BASE64.encrypt(subject)));
		list.add(new NameValuePair("code", mailCode));
		list.add(new NameValuePair("mails", mails));
		String key = "";
		String value = "";
		while (it.hasNext()) 
		{
			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
			key = entry.getKey().toString();
			value = entry.getValue().toString();
			if (key != null && !key.equals("") && value != null && !value.equals(""))
			{
				NameValuePair nvp = new NameValuePair(key, BASE64.encrypt(value));
				list.add(nvp);
			}
		}
		NameValuePair data[] = new NameValuePair[list.size()];
		for (int i = 0; i < list.size(); i++)
			data[i] = (NameValuePair)list.get(i);

		String str = SSLURLUtil.getResponseBodyAsPost(url, data, protocol);
		return str.toString();
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		url = url;
	}

	private static String getHostFromUrl(String url)
	{
		int p1 = url.indexOf("://");
		int p2 = url.indexOf("/", 10);
		if (p1 > 0 && p2 > 0 && p2 - p1 > 5)
			return url.substring(p1 + 3, p2);
		else
			return "";
	}

}
