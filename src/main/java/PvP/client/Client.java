package PvP.client;
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

    public String llegeixMissatgeDelServidor() throws IOException {
        String missatge = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        missatge = reader.readLine();
        return missatge;
    }

    public String enviaMissatgeAServidor(String msg) throws IOException {
        String missatge = msg;
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        writer.write(missatge);
        writer.newLine();
        writer.flush();
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
