package PvP.server;

import java.io.IOException;
import java.net.*;

public class ServidorMultifil {
    private ServerSocket serverSocket;
    private int PORT = 65000;
    protected static String cadena = "";

    public void inicia() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Iniciat servidor al port "
                + serverSocket.getLocalPort());
        while (true){
            Socket socket = serverSocket.accept();
            try {
//                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                System.out.println("MENSAJE QUE LLEGA AL SERVER: " + entrada.readLine());
//                System.out.println(cadena);
                Gestor gestor = new Gestor(socket);
                new Thread(gestor).start();

            }catch (IOException e){
                System.out.println("Error creant gestor pel client ");
            }
        }
    }
}

