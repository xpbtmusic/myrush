package com.example.mynews.service;

import java.io.InputStream;


public interface INgnHttpClientService extends INgnBaseService
{
	/**
	 * 
	 * 方法说明：HTTP GET请求
	 * 
	 * @param uri
	 *            URI
	 * @return String
	 */
	String get(String uri);

	/**
	 * 
	 * 方法说明：HTTP Post请求
	 * 
	 * @param uri
	 *            URL地址
	 * @param contentUTF8
	 *            发送的内容
	 * @param contentType
	 *            编码
	 * @return String
	 */
	String post(String uri, String contentUTF8, String contentType);

	/**
	 * 
	 * 方法说明：Post请求，传参
	 * 
	 * @param uri
	 *            接口地址
	 * @param params
	 *            传递的参数
	 * @return String
	 */
	String post5(String uri, String[] params);

	/**
	 * 
	 * 方法说明：HTTP Post请求，发送文本
	 * 
	 * @param uri
	 *            URl地址
	 * @param contentUTF8
	 *            编码
	 * @param contentType
	 *            内容
	 * @return String
	 */

	String post2(String uri, String contentUTF8, String contentType);

	/**
	 * 
	 * 方法说明：得到输入流
	 * 
	 * @param uri
	 *            URI
	 * @return InputStream
	 */
	public InputStream getBinary(String uri);

	/**
	 * 
	 * 方法说明：得到文件
	 * 
	 * @param uri
	 *            void
	 */
	void getFile(String uri);

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
	public String put(String uri, String contentUTF8, String contentType);

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
	InputStream post3(String uri, String contentUTF8, String contentType);

	/**
	 * 
	 * 方法说明：post请求
	 * 
	 * @param uri
	 *            URL地址
	 * @return String
	 */
	public String post4(String uri);
}
