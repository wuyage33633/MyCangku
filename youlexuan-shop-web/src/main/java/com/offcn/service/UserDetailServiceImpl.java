package com.offcn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.offcn.pojo.TbSeller;

/**
 * spring-security自定义的认证类
 * @author luoyanpeng
 *
 */
public class UserDetailServiceImpl implements UserDetailsService{


	private SellerService serllerService;
	public void setSerllerService(SellerService serllerService) {
		this.serllerService = serllerService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 用户的角色和权限的集合
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("ROLE_SELLER"));
		// 根据用户名查询用户
		TbSeller tbSeller = serllerService.findOne(username);
		if(tbSeller != null){
			if(tbSeller.getStatus().equals("1")){
				return new User(username, tbSeller.getPassword(), list);
			}else{
				return null;
			}
		}else{
			return null;
		}
		
	}

}
