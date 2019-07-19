package com.offcn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offcn.pojo.User;

@Controller
public class UserController {

	@RequestMapping("/find")
	@ResponseBody
	public List<User> getTest(){
		List<User> list = new ArrayList<User>();
		for(int i=0; i < 10; i ++){
			User u = new User();
			u.setName("xiaocang"+i);
			u.setAge(i);
			list.add(u);
		}
		return list;
	}
}
