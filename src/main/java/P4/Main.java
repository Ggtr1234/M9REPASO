package P4;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try{
            File file = new File("src/img.jpg");
            File[] files = new File[]{file};
            if (!file.exists()) {
                System.out.println("El archivo no existe.");
                return;
            }

            for (int i = 0; i < 2; i++) {
                GestorEnviaCorreu gestorEnviaCorreu = new GestorEnviaCorreu();
                gestorEnviaCorreu.enviarCorreo("jllaberia@iespoblenou.org","Jose Padre", files,"JOSE PADRE -> " + String.valueOf(i));
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}