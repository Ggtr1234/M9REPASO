package P2.server;
import java.io.IOException;
import java.net.*;
import java.util.*;

public class ServidorMultifil {
    private ServerSocket serverSocket;
    private int PORT = 65000;
    protected static int contador = 0;
    protected static Map<String, HashSet<String>> ipsRegistrades = new HashMap<>();
    protected static List<String> missatgesServidor = new ArrayList<String>(Arrays.asList("Hola1","Hola2","Hola3","Hola4","Hola5"));
    protected static String WELCOME_MESSAGE = "";
    protected static int acumulator = 0;

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

