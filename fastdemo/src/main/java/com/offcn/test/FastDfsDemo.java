package com.offcn.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDfsDemo {

	public static void main(String[] args) throws Exception, MyException {
		String ip = "10.10.84.229";
		// 加载配置文件（tracker的地址）
		ClientGlobal.init("./src/main/resources/fastdfs.conf");
		// 创建tracker的客户端
		TrackerClient trackerClient = new TrackerClient();
		// 创建trackerServer
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		// 获取storageclient
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 第一个参数  文件    第二个参数是文件 后缀   第三个是文件名称集合
		String[] strs = storageClient.upload_appender_file("c://1.jpg", "jpg", null);
		String url = "";
		for(String s : strs){
			url += "/"+s;
		}
		System.out.println(ip+url);
	}

}
