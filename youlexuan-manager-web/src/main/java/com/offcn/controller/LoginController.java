package com.offcn.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@RequestMapping("/getName")
	@ResponseBody
	public Map getName(HttpSession session){
		SecurityContext context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();
		Map<String, String> map = new HashMap<>();
		map.put("name", name);
		return map;
	}
}
