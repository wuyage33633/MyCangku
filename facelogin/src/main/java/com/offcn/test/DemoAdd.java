package com.offcn.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.offcn.util.AccesTokenUtil;
import com.offcn.util.Base64Utils;

public class DemoAdd {

	public static void main(String[] args) throws IOException {
		String url = "https://aip.baidubce.com/oauth/2.0/token";
		String grant_type = "client_credentials";
		String client_id = "GpQa5um62nbl9BvFfcTWGIoF";
		String client_secret = "mFe77Bz88nOoUYpmk6rE5Pb2auxNnEvM";
		// 获取access_token
		String token = AccesTokenUtil.getAccessToken(url, grant_type, client_id, client_secret);
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost("https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add");
		// 设置请求头
		post.setHeader("Content-Type","application/json");
		
		// 获取图片进行登录
		String imageStr = Base64Utils.getImageStr("c://1.jpg");
		// 给post请求传递参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("access_token", token));
		list.add(new BasicNameValuePair("image", imageStr));
		list.add(new BasicNameValuePair("image_type", "BASE64"));
		list.add(new BasicNameValuePair("group_id", "java0312"));
		list.add(new BasicNameValuePair("user_id", "rose"));
		
		post.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
		// 请求
		CloseableHttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		String str = EntityUtils.toString(entity, "utf-8");
		System.out.println(str);
	}
}
