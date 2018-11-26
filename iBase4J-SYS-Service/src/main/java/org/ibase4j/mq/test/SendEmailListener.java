package org.ibase4j.mq.test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.support.email.Email;
import org.ibase4j.core.util.EmailUtil;

/**
 * 发送邮件队列
 * 
 * @author ShenHuaJie
 * @version 2016年6月14日 上午11:00:53
 */
//这是mq的监听器。  实现了MessageListener接口.
//mq的接收端，接收到provider发送的消息。就开始发送邮件。
public class SendEmailListener implements MessageListener {
	private final Logger logger = LogManager.getLogger();

	public void onMessage(Message message) {
		try {
			/*Email email = (Email) ((ObjectMessage) message).getObject();
			logger.info("将发送邮件至：" + email.getSendTo());
			EmailUtil.sendEmail(email);*/
			TextMessage textMessage = (TextMessage)message;
                System.out.println("这是什么消息"+textMessage.getText());
		} catch (JMSException e) {
			logger.error(e);
		}
	}
}
