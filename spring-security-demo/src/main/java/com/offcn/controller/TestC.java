package com.offcn.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestC {

	@RequestMapping("/logina")
	public String getTest(){
		String name = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		System.out.println(name);
		return  "success.html";
	}
}
