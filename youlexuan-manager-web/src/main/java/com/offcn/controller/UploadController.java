package com.offcn.controller;

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
