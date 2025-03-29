package P1.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class WelcomeClient {
    private Socket socket;
    private InputStream input;
    private OutputStream output;

    public void connecta(String ipAddress, int port) {
        try{
            socket = new Socket(ipAddress,port);
            input = socket.getInputStream();
            output = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String llegeixMissatge(){
        InputStreamReader reader = new InputStreamReader(input);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String missatge = "";
        try{
            missatge = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return missatge;
    }

    public static void main(String[] args) {
        WelcomeClient client = new WelcomeClient();
        client.connecta("127.0.0.1",6000);
        System.out.println("Connectat amb el servidor.");
        String missatgeDelServidor = client.llegeixMissatge();
        System.out.println("El servidor diu: " + missatgeDelServidor);
    }
}
