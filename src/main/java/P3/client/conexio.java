package P3.client;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class conexio {
    public static void main(String[] args) throws Exception {
        URL oracle = new URL("http://www.google.com/");
        File file = new File("pagina.html");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null){
            writer.write(inputLine);
            writer.flush();
            System.out.println(inputLine);
        }
        writer.close();
        in.close();
    }
}
