package edu.javacourse.zags;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class RequestHandler implements Runnable {

    private Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            processRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processRequest() throws IOException {
        StringBuilder sb = new StringBuilder(); // create new string
        Reader br = new InputStreamReader(socket.getInputStream()); // reader from the IS from the socket, which get IS
        char[] request = new char[256]; // create array of 16 bytes
        int count = br.read(request); // count qty of data inside the "request"
        while (count != -1) { // while counter of bytes is not negative, do
            sb.append(new String(request, 0, count));
            if (sb.toString().endsWith("</marriage>") | sb.toString().endsWith("</child-check>")) {
                break;
            }
            count = br.read(request);
        }
        if (sb.toString().endsWith("</marriage>")) {
            System.out.println("Requested: 'marriage status'");
        } else if (sb.toString().endsWith("</child-check>")) {
            System.out.println("Requested: 'children status'");
        }
        System.out.println(sb.toString()); // show what's read

        boolean result;
        String message;
        try {
            ZagsPerson person = buildPerson(sb.toString()); // calling for ZagsPerson, sending sb.toString(), requesting 'person'

//            System.out.println(person);

            result = checkPerson(person);
            message = "ZAGS @ CONFIRMED";
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            message = "ZAGS System ERROR: " + e.getMessage();
        }
        OutputStream os = socket.getOutputStream(); // stream to be sent through socket
        os.write(("<?xml version=\"1.0\" ?><answer>" +
                "<result>" + result + "</result>" +
                "<message>" + message + "</message>" +
                "</answer>").getBytes());
        socket.close();
    }

    private boolean checkPerson(ZagsPerson person) throws InterruptedException {
        Thread.sleep(1000);
        return true;
    }

    private ZagsPerson buildPerson(String s) throws Exception {
        byte[] buffer = s.getBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(buffer);

        ZagsPerson person = new ZagsPerson();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(bis);
        NodeList childNodes = doc.getFirstChild().getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node instanceof Element) {
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
