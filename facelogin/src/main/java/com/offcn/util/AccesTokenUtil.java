package com.offcn.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class AccesTokenUtil {

	public static String getAccessToken(String url,String grant_type,String client_id,String client_secret ){
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url+"?grant_type="+grant_type+"&client_id="+client_id+"&client_secret="+client_secret);;
		try {
			CloseableHttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(entity, "utf-8");
			String access_token = (String) JSON.parseObject(json).get("access_token");
			return access_token;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
