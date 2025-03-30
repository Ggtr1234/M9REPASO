package P3.client;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class MainURL {



    public static void urlReader() throws Exception {

        URL oracle = new URL("http://www.google.com/");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }

    public static void parseURL() throws Exception {
        Scanner sc = new Scanner(System.in);
        String url = sc.nextLine();
        URL aURL = new URL(url);

        System.out.println("protocol = " + aURL.getProtocol());
        System.out.println("authority = " + aURL.getAuthority());
        System.out.println("host = " + aURL.getHost());
        System.out.println("port = " + aURL.getPort());
        System.out.println("path = " + aURL.getPath());
        System.out.println("query = " + aURL.getQuery());
        System.out.println("filename = " + aURL.getFile());
        System.out.println("ref = " + aURL.getRef());
    }
}
