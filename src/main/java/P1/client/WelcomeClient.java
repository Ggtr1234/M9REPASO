package P1.client;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class WelcomeClient {
    private static Socket socket;
    private InputStream input;
    private OutputStream output;
    private static DataOutputStream dos;

    public void connecta(String ipAddress, int port) {
        try{
            socket = new Socket(ipAddress,port);
            input = socket.getInputStream();
            output = socket.getOutputStream();
            dos = new DataOutputStream(output);
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

    public int llegeixNumero() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            return dataInputStream.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int enviaNumero(){

        try {
            int num =new Random().nextInt(1,20);
            dos.writeInt(num);
            dos.flush();
            return num;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        WelcomeClient client = new WelcomeClient();
        client.connecta("192.168.1.140",6000);
        System.out.println("Connectat amb el servidor.");

        String missatgeDelServidor = client.llegeixMissatge();
        System.out.println("El servidor diu: " + missatgeDelServidor);
        String respuesta;
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        do {
            System.out.print("Ingresa un número: ");
            int numero = client.enviaNumero();
            dos.writeInt(numero);
            dos.flush();
            respuesta = dis.readUTF();
            System.out.println("Servidor: " + respuesta);
        } while (!respuesta.equals("Victoria"));
        dos.close();

        client.enviaNumero();
        System.out.println("num que envia serv del acum: " + client.llegeixNumero());

    }
}
