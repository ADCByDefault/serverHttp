package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;

/**
 * Hello world!
 *
 */
public class App {

    private static void sendFile(Socket s, String file, String extFile) {
        try {
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            String filet = file;
            file = "./root" + file;
            try {
                File myObj = new File(file);
                FileInputStream in = new FileInputStream(file);

                System.out.println("cerco file:" + file);
                out.writeBytes("HTTP/1.1 200 OK" + "\n");
                System.out.println(myObj.length());
                out.writeBytes("Content-Length: " + myObj.length() + "\n");
                out.writeBytes("Server: Java HTTP Server from Benve: 1.0" + "\n");
                out.writeBytes("Date: " + new Date(0) + "\n");
                out.writeBytes("Content-Type: " + getContentType(extFile) + "\n");
                out.writeBytes("\n");
                byte[] buf = new byte[8192];
                int n;
                while ((n = in.read(buf)) != -1) {
                    out.write(buf, 0, n);
                }
                System.out.println("----ho inviato:" + file);
                in.close();
            } catch (Exception e) {
                System.out.println("l'estensione è:" + extFile);
                if (extFile.equals("") || extFile.isEmpty()) {
                    redirect(out, filet + "/");
                }
                out.writeBytes("HTTP/1.1 404 Not found" + "\n");
            }
            out.close();
        } catch (Exception e) {
            System.out.println("errore");
        }
    }

    private static String getContentType(String ext) {
        String type = "text/plain";
        switch (ext) {
            case "html":
                type = "text/html;" + "charset=utf-8";
                break;
            case "jpg":
            case "png":
            case "jpeg":
            case "webp":
                type = "image/" + ext;
                break;
            case "ico":
                type = "favicon/ico";
                break;
            case "css":
                type = "text/html";
                break;
            case "js":
                type = "application/js";
                break;
            default:
                type = "text/plain";
                break;
        }
        return type;
    }

    private static void redirect(DataOutputStream out, String location) {
        try {
            out.writeBytes("HTTP/1.1 301 Move Permanently" + "\n");
            out.writeBytes("Location: " + location + "\n");
            out.writeBytes("\n");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true) {
                Socket client = serverSocket.accept();
                BufferedReader inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String in = "";
                String percorso = "";
                String ext = "";
                do {
                    in = inDalClient.readLine();
                    System.out.println(in);
                    if (percorso.isEmpty() && (!in.isEmpty() || !in.equals(null))) {
                        percorso = in.split(" ")[1];
                    }
                    if (in.isEmpty() || in.equals(null)) {
                        break;
                    }
                } while (true);
                System.out.println("----percorso:" + percorso + ext);
                if (percorso.endsWith("/")) {
                    percorso = percorso + "index.html";
                }
                try {
                    ext = percorso.split("\\.")[1];
                } catch (Exception e) {
                    System.out.println("errore nel trovare l'estensione");
                }
                System.out.println("----percorso:" + percorso);
                sendFile(client, percorso, ext);
            }
        } catch (Exception e) {

            System.out.println("errore" + e);
        }
    }
}