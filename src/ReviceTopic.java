import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ReviceTopic implements Runnable {
	
	private static ConnectionFactory factory = null;
	private static Connection connection = null;
	private static Session session = null;
	private static Destination destination = null;
	private static MessageConsumer consumer = null;
	
	public ReviceTopic(){
		try {
			factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL); //  "tcp://localhost:61616"
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createTopic("SAMPLETOPIC");
			//destination = session.createQueue("SAMPLEQUEUE");
			consumer = session.createConsumer(destination);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			while(true){
				Message message = consumer.receive();
				if(message != null){
					if (message instanceof TextMessage) {
						TextMessage text = (TextMessage) message;
						System.out.println("Message is : " + text.getText());
					}
				}else{
					continue;
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} finally{
			try{
				if(consumer != null) consumer.close();
				if(session != null) session.close();
				if(connection != null){
					connection.stop();
					connection.close();
				}
			}catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
