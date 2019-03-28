import javax.naming.Context;
import javax.jms.*;

/**
   This simple consumer consumes a single text message on a Queue. The Queue
   is command-line argument 0. Run the corresponding QProducer to send the message.
 */
public class QConsumer {
    public static void main(String args[]) throws Exception {
	try {
	    Connection connection = JMSHelperActiveMQ.getJMSConnection();
	    connection.start();
	    //connection.setExceptionListener(this);
	
	    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	
	    Destination destination = session.createQueue(args[0]);
	    MessageConsumer consumer = session.createConsumer(destination);
	    // Wait for a message
	    Message message = consumer.receive(5000);
 
	    if (message instanceof TextMessage) {
		TextMessage textMessage = (TextMessage) message;
		String text = textMessage.getText();
		System.out.println("Received: " + text);
	    } else {
		System.out.println("Received: " + message);
	    }
	
	    consumer.close();
	    session.close();
	    connection.close();
	} catch (Exception e) {
	    System.out.println("Caught: " + e);
	    e.printStackTrace();
	}
    }
}

