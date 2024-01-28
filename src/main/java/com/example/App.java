package com.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

                out.writeBytes("HTTP/1.1 200 OK" + "\n");
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
                } else {
                    out.writeBytes("HTTP/1.1 404 Not found" + "\n");
                }
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
            case "json":
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
        Studente s1 = new Studente("s1", "c1", new Date());
        Studente s2 = new Studente("s2", "c2", new Date());
        Studente s3 = new Studente("s3", "c3", new Date());
        Classe c = new Classe("5", "DIA", "est-5");
        ArrayList<Studente> a = new ArrayList<>();
        a.add(s1);
        a.add(s2);
        a.add(s3);
        c.setStudenti(a);
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
                System.out.println("----percorso:" + percorso);
                if (percorso.endsWith("/")) {
                    percorso = percorso + "index.html";
                }
                try {
                    ext = percorso.split("\\.")[1];
                } catch (Exception e) {
                    ext = "";
                    System.out.println("errore nel trovare l'estensione");
                }
                if (percorso.equals("/classe.json")) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        mapper.writeValue(new File("./root/classe.json"), c);
                    } catch (StreamWriteException e) {
                        System.out.println("errore nel creare il file");
                    }
                }
                sendFile(client, percorso, ext);
            }
        } catch (Exception e) {
            System.out.println("errore" + e);
        }
    }
}