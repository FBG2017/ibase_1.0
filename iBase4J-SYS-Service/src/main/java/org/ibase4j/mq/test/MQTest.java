package org.ibase4j.mq.test;



import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;


public class MQTest {
	private QueueSender queueSender;
	
	private ApplicationContext context;
	/*@Before
	public void loadxml(){
	}*/
     		
//	@Test
	public void  test_queue() {
		System.out.println("开启");
		context = new ClassPathXmlApplicationContext("A-config.xml");
		queueSender = (QueueSender) context.getBean("QueueSender.class");
		//发送消息
		queueSender.send("fbg_mq","你好fbg");
//		queueSender.receive("fbg_mq");
	}
}
