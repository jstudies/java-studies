/*
 * Created by Dmitry Melnikov 2018.
 */

package edu.javacourse.stp.checkers;

import edu.javacourse.stp.domain.Person;
import edu.javacourse.stp.domain.answer.CheckAnswer;
import edu.javacourse.stp.exception.SendGetDataException;


import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
//import java.util.Properties;


public class GRNchecker extends BasicChecker {

// all static assigned first hand on loading of the class

    private static final String host; // static == set once for the class for all possible instances
    private static final int port;
    private static final String login;
    private static final String password;

//    private static Map<String, String> settings = new HashMap<>();

    static {

//        Properties pr = new Properties();
//        try {
//            pr.load(new FileInputStream("grn_checker.properties"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Locale lcl=new Locale("ru","RU","Mac"); // establish Locale

        Locale lcl = new Locale("en", "US", "Mac");

        PropertyResourceBundle pr = (PropertyResourceBundle)
                PropertyResourceBundle.getBundle("grn_checker", lcl); // use properties file and locale

//        settings.put("host", pr.getString("grn.host")); // works with Map
//        settings.put("port", pr.getString("grn.port"));
//        settings.put("login", pr.getString("grn.login"));
//        settings.put("password", pr.getString("grn.password"));


        host = pr.getString("grn.host");
        port = Integer.parseInt(pr.getString("grn.port"));
        login = pr.getString("grn.login");
        password = pr.getString("grn.password");
        String lng = pr.getString("grn.test");
        System.out.println(lng);
        System.out.println("Host:port - " + host + ":" + port + " // login - " + login + " // password - " + password);

    }

    private Person person;


    public GRNchecker() { // get connection parameters
        super(host, port, login, password); // send it to super BasicChecker
    }

    public void setPerson(Person person) {
        this.person = person; // set the person
    }

    protected CheckAnswer sendAndGetData() throws SendGetDataException {
        try {
            OutputStream os = socket.getOutputStream(); // stream to be sent
            StringBuilder sb = new StringBuilder(buildXmlForPerson());
            os.write(sb.toString().getBytes());
            os.flush();

            StringBuilder sr = new StringBuilder(); // create new string
            Reader br = new InputStreamReader(socket.getInputStream()); // buffer reader form the IS from the socket, which get IS
            char[] request = new char[6];
            int count = br.read(request);
            while (count != -1) { // while counter of bytes is not negative, do
                sr.append(new String(request, 0, count));
                if (sr.toString().endsWith("OK")) {
                    break;
                }
                count = br.read(request);
            }

            CheckAnswer answer = buildAnswer(sr.toString());
            return answer;

        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
            throw new SendGetDataException(e.getMessage());
        }
    }

    private String buildXmlForPerson() throws UnsupportedEncodingException, XMLStreamException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        XMLStreamWriter xml = factory.createXMLStreamWriter(bos);

        xml.writeStartDocument();
        xml.writeStartElement("person");

        xml.writeStartElement("surName");
        xml.writeCharacters(person.getSurName());
        xml.writeEndElement();
        xml.writeStartElement("givenName");
        xml.writeCharacters(person.getGivenName());
        xml.writeEndElement();
        xml.writeStartElement("patronymic");
        xml.writeCharacters(person.getPatronymic());
        xml.writeEndElement();
        xml.writeStartElement("dateOfBirth");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        xml.writeCharacters(sdf.format(new Date()));
        xml.writeEndElement();

        xml.writeEndElement();
        xml.writeEndDocument();

        String answer = new String(bos.toByteArray(), 0, bos.size(), "UTF-8");
        return answer;
    }

    private CheckAnswer buildAnswer(String s) {
        int r1 = s.indexOf("<result>");
        int r2 = s.indexOf("</result>");
        int m1 = s.indexOf("<message>");
        int m2 = s.indexOf("</message>");

        Boolean result = Boolean.parseBoolean(s.substring(r1 + "<result>".length(), r2));
        String message = s.substring(m1 + "<message>".length(), m2);

        System.out.println("result: " + result);
        System.out.println("message: " + message);

        BasicCheckerAnswer answer = new BasicCheckerAnswer(result, message);
        return answer;
    }
}
