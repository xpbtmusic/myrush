package com.example.mynews.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.example.mynews.service.INgnHttpClientService;
import com.example.mynews.utils.ILog;
import com.example.mynews.utils.StringUtils;

import android.util.Log;




public class HttpService extends NgnBaseService implements INgnHttpClientService
{
	/**
	 * Log标签
	 */
	private static final String TAG = HttpService.class.getCanonicalName();
	/**
	 * 连接超时时间
	 */
	private static final int TIMEOUTCONNECTION = 30000;
	/**
	 * 请求时间
	 */
	private static final int TIMEOUTSOCKET = 50000;
	/**
	 * 数据库路径
	 */
	private static final String DATABASE_PATH = "data/data/com.example.mynews/databases/base.db";
	/**
	 * 数据库路径
	 */
	private static final String DATABASE_PATH_STRING = "data/data/com.example.mynews/databases";

	/**
	 * HTTP客户端
	 */
	private HttpClient mClient;

	/**
	 * 
	 * 方法说明：构造方法
	 */
	public HttpService()
	{
		super();
	}

	/**
	 * 方法说明:开启服务
	 * 
	 * @return boolean
	 */
	public boolean start()
	{
		ILog.d(TAG, "Starting...");

		if (mClient == null) {
			mClient = new DefaultHttpClient();

			final HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, TIMEOUTCONNECTION);
			HttpConnectionParams.setSoTimeout(params, TIMEOUTSOCKET);
			((DefaultHttpClient) mClient).setParams(params);

			return true;
		}

		ILog.d(TAG, "Already started");
		return false;
	}

	/**
	 * 方法说明:停止服务
	 * 
	 * @return boolean
	 */
	public boolean stop()
	{

		if (mClient != null) {
			mClient.getConnectionManager().shutdown();
		}
		mClient = null;
		ILog.d(TAG, "Stopped...");
		return true;
	}

	/**
	 * 方法说明:GET请求
	 * 
	 * @param uri
	 *            URL地址
	 * @return String
	 */
	public String get(String uri)
	{
		try {
			HttpGet getRequest = new HttpGet(uri);
			final HttpResponse resp = mClient.execute(getRequest);
			if (resp != null) {
				return getResponseAsString(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 方法说明:POST请求
	 * 
	 * @param uri
	 *            URL地址
	 * @param params
	 *            用户名，密码，MD5
	 * 
	 * @return String
	 */
	public String post5(String uri, String[] params)
	{
		String result = null;
		try {
			HttpPost postRequest = new HttpPost(uri);
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			for (int i = 0; i < params.length; i++) {
				if (!StringUtils.isNull(params[i])) {
					ILog.d(TAG, params[i]);
					if (0 == i) {
						params1.add(new BasicNameValuePair("userID", params[0]));
					} else if (1 == i) {
						params1.add(new BasicNameValuePair("pwd", params[1]));
					} else if (i == 2) {
						params1.add(new BasicNameValuePair("md5", params[2]));
					}
					postRequest.setEntity(new UrlEncodedFormEntity(params1, HTTP.UTF_8));
				}
			}

			final HttpResponse resp = mClient.execute(postRequest);
			if (resp != null) {
				return getResponseAsString(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 
	 * 方法说明：HTTP Post请求
	 * 
	 * @param uri
	 *            URl地址
	 * @param contentUTF8
	 *            发送的内容
	 * @param contentType
	 *            编码
	 * @return String
	 */
	public String post(String uri, String contentUTF8, String contentType)
	{
		String result = null;
		try {
			HttpPost postRequest = new HttpPost(uri);

			final StringEntity entity = new StringEntity(contentUTF8, "UTF-8");
			if (contentType != null) {
				entity.setContentType(contentType);
			}
			postRequest.setEntity(entity);
			final HttpResponse resp = mClient.execute(postRequest);
			if (resp != null) {
				return getResponseAsString(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 方法说明:得到文件
	 * 
	 * @param uri
	 *            URL地址
	 */
	public void getFile(String uri)
	{
		FileOutputStream fos = null;
		InputStream inputStream = null;
		try {
			File jhPath = new File(DATABASE_PATH);
			if (jhPath.exists()) {

			} else {
				File path = new File(DATABASE_PATH_STRING);
				if (path.mkdir()) {
					ILog.d(TAG, "创建成功");
				} else {

					ILog.d(TAG, "创建失败");

				}
				;
				inputStream = get2(uri);
				// 用输出流写到SDcard上面
				fos = new FileOutputStream(DATABASE_PATH);
				// 创建byte数组 用于1KB写一次
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = inputStream.read(buffer)) != -1) {
					fos.write(buffer, 0, count);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			try {
				if (fos != null) {
					fos.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * 
	 * 方法说明：HTTP GET请求
	 * 
	 * @param uri
	 *            URI
	 * @return String
	 */
	public InputStream get2(String uri)
	{
		try {
			HttpGet getRequest = new HttpGet(uri);
			final HttpResponse resp = mClient.execute(getRequest);
			int codes = resp.getStatusLine().getStatusCode();
			Log.d(TAG, codes + "=文件下载===");
			if (resp != null) {
				return getResponseInputStream(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 方法说明：HTTP Post请求，发送文本
	 * 
	 * @param uri
	 *            URL地址
	 * @param contentUTF8
	 *            编码
	 * @param contentType
	 *            内容
	 * @return String
	 */
	public String post2(String uri, String contentUTF8, String contentType)
	{
		String result = null;
		try {
			HttpPost postRequest = new HttpPost(uri);
			final StringEntity entity = new StringEntity(contentUTF8, "UTF-8");
			if (contentType != null) {
				entity.setContentType(contentType);
			}
			postRequest.setEntity(entity);
			final HttpResponse resp = mClient.execute(postRequest);
			if (resp != null) {
				return getResponseAsString(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 方法说明:得到请求的二进制
	 * 
	 * @param uri
	 *            URL地址
	 * @return InputStream
	 */
	public InputStream getBinary(String uri)
	{
		try {
			HttpGet getRequest = new HttpGet(uri);
			final HttpResponse resp = mClient.execute(getRequest);
			if (resp != null) {
				return resp.getEntity().getContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param resp
	 *            HTTP响应
	 * @return String 请求返回的字符串
	 */
	public static String getResponseAsString(HttpResponse resp)
	{
		String result = "";
		InputStream in = null;
		BufferedReader reader = null;

		Reader readerin = null;
		try {
			int code = resp.getStatusLine().getStatusCode();
			ILog.d(TAG, code + "");
			if (code != 200) {
				return null;
			}
			in = resp.getEntity().getContent();
			readerin = new InputStreamReader(in);
			reader = new BufferedReader(readerin);
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line + "\n");
			}

			result = str.toString();
		} catch (IOException ex) {
			result = null;

		} finally {

			try {
				if (in != null) {
					in.close();
				}

			} catch (IOException e) {

				e.printStackTrace();
			}
			try {
				if (readerin != null) {
					readerin.close();
				}

			} catch (IOException e) {

				e.printStackTrace();
			}
			try {
				if (reader != null) {
					reader.close();
				}

			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		return result;
	}

	/**
	 * 
	 * 方法说明：得到输入流
	 * 
	 * @param resp
	 *            HTTP响应
	 * @return InputStream 输入流
	 */
	public static InputStream getResponseInputStream(HttpResponse resp)
	{
		InputStream in = null;
		try {
			int code = resp.getStatusLine().getStatusCode();
			ILog.d(TAG, code + "");
			if (code != 200) {
				return null;
			}
			in = resp.getEntity().getContent();

		} catch (Exception ex) {
			in = null;
		}
		return in;
	}

	/**
	 * 
	 * 方法说明：PUT请求
	 * 
	 * @param uri
	 *            URL地址
	 * @param contentUTF8
	 *            内容
	 * @param contentType
	 *            编码类型
	 * @return String
	 */
	public String put(String uri, String contentUTF8, String contentType)
	{
		String result = null;
		try {
			HttpPut putRequest = new HttpPut(uri);
			final StringEntity entity = new StringEntity(contentUTF8, "UTF-8");
			if (contentType != null) {
				entity.setContentType(contentType);
			}
			putRequest.setEntity(entity);
			final HttpResponse resp = mClient.execute(putRequest);
			if (resp != null) {
				return getResponseAsString(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * 方法说明：HTTP Post请求
	 * 
	 * @param uri
	 *            URL地址
	 * @param contentUTF8
	 *            编码
	 * @param contentType
	 *            要发送的内容
	 * @return InputStream
	 */
	public InputStream post3(String uri, String contentUTF8, String contentType)
	{

		InputStream result = null;
		try {
			HttpPost postRequest = new HttpPost(uri);
			final StringEntity entity = new StringEntity(contentUTF8, "UTF-8");
			if (contentType != null) {
				entity.setContentType(contentType);
			}
			postRequest.setEntity(entity);
			final HttpResponse resp = mClient.execute(postRequest);
			if (resp != null) {
				result = getResponseInputStream(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * 方法说明：post请求
	 * 
	 * @param uri
	 *            URL地址
	 * @return String
	 */
	public String post4(String uri)
	{
		ILog.d(TAG, uri);

		String code = null;
		try {
			HttpPost httpPost = new HttpPost(uri);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(httpPost);
			int codes = response.getStatusLine().getStatusCode();
			ILog.d("code", codes + "=11=");
			if (codes == 200) {
				code = EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return code;
	}
}
