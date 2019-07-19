package com.offcn.demo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MqProducer {

	public static void main(String[] args) throws Exception {
		// jetty 默认启动mq的端口是8161   
		// mq的默认端口61616
		String url = "tcp://10.10.84.201:61616";
		// 创建连接
		ConnectionFactory  connectionFactory = new ActiveMQConnectionFactory(url);
		// 获取连接对象
		Connection connection = connectionFactory.createConnection();
		// 通过connection获取session对象 
		connection.start();
		// 第一个参数  是否开启事务   ，  消息的模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 获取队列
		Queue queue = session.createQueue("java_1210");
		// 创建消息生产者
		MessageProducer producer = session.createProducer(queue);
		// 初始化
		TextMessage message = session.createTextMessage("来了老弟");
		// 发送消息
		producer.send(message);
		// 关闭资源
		producer.close();
		session.close();
		connection.close();
		
	}

}
