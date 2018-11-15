/*
 * Created by Dmitry Melnikov 2018.
 */

/*
 * Created by Dmitry Melnikov 2018.
 */

package edu.javacourse.stp.checkers;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import edu.javacourse.stp.domain.Person;
import edu.javacourse.stp.domain.answer.CheckAnswer;
import edu.javacourse.stp.exception.SendGetDataException;


import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GRNchecker extends BasicChecker {

    private Person person;

    public GRNchecker(String host, int port, String login, String password) { // get connection parameters
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
            System.out.println("reply: ");
            System.out.println("--");

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

            System.out.println(sr); // show what's read

            System.out.println("--");
            CheckAnswer answer = new BasicCheckerAnswer(true, "MESSAGE");
            return answer;

        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
            throw new SendGetDataException(e.getMessage());
        }

    }

    private String buildXmlForPerson() throws UnsupportedEncodingException, XMLStreamException {
        ByteOutputStream bos = new ByteOutputStream();

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

        String answer=new String(bos.getBytes(),0,bos.getCount(),"UTF-8");
        return answer;
    }
}
