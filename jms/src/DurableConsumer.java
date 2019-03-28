import javax.naming.Context;
import javax.jms.*;

public class DurableConsumer {
    public static void main(String args[]) throws Exception {

        String topicName = "topic1";

        Context context = JMSHelper.getJMSContext();
        TopicConnection connection = JMSHelper.getTopicConnection(context);
        connection.start();
        TopicSession session =
             connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);

        Topic topic = (Topic)context.lookup(topicName);
        TopicSubscriber subscriber =
                        session.createDurableSubscriber(topic, "DoNotDrop");

        while (true) {
            System.out.println("waiting for message...");
            ObjectMessage objMsg = (ObjectMessage) subscriber.receive();
            String msg = (String) objMsg.getObject();
            System.out.println("Received message: " + msg.toString());
        }

        // Tell JMS to stop saving messages
        // But we never get here in this example...
        //session.unsubscribe("DoNotDrop");
    }
}

