package edu.javacourse.grn;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class GrnSystem {
    public static void main(String[] args) {
        GrnSystem grn = new GrnSystem();
        grn.start();
    }

    private void start() {
        try {
            ServerSocket ses = new ServerSocket(7777); // setup incoming socket

            System.out.println(this.getClass() + " is listening at " + ses);
            System.out.println("\n");
            System.out.println("---");

            while (true) {
                Socket socket = ses.accept(); // set socket to accept

                processRequest(socket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void processRequest(Socket socket) throws IOException {
        StringBuilder sb = new StringBuilder(); // create new string
        Reader br = new InputStreamReader(socket.getInputStream()); // reader from the IS from the socket, which get IS
        char[] request = new char[256]; // create array of 16 bytes
        int count = br.read(request); // count qty of data inside the "request"
        while (count != -1) { // while counter of bytes is not negative, do
            sb.append(new String(request, 0, count));
            if (sb.toString().endsWith("</person>")) {
                break;
            }
            count = br.read(request);
        }

        System.out.println(sb.toString()); // show what's read
        System.out.println("---");
        boolean result;
        String message;
        try {
            GrnPerson person = buildPerson(sb.toString()); // calling for GrnPerson, sending sb.toString(), requesting 'person'
            System.out.println(person);
            result=checkPerson(person);
            message = "CONFIRMED";
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            message = "GRN System ERROR: " + e.getMessage();
        }
        OutputStream os = socket.getOutputStream(); // stream to be sent through socket
        os.write(("<?xml version=\"1.0\" ?><answer>" +
                "<result>" + result + "</result>" +
                "<message>" + message + "</message>" +
                "</answer>").getBytes());
        socket.close();
    }

    private boolean checkPerson(GrnPerson person) {
        return true;
    }

    private GrnPerson buildPerson(String s) throws Exception {
        byte[] buffer = s.getBytes();
        ByteInputStream bis = new ByteInputStream(buffer, buffer.length);

        GrnPerson person = new GrnPerson();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(bis);
        NodeList childNodes = doc.getFirstChild().getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if(node instanceof Element){
                if ("surName".equals(node.getNodeName())) {
                 person.setSurName(node.getTextContent().trim());
              }
              if ("givenName".equals(node.getNodeName())) {
                  person.setGivenName(node.getTextContent().trim());
              }
              if ("patronymic".equals(node.getNodeName())) {
                  person.setPatronymic(node.getTextContent().trim());
              }
               if ("dateOfBirth".equals(node.getNodeName())) {
                   SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                   person.setDateOfBirth(sdf.parse(node.getTextContent()));
               }
            }
        }
        return person;
    }


}
