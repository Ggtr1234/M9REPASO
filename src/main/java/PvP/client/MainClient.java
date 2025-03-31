package PvP.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainClient {
    public static void main(String[] args) throws InterruptedException {
//        Client client = new Client();
//        try {
//            for (int i = 0; i < 10; i++) {
//                client.connectaAServidor("127.0.0.1", 65000);
//                client.enviaMissatgeAServidor("Client" + i );
//                String missatge = client.llegeixMissatgeDelServidor();
//                System.out.println("Client " + i+": " + missatge);
//            }
//        } catch (IOException e) {
//            System.out.println("Error connectant o llegint al servidor");
//        }
//        client.desconnecta();

        List<Thread> clients = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            Client client = new Client();
            int finalI = i;
            Thread t  = new Thread(){
                public void run(){
                    try {
                        client.connectaAServidor("127.0.0.1", 65000);
                        client.enviaMissatgeAServidor("Client" + finalI);
                        String missatge = client.llegeixMissatgeDelServidor();
                        System.out.println("Client " + finalI +": " + missatge);
                        client.desconnecta();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            clients.add(t);
        }
        for (Thread t : clients) {
            t.start();
        }
    }
}
