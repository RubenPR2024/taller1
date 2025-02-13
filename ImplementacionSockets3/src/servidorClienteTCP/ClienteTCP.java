package servidorClienteTCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteTCP {
	public static void main(String[] args) {
        String serverAddress = "localhost"; // Dirección IP del servidor
        int port = 56789; // Puerto del servidor

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Conexión establecida con el servidor.");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String message;
            while (true) {
                System.out.print("Escribe un mensaje: ");
                message = userInput.readLine();
                out.println(message);

                // Si el mensaje es "Adiós", terminamos la conexión
                if ("Adiós".equalsIgnoreCase(message)) {
                    System.out.println("Conexión cerrada.");
                    break;
                }

                // Mostrar la respuesta del servidor
                String response = in.readLine();
                System.out.println("Respuesta del servidor: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
