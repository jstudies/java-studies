package edu.javacourse.stp.db;

import edu.javacourse.stp.domain.PersonAdult;
import edu.javacourse.stp.domain.StudentOrder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XML2SOXP implements StudentOrderDataSource {

    @Override
    public List<StudentOrder> getStudentOrders(){
        try{
            DocumentBuilder docBuild = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document XMLdoc = docBuild.parse("student_orders.xml");
            Node root = XMLdoc.getDocumentElement();
            List<Integer> ids = extractOrdersID(root);
            for (Integer id:ids){
                PersonAdult wife = makeWife(root,id);
//                PersonAdult husband=makeHusband(root,id);
//                PersonChild child=makeChild(root,id);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static List<Integer> extractOrdersID(Node root) throws XPathExpressionException {
        XPathFactory pf = XPathFactory.newInstance(); // initiate XPath
        XPath xp = pf.newXPath();
        List<Integer> result= new ArrayList<>(); // make list
        XPathExpression expr = xp.compile("student-order"); // set expression to search
        NodeList nodes = (NodeList) expr.evaluate(root, XPathConstants.NODESET); // load nodes
        for (int i=0;i<nodes.getLength();i++){
            Node n = nodes.item(i); // assign node to n
            int id = Integer.parseInt(((Element) n).getAttribute("so-id")); // parse for attribute so-id
            result.add(id); // add result to the list
        }
        return result;
    }

    private static PersonAdult makeWife(Node root, Integer id) throws XPathExpressionException {
        PersonAdult person = new PersonAdult();
        XPathFactory pf = XPathFactory.newInstance(); // initiate XPath
        XPath xp = pf.newXPath();
        String who=String.format("student-order[@so-id='%d']/wife", id); // which instance to stop at
        XPathExpression expr = xp.compile(who);
        Node nodes = (Node) expr.evaluate(root, XPathConstants.NODE); // load nodes
        NodeList names =nodes.getChildNodes();
        for (int i=0;i<names.getLength();i++){
            Node name=names.item(i);
            if (name instanceof Element){
                if("surName".equals(name.getNodeName())){
                    person.setSurName(name.getTextContent().trim());
                }
                if ("givenName".equals(name.getNodeName())) {
                    person.setGivenName(name.getTextContent());
                }
                if ("patronymic".equals(name.getNodeName())) {
                    person.setPatronymic(name.getTextContent());
                }
                if ("surName".equals(name.getNodeName())) {
                    person.setSurName(name.getTextContent());
                }
                if ("dateOfBirth".equals(name.getNodeName())) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    try {
                        Date dob = sdf.parse(name.getTextContent());
                        person.setDateOfBirth(dob);
                    } catch (ParseException pex) {
                        pex.printStackTrace(System.out);
                    }
                }
            }
        }
        return person;
    }
}
