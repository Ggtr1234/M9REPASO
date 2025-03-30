package P1.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class WelcomeServer {
    private ServerSocket serverSocket;
    private Socket socket;
    private static Map<String, HashSet<String>> ipsRegistrades = new HashMap<>();
    private static List<String> missatgesServidor = new ArrayList<String>(Arrays.asList("Hola1","Hola2","Hola3","Hola4","Hola5"));
    private String WELCOME_MESSAGE = "";
    public static final int PORT = 6000;
    private OutputStream output;
    private InputStream input;
    private static int acumulator = 0;
    protected static int numeroVictoria = calcularNumero();
    private static DataInputStream dis;
    private static DataOutputStream dos;

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

    private static int calcularNumero() {
        Random rand = new Random();
        return rand.nextInt(1,20);
    }

    private void gestionaNovaConexio(Socket socket) {
        try {
            System.out.println("Conexion desde: " + socket.getInetAddress() + ":" + socket.getPort());
            System.out.println("Num: " + numeroVictoria);
            WELCOME_MESSAGE = enviarNouMissatge(String.valueOf(socket.getInetAddress()));
            output = socket.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(output));
            bufferedWriter.write(WELCOME_MESSAGE);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            int number;
            boolean acertado = false;


            while (!acertado) {
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                number = dis.readInt();
                System.out.println("Cliente envió: " + number);

                if (number == numeroVictoria) {
                    dos.writeUTF("Victoria");
                    dos.flush();
                    acertado = true;
                } else {
                    dos.writeUTF("Fallo, intenta de nuevo.");
                    dos.flush();
                }
            }

            System.out.println("El cliente acertó, cerrando conexión.");
            bufferedWriter.write("Victoria");
            bufferedWriter.newLine();
            bufferedWriter.flush();

            number = dis.readInt();
            acumulator += number;
            System.out.println("Serv recibe: " + number);
            System.out.println("Serv acumulado: " + acumulator);

            dos.writeInt(acumulator);
            dos.flush();

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean verificarNumero(int numero){
        if(numero == numeroVictoria){
            return true;
        }else {
            return false;
        }
    }

    private String enviarNouMissatge(String ip){
        ipsRegistrades.putIfAbsent(ip, new HashSet<>());
        HashSet<String> mensajesRecibidos = ipsRegistrades.get(ip);
        List<String> mensajesDisponibles = new ArrayList<>(missatgesServidor);
        mensajesDisponibles.removeAll(mensajesRecibidos);
        if (mensajesDisponibles.isEmpty()) {
            return "No hay más mensajes nuevos disponibles para tu IP.";
        }
        String nuevoMensaje = mensajesDisponibles.get(new Random().nextInt(mensajesDisponibles.size()));
        mensajesRecibidos.add(nuevoMensaje);
        return nuevoMensaje;
    }

    private void tancaConexio() {
        try{
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
