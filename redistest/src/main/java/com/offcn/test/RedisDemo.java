package com.offcn.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.offcn.pojo.Person;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDemo {

	public static void main(String[] args) {
		
			ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
			
			JedisPool jp = (JedisPool) app.getBean("jedisPool");
			
			Jedis jedis = jp.getResource();
			// 取值（手动添加过数据，key是name）
			String name = jedis.get("name");
			
			System.out.println(name);
			
			
	}

}
