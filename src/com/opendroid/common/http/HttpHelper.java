package com.opendroid.common.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpHelper {

	/** The Constant CONNECTION_TIMEOUT. */
	public static final int CONNECTION_TIMEOUT = 10 * 1000;

	/** The Constant WAIT_RESPONSE_TIMEOUT. */
	public static final int WAIT_RESPONSE_TIMEOUT = 20 * 1000;

	/**
	 * Execute HTTP get.
	 * 
	 * @param url
	 *            the URL
	 * @return the string
	 */
	public static String executeHttpGet(String url) {
		return executeHttpGet(url, null);
	}

	/**
	 * Execute HTTP get.
	 * 
	 * @param url
	 *            The URL
	 * @param params
	 *            Use BasicNameValuePair to form List<NameValuePair>.<br/>
	 *            Pass null if no there are no parameters or use overloaded
	 *            version of the same.
	 * @return The response string.
	 */
	public static String executeHttpGet(String url, List<NameValuePair> params) {

		InputStream inputStream = null;
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		HttpResponse httpResponse = null;
		String strResponse = null;

		String strParams = "";
		if (params != null) {
			strParams = URLEncodedUtils.format(params, "utf-8");
		}

		try {
			httpClient = getThreadSafeHttpClient();
			httpGet = new HttpGet(new URI(url + strParams));
			httpResponse = httpClient.execute(httpGet);
			inputStream = httpResponse.getEntity().getContent();

			strResponse = convertStreamToString(inputStream);
		} catch (Exception e) {
			// TODO: handle exception
			// Log.e("Error in executing HttpGet");
			// Log.e(e);
			e.printStackTrace();
		} finally {

			/*
			 * The entity needs to be consumed completely in order to re-use the
			 * connection with keep-alive.
			 */
			if (httpResponse != null) {
				try {
					httpResponse.getEntity().consumeContent();
					// Log.i("***** HTTP RESPONSE ENTITY CONSUMED *****");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// Log.e("***** ERROR CONSUMING HTTP RESPONSE ENTITY *****");
					// Log.e(e);
					e.printStackTrace();
				}
			}

			/*
			 * Shut down HttpClient
			 */
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
				// Log.i("***** HTTP CONNECTION SHUTDOWN *****");
			}

			/*
			 * Close input stream
			 */
			if (inputStream != null) {
				try {
					inputStream.close();
					// Log.i("***** INPUTSTREAM CLOSED *****");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// Log.e("ERROR CLOSING INPUTSTREAM");
					// Log.e(e);
					e.printStackTrace();
				}
			}
		}

		return strResponse;
	}

	/**
	 * Execute HTTP post.
	 * 
	 * @param url
	 *            The URL
	 * @param params
	 *            Use BasicNameValuePair to form List<NameValuePair>.<br/>
	 *            Pass null if no there are no parameters or use overloaded
	 *            version of the same.
	 * @return The response string.
	 */
	public static String executeHttpPost(String url, List<NameValuePair> params) {

		InputStream inputStream = null;
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		HttpResponse httpResponse = null;
		String strResponse = null;

		try {
			httpClient = getThreadSafeHttpClient();
			httpPost = new HttpPost(new URI(url));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			httpResponse = httpClient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();

			strResponse = convertStreamToString(inputStream);
		} catch (Exception e) {
			// TODO: handle exception
			// Log.e("Error in executing HttpGet");
			// Log.e(e);
			e.printStackTrace();
		} finally {

			/*
			 * The entity needs to be consumed completely in order to re-use the
			 * connection with keep-alive.
			 */
			if (httpResponse != null) {
				try {
					httpResponse.getEntity().consumeContent();
					// Log.i("***** HTTP RESPONSE ENTITY CONSUMED *****");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// Log.e("***** ERROR CONSUMING HTTP RESPONSE ENTITY *****");
					// Log.e(e);
					e.printStackTrace();
				}
			}

			/*
			 * Shut down HttpClient
			 */
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
				// Log.i("***** HTTP CONNECTION SHUTDOWN *****");
			}

			/*
			 * Close input stream
			 */
			if (inputStream != null) {
				try {
					inputStream.close();
					// Log.i("***** INPUTSTREAM CLOSED *****");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// Log.e("ERROR CLOSING INPUTSTREAM");
					// Log.e(e);
					e.printStackTrace();
				}
			}
		}

		return strResponse;
	}

	/**
	 * Convert stream to string.
	 * 
	 * @param streamToConvert
	 *            the InputStream to convert
	 * @return the string or null
	 */
	public static String convertStreamToString(InputStream streamToConvert) {
		try {
			return new Scanner(streamToConvert).useDelimiter("\\A").next();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * Gets the thread safe HttpClient.
	 * 
	 * @return the thread safe HttpClient
	 */
	private static HttpClient getThreadSafeHttpClient() {

		HttpClient client = new DefaultHttpClient();

		ClientConnectionManager mgr = client.getConnectionManager();

		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, WAIT_RESPONSE_TIMEOUT);
		HttpConnectionParams.setTcpNoDelay(params, true);

		client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,
				mgr.getSchemeRegistry()), params);

		return client;
	}

	/**
	 * Gets the input stream from URL.
	 * 
	 * @param url
	 *            the URL
	 * @return the input stream from URL
	 * @throws IllegalStateException
	 *             the illegal state exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws URISyntaxException
	 *             the uRI syntax exception
	 */
	public static InputStream getInputStreamFromUrl(String url)
			throws IllegalStateException, IOException, URISyntaxException {

		InputStream content = null;

		HttpClient httpclient = getThreadSafeHttpClient();

		HttpGet httpGet = new HttpGet(new URI(url));
		HttpResponse response = httpclient.execute(httpGet);

		content = response.getEntity().getContent();

		response.getEntity().consumeContent();
		httpclient.getConnectionManager().shutdown();

		return content;
	}
}
