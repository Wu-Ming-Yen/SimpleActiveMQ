
public class main {

	public static void main(String[] args) {
		
		//ActiveMQ Communication Check
		ReviceTopic revice = new ReviceTopic();
		Thread thread = new Thread(revice);
		thread.start();
		SendTopic send = new SendTopic();
		send.sendMessage("testing message 1");
		send.sendMessage("testing message 2");
		send.closeConnection();
		
	}
	
}
