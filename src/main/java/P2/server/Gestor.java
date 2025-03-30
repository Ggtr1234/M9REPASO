package P2.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;


public class Gestor implements Runnable{
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private int numeroAsignat;

    public Gestor(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("Creat nou client ");
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    private String enviarNouMissatge(String ip){
        ServidorMultifil.ipsRegistrades.putIfAbsent(ip, new HashSet<>());
        HashSet<String> mensajesRecibidos = ServidorMultifil.ipsRegistrades.get(ip);
        List<String> mensajesDisponibles = new ArrayList<>(ServidorMultifil.missatgesServidor);
        mensajesDisponibles.removeAll(mensajesRecibidos);
        if (mensajesDisponibles.isEmpty()) {
            return "No hay más mensajes nuevos disponibles para tu IP.";
        }
        String nuevoMensaje = mensajesDisponibles.get(new Random().nextInt(mensajesDisponibles.size()));
        mensajesRecibidos.add(nuevoMensaje);
        return nuevoMensaje;
    }

    @Override
    public void run() {

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            System.out.println("Conexion desde: " + socket.getInetAddress() + ":" + socket.getPort());
            ServidorMultifil.WELCOME_MESSAGE = enviarNouMissatge(String.valueOf(socket.getInetAddress()));
            writer.write(ServidorMultifil.WELCOME_MESSAGE);
            writer.newLine();
            writer.flush();
            synchronized (ServidorMultifil.class) {
                ServidorMultifil.contador++;
                numeroAsignat = ServidorMultifil.contador;
                writer.write("Ets el client numero: " + numeroAsignat);
                writer.newLine();
                writer.flush();

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                int number = dis.readInt();
                System.out.println("Server reciba nuermo: " + number);
                DataOutputStream dos = new DataOutputStream(out);
                ServidorMultifil.acumulator += number;
                dos.writeInt(ServidorMultifil.acumulator);
                dos.flush();
            }


            this.socket.close();
        } catch (IOException e) {
            System.out.println("Error escrivint al client " + e.getMessage());e.printStackTrace();
        }
        System.out.println("FI atenent al client ");
    }
}
