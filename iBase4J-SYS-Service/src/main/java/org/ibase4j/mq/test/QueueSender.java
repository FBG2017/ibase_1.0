package org.ibase4j.mq.test;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
/**
 * 队列消息发送类
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
//MQ的queue服务提供者 provider
public class QueueSender {
//	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;
	/*private JmsTemplate jmsQueueTemplate;
	public void setJmsQueueTemplate(JmsTemplate jmsQueueTemplate) {
		this.jmsQueueTemplate = jmsQueueTemplate;
	}*/

//	其实报错信息已经说得很明确了,在autoware时，
//由于有两个类实现了EmployeeService接口，所以Spring不知道应该绑定哪个实现类，所以抛出了如上错误。

//	这个时候就要用到@Qualifier注解了，qualifier的意思是合格者，
//通过这个标示，表明了哪个实现类才是我们所需要的，我们修改调用代码，
//添加@Qualifier注解，需要注意的是@Qualifier的参数名称必须为我们之前定义@Service注解的名称之一！
	
	
	/**
	 * 发送一条消息到指定的队列（目标）
	 * 
	 * @param queueName 队列名称
	 * @param message 消息内容
	 */
	
	public void send(String queueName, final Serializable message) {
		System.out.println("队列名称"+queueName);
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(message);
			}
		});
	}
	/*public void receive(String queueName){
		Message message = jmsTemplate.receive(queueName);
		System.out.println("传递过来的消息为"+message);
		
	}*/
	
}
