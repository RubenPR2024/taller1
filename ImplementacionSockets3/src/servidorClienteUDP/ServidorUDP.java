package servidorClienteUDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServidorUDP {
	public static void main(String[] args) {
        int port = 56789;
        DatagramSocket socket = null;
        byte[] receiveData = new byte[1024];
        int messageCount = 0;

        try {
            socket = new DatagramSocket(port);
            System.out.println("Servidor iniciado y esperando mensajes en el puerto " + port + "...");

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                messageCount++;

                System.out.println("Mensaje recibido: " + message);
                if ("Adiós".equalsIgnoreCase(message)) {
                    System.out.println("Conexión cerrada.");
                    break;
                }
                String response = "Mensaje " + messageCount + ": " + message;
                byte[] sendData = response.getBytes();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
