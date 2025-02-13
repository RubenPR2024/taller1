package servidorClienteUDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClienteUDP {
	public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 56789;

        DatagramSocket socket = null;
        Scanner scanner = new Scanner(System.in);

        try {
            socket = new DatagramSocket();

            InetAddress serverInetAddress = InetAddress.getByName(serverAddress);

            while (true) {
                System.out.print("Escribe un mensaje: ");
                String message = scanner.nextLine();

                // Enviar el mensaje al servidor
                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverInetAddress, port);
                socket.send(sendPacket);
                if ("Adiós".equalsIgnoreCase(message)) {
                    System.out.println("Conexión cerrada.");
                    break;
                }
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Respuesta del servidor: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            scanner.close();
        }
    }
}
