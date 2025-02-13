package servidorClienteTCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
	public static void main(String[] args) {
        int port = 56789;
        int messageCount = 0;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado y esperando conexiones en el puerto " + port + "...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Conexión establecida con el cliente " + clientSocket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                messageCount++;
                if ("Adiós".equalsIgnoreCase(message)) {
                    out.println("Conexión cerrada");
                    break;
                }
                out.println("Mensaje " + messageCount + ": " + message);
                System.out.println("Mensaje recibido: " + message);
            }

            clientSocket.close();
            System.out.println("Conexión cerrada");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
