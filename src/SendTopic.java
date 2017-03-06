import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class SendTopic {
	
	private static ConnectionFactory factory = null;
	private static Connection connection = null;
	private static Session session = null;
	private static Destination destination = null;
	private static MessageProducer producer = null;
		
	public SendTopic(){
		try {
			factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL); //  "tcp://localhost:61616"
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createTopic("SAMPLETOPIC");
			//destination = session.createQueue("SAMPLEQUEUE");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg) {
		try {
			producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage();
			message.setText(msg);
			producer.send(message);
			System.out.println("Sent: " + message.getText());
			message.clearBody();
			message.clearProperties();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection(){
		try {
			if(producer != null) producer.close();
			if(session != null) session.close();
			if(connection != null){
				connection.stop();
				connection.close();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
