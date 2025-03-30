package P2.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainClient {
    public static void main(String[] args) {
        List<Thread> clients = new ArrayList<Thread>();
        try {
            for (int i = 0; i < 5; i++) {
                Client client = new Client();
                Thread t = new Thread(){
                    public void run() {
                        try {
                            client.connectaAServidor("127.0.0.1", 65000);
                            String missatge = client.llegeixMissatge();
                            System.out.println("El servidor diu: " + missatge);
                            client.enviaNumero();
                            System.out.println("Acumulador: " + client.llegeixNumero());
                            client.desconnecta();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                clients.add(t);
            }
            for (Thread t : clients) {
                Thread.sleep (10);
                t.start();
            }
        } catch (InterruptedException e) {
            System.out.println("Error connectant o llegint al servidor");
        }

    }

}
