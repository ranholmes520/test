package com.ranholmes.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

/**
 * 
 * @author Ku_wan
 *
 */
public class HttpDownload {
	
	private static HttpClient httpClient =null;
	
	public static HttpClient getHttpClient(){
		if(httpClient == null){
			httpClient = new DefaultHttpClient();
		}
		return httpClient;
	}
	
	public static String getJSONData(String url)
			throws ClientProtocolException, IOException {
		String result = "";
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = getHttpClient();
		HttpResponse httpResponse = null;

		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				InputStream inputStream = httpEntity.getContent();
				result = convertStreamToString(inputStream);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
			httpResponse = null;
		}
		return result;

	}
	
	public static String sendPostHttpRequest(String url,List<NameValuePair> params){
		//设置HttpPost连接对象(HttpPost 即为 HttpRequest)
		HttpPost httpPost = new HttpPost(GlobalVariate.SERVERADDRESS+url);
//		params.add(new BasicNameValuePair("userId", Storage.getString(context, "userId")));
		try {
			if(params!=null){
				//设置字符集
				HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");
				//
				httpPost.setEntity(httpEntity);
				//使用DefaultHttpClient 为 HttpClient
			}
			HttpClient httpClient = getHttpClient();
			//取得HttpResponse 对象
			HttpResponse httpResponse = httpClient.execute(httpPost);
			//HttpStatus.SC_OK 表示连接成功
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				//取得返回的字符串
				String result ="";
				if(httpResponse.getEntity() != null){
					InputStream is = httpResponse.getEntity().getContent();
					result = convertStreamToString(is);
//					result = result.replaceAll("\r", "");
					return result.trim();
				} 
			}else {
				System.out.println("fail the request");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("错误"+e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"),// 防止模拟器上的乱码
					512 * 1024);
		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			Log.e("DataProvier convertStreamToString", e.getLocalizedMessage(),e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
