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
import java.util.concurrent.Callable;

public class StudentChecker extends BasicChecker implements Callable<CheckAnswer> {

    private static final String host;
    private static final int port;
    private static final String login;
    private static final String password;

    static {
        PropertyResourceBundle pr = (PropertyResourceBundle) PropertyResourceBundle.getBundle("student_checker");

        host = pr.getString("stu.host");
        port = Integer.parseInt(pr.getString("stu.port"));
        login = pr.getString("stu.login");
        password = pr.getString("stu.password");
    }

    private Person person;

    public StudentChecker(Person person) {
        super(host, port, login, password);
        this.person=person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public CheckAnswer call() throws Exception {
        return check();
    }

    protected CheckAnswer sendAndGetData() throws SendGetDataException {
        try {
            OutputStream os = socket.getOutputStream();
            StringBuilder sb = new StringBuilder(buildXmlForPerson());

            os.write(sb.toString().getBytes());
            os.flush();

            StringBuilder sr = new StringBuilder();
            Reader br = new InputStreamReader(socket.getInputStream());
            char[] request = new char[256];
            int count = br.read(request);
            while (count != -1) {
                sr.append(new String(request, 0, count));
                if (sr.toString().endsWith("OK")) {
                    break;
                }
                count = br.read(request);
            }
            CheckAnswer answer = buildAnswer(sr.toString());
            System.out.println(answer);
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

        BasicCheckerAnswer answer = new BasicCheckerAnswer(result, message);
        return answer;
    }
}
