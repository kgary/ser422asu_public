import javax.jms.*;
import javax.naming.*;
import org.apache.log4j.BasicConfigurator;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DemoSubscriberModel implements javax.jms.MessageListener {

    private TopicSession pubSession;
    private TopicConnection connection;

    /* Establish JMS subscriber */
    public DemoSubscriberModel(String topicName, String username,
			       String password) throws Exception {

	// Obtain a JNDI connection
	InitialContext jndi = new InitialContext();
	// Look up a JMS connection factory

	TopicConnectionFactory conFactory = (TopicConnectionFactory)jndi.lookup("topicConnectionFactry");
	// Create a JMS connection
	connection = conFactory.createTopicConnection();
	connection.setClientID("ser422");  // this is normally done by configuration not programmatically

	// Look up a JMS topic
	Topic chatTopic = (Topic) jndi.lookup(topicName);

	TopicSession subSession = connection.createTopicSession(false,
								Session.AUTO_ACKNOWLEDGE);

	TopicSubscriber subscriber = subSession.createDurableSubscriber(chatTopic, "DemoSubscriberModel");
	// Set a JMS message listener
	// Start the JMS connection; allows messages to be delivered
	connection.start();

	//subscriber.setMessageListener(this);
	int count=0;
        while (count < 10) {
            System.out.println("waiting for message...");
            TextMessage msg = (TextMessage) subscriber.receive();
            System.out.println(++count + "Received message: " + msg.getText());
        }
	connection.close();
    }
    
    /*
     * A client can register a message listener with a consumer. A message
     * listener is similar to an event listener. Whenever a message arrives at
     * the destination, the JMS provider delivers the message by calling the
     * listener's onMessage method, which acts on the contents of the message.
     */
    public void onMessage(Message message) {
	try {
	    TextMessage textMessage = (TextMessage) message;
	    String text = textMessage.getText();
	    System.out.println(text);
	} catch (JMSException jmse) {
	    jmse.printStackTrace();
	}
    }

        public static void main(String[] args) {
	// uncomment this line for verbose logging to the screen
	// BasicConfigurator.configure();

	try {
	    if (args.length != 3)
		System.out.println("Please Provide the topic name,username,password!");

	    DemoSubscriberModel demo = new DemoSubscriberModel(args[0], args[1], args[2]);
	    BufferedReader commandLine = new java.io.BufferedReader(new InputStreamReader(System.in));
	    
	    // closes the connection and exit the system when 'exit' enters in
	    // the command line
	    while (true) {
		String s = commandLine.readLine();
		if (s.equalsIgnoreCase("exit")) {
		    demo.connection.close();
		    System.exit(0);
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
