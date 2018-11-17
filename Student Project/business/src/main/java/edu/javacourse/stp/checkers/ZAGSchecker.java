/*
 * Created by Dmitry Melnikov 2018.
 */

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
import java.util.Date;
import java.util.PropertyResourceBundle;
import java.util.concurrent.Callable;

public class ZAGSchecker extends BasicChecker implements Callable<CheckAnswer> {

    private static final String host;
    private static final int port;
    private static final String login;
    private static final String password;

    static {
        PropertyResourceBundle pr = (PropertyResourceBundle) PropertyResourceBundle.getBundle("zags_checker");

        host = pr.getString("zags.host");
        port = Integer.parseInt(pr.getString("zags.port"));
        login = pr.getString("zags.login");
        password = pr.getString("zags.password");
    }

    private Person husband;
    private Person wife;
    private Person child;

    public ZAGSchecker(Person husband, Person wife, Person child) {
        super(host, port, login, password);
        this.husband=husband;
        this.wife=wife;
        this.child=child;
    }

    public void setParameters(Person husband, Person wife, Person child) {
        this.husband = husband;
        this.wife = wife;
        this.child = child;
    }
    @Override
    public CheckAnswer call() throws Exception {
        return check();
    }

    protected CheckAnswer sendAndGetData() throws SendGetDataException {
        try {
            OutputStream os = socket.getOutputStream();
            StringBuilder sb = new StringBuilder(buildXml());

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
            return answer;

        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
            throw new SendGetDataException(e.getMessage());
        }
    }

    private String buildXml() throws UnsupportedEncodingException, XMLStreamException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        XMLStreamWriter xml = factory.createXMLStreamWriter(bos);

        String type;
        int q;
        if (child == null) {
            type = "marriage";
            q = 2;
        } else {
            type = "child-check";
            q = 3;
        }

        String p;
        Person persona;

        xml.writeStartDocument();
        xml.writeStartElement(type);
        for (int i = 0; i < q; i++) {
            if (i == 0) {
                p = "husband";
                persona = husband;
            } else if (i == 1) {
                p = "wife";
                persona = wife;
            } else {
                p = "child";
                persona = child;
            }
            xml.writeStartElement(p);
            xml.writeStartElement("surName");
            xml.writeCharacters(persona.getSurName());
            xml.writeEndElement();
            xml.writeStartElement("givenName");
            xml.writeCharacters(persona.getGivenName());
            xml.writeEndElement();
            xml.writeStartElement("patronymic");
            xml.writeCharacters(persona.getPatronymic());
            xml.writeEndElement();
            xml.writeStartElement("dateOfBirth");
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            xml.writeCharacters(sdf.format(new Date()));
            xml.writeEndElement();
            xml.writeEndElement();
        }
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

        System.out.print("message (" + message + ") // " + result + "\n");

        BasicCheckerAnswer answer = new BasicCheckerAnswer(result, message);
        return answer;
    }

}
