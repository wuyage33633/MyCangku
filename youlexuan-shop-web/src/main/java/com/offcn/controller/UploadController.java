package com.offcn.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.offcn.common.Result;
import com.offcn.util.FastDFSClient;

@RestController
public class UploadController {

	@RequestMapping("/upload")
	public Result  getUpload(MultipartFile file){
		// 获取图片的名称
		String oldName = file.getOriginalFilename();  //  aaa.aa.jgp
		String extName = oldName.substring(oldName.lastIndexOf(".") + 1);
		String ip = "http://10.10.84.201/";
		// 加载配置文件（tracker的地址）
		try {
//			ClientGlobal.init("../src/main/resources/fastdfs.conf");
//			// 创建tracker的客户端
//			TrackerClient trackerClient = new TrackerClient();
//			// 创建trackerServer
//			TrackerServer trackerServer = trackerClient.getConnection();
//			StorageServer storageServer = null;
//			// 获取storageclient
//			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//			// 第一个参数  文件    第二个参数是文件 后缀   第三个是文件名称集合
//			String[] strs = storageClient.upload_appender_file(file.getBytes(), suf_name, null);
//			String url = "";
//			for(String s : strs){
//				url += "/"+s;
//			}
//			String imgurl = ip + url;
			
			FastDFSClient fs = new FastDFSClient("classpath:conf/fastdfs.conf");
			String path = fs.uploadFile(file.getBytes(), extName);
			String imgurl = ip + path;
			return new Result(true, imgurl);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(false, "上传失败");
		}
	}
}
