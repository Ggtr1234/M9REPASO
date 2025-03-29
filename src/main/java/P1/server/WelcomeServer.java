package P1.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WelcomeServer {
    private ServerSocket serverSocket;
    private Socket socket;
    private static List<String> missatgesServidor = new ArrayList<String>(Arrays.asList("Hola1","Hola2","Hola3","Hola4","Hola5"));
    private String WELCOME_MESSAGE = "";
    public static final int PORT = 6000;
    private OutputStream output;
    private InputStream input;

    public void iniciaServei(){
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado en el puerto: " + PORT);
            while(true){
                socket = serverSocket.accept();
                gestionaNovaConexio(socket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void gestionaNovaConexio(Socket socket) {
        try{
            System.out.println("Conexion desde: " + socket.getInetAddress() + ":" + socket.getPort());
            WELCOME_MESSAGE = missatgesServidor.get(new Random().nextInt(missatgesServidor.size()));
            output = socket.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(output);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(WELCOME_MESSAGE);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            tancaConexio();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void tancaConexio() {
        try{
            this.output.close();
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        WelcomeServer server = new WelcomeServer();
        server.iniciaServei();
    }

}
