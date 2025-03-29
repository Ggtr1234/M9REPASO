package P2.server;
import java.io.IOException;
import java.net.*;

public class ServidorMultifil {
    private ServerSocket serverSocket;
    private int PORT = 65000;
    protected static int contador = 0;

    public void inicia() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Iniciat servidor al port "
                + serverSocket.getLocalPort());
        while (true){
            Socket socket = serverSocket.accept();
            try {
                Gestor gestor = new Gestor(socket);
                new Thread(gestor).start();
            }catch (IOException e){
                System.out.println("Error creant gestor pel client ");
            }
        }
    }
}

