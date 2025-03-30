package P2.client;
import java.io.*;
import java.net.Socket;

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

    public void desconnecta(){
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

