package PvP.server;
import java.io.*;
import java.net.Socket;
import java.util.Random;


public class Gestor implements Runnable{
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public Gestor(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("Creat nou client ");
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    @Override
    public void run() {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String missatge = "";
            missatge = reader.readLine();
            if (missatge != null) {
                synchronized (ServidorMultifil.cadena) {
                    ServidorMultifil.cadena += "|" + missatge;
                    writer.write(ServidorMultifil.cadena);
                    writer.newLine();
                    writer.flush();
                    try {
                        Thread.sleep(new Random().nextInt(10, 20)); // Retardo aleatorio
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Error en sleep: " + e.getMessage());
                    }
                }
            }


            System.out.println(ServidorMultifil.cadena);
            this.socket.shutdownInput();
            this.socket.shutdownOutput();
            this.socket.close();
        } catch (IOException e) {
            System.out.println("Error escrivint al client ");
        }
        System.out.println("FI atenent al client ");
    }
}

