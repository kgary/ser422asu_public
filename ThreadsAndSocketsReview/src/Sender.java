import java.net.*;

class Sender {

	public static void main(String[] args) throws Exception {

		String msg = args[0];
		InetAddress group = InetAddress.getByName("225.6.7.8");
		MulticastSocket socket = null;
		
		try { 
			socket = new MulticastSocket(2222);
		socket.joinGroup(group);	
		DatagramPacket packet =	
			new DatagramPacket(msg.getBytes(), msg.length(), group, 2222);
 		socket.send(packet);
		socket.leaveGroup(group);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception exc) {
					System.out.println(("Unable to close socket properly"));
				}
			}
		}
	}
}
