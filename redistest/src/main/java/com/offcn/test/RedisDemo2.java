package com.offcn.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.offcn.pojo.Person;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDemo2 {

	public static void main(String[] args) {
		
			ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis2.xml");
			RedisTemplate template = (RedisTemplate) app.getBean("redisTemplate");
			
			
//			Person p = new Person();
//			p.setName("xiaoze");
//			
//			// 存执
//			template.boundValueOps("person").set(p);
//			
//			// 删除
////			template.delete("name");
//			
//			// 取值
//			Person pp = (Person) template.boundValueOps("person").get();
//			System.out.println(pp);
			
			
			// list类型
			List<Person> list = new ArrayList<Person>();
			for(int i = 0; i < 5; i++){
				Person p = new Person();
				p.setId(i);
				p.setName("xiaoze"+i);
				list.add(p);
			}
			
			// 存list类型数据
//			template.boundListOps("plist").leftPush(list);
			// 取list数据
//			List list2 = template.boundListOps("plist").range(0, 0);
//			System.out.println(list2.get(0));
			
//			template.boundListOps("name1").leftPush("haha");  
//			template.boundListOps("name1").leftPush("xixi");
//	
//			List list3 = template.boundListOps("name1").range(0, 0);
//			System.out.println(list3);
			
		
			// 存取hast类型
//			template.boundHashOps("mapcontent").put(1, list);
//			template.boundHashOps("mapcontent").put(2, list);
//			
//			
////			template.boundHashOps("mapcontent").delete(1);  // 按指定对象删除
//			template.delete("mapcontent"); // 全部删除
//			
//			List<Person> list4 = (List<Person>) template.boundHashOps("mapcontent").get(1);
//			List<Person> list5 = (List<Person>) template.boundHashOps("mapcontent").get(2);
//			System.out.println(list4.size());
//			System.out.println(list5.size());
			
			
			
	}

}
