package P2.client;
import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Client {
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public void connectaAServidor(String address, int port) throws IOException {
        socket = new Socket(address, port);
        System.out.println("Connectat a servidor " + socket.getRemoteSocketAddress());
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    public String llegeixMissatge(){
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String missatge = "";
        try{
            missatge = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return missatge;
    }

    public void enviaNumero(){
        DataOutputStream dos = new DataOutputStream(out);
        try {
            dos.writeInt(new Random().nextInt(5));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int llegeixNumero() {
        try {
            DataInputStream dataInputStream = new DataInputStream(in);
            int number = dataInputStream.readInt();
            return number;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void desconnecta(){
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

