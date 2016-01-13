import java.net.*;

class Listener {

	public static void main(String[] args) throws Exception {

		InetAddress group = InetAddress.getByName("225.6.7.8");
		MulticastSocket socket = null; 
		
		try {
			socket = new MulticastSocket(2222);
			socket.joinGroup(group);	

			String msgS;
			byte[] msg = new byte[1000];

			do {
				DatagramPacket packet = new DatagramPacket(msg, msg.length);

				socket.receive(packet);

				msgS = new String(msg, 0, packet.getLength());
				System.out.println("Message: *" + msgS + "*");
			} while (!msgS.equals("stop"));

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
