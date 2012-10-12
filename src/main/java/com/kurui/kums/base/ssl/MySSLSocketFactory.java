package com.kurui.kums.base.ssl;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.security.*;
import javax.net.SocketFactory;
import javax.net.ssl.*;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

public class MySSLSocketFactory
	implements ProtocolSocketFactory
{

	private SSLContext sslcontext;
	private KeyManager kms[];
	private TrustManager tms[];

	public MySSLSocketFactory(KeyManager kms[], TrustManager tms[])
	{
		sslcontext = null;
		this.kms = null;
		this.tms = null;
		this.kms = kms;
		this.tms = tms;
	}

	private SSLContext createSSLContext()
	{
		SSLContext sslcontext = null;
		try
		{
			sslcontext = SSLContext.getInstance("SSL");
			sslcontext.init(kms, tms, new SecureRandom());
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (KeyManagementException e)
		{
			e.printStackTrace();
		}
		return sslcontext;
	}

	private SSLContext getSSLContext()
	{
		if (sslcontext == null)
			sslcontext = createSSLContext();
		return sslcontext;
	}

	public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
		throws IOException, UnknownHostException
	{
		return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
	}

	public Socket createSocket(String host, int port)
		throws IOException, UnknownHostException
	{
		return getSSLContext().getSocketFactory().createSocket(host, port);
	}

	public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort)
		throws IOException, UnknownHostException
	{
		return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
	}

	public Socket createSocket(String host, int port, InetAddress localAddress, int localPort, HttpConnectionParams params)
		throws IOException, UnknownHostException, ConnectTimeoutException
	{
		if (params == null)
			throw new IllegalArgumentException("Parameters may not be null");
		int timeout = params.getConnectionTimeout();
		SocketFactory socketfactory = getSSLContext().getSocketFactory();
		if (timeout == 0)
		{
			return socketfactory.createSocket(host, port, localAddress, localPort);
		} else
		{
			Socket socket = socketfactory.createSocket();
			java.net.SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
			java.net.SocketAddress remoteaddr = new InetSocketAddress(host, port);
			socket.bind(localaddr);
			socket.connect(remoteaddr, timeout);
			return socket;
		}
	}

	static 
	{
		System.out.println("loading SSL");
	}
}
