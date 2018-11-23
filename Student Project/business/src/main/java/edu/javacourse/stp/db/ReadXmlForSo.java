package edu.javacourse.stp.db;

import edu.javacourse.stp.domain.PersonAdult;
import edu.javacourse.stp.domain.PersonChild;
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
import java.util.*;

public class ReadXmlForSo {

    public static List<StudentOrder> getStudentOrders() {
        List<StudentOrder> so = new ArrayList<>(); // create array for SOs
        try {
            DocumentBuilder docBuild = DocumentBuilderFactory.newInstance().newDocumentBuilder(); // launching DocumentBuilder proccess
            Document XMLdoc = docBuild.parse("student_orders.xml"); // parsing the orders file
            Node root = XMLdoc.getDocumentElement(); // set root
            List<Integer> ids = extractOrdersID(root); // create array and fill it with list of SO ID's, read by extractOrdersID func

            for (Integer id : ids) { // processing each SO
                int p = extractChildrenQty(root, id); // extracting qty of children attribute
                so.add(getStudentOrder(root, id, p)); // add to the SO list a new SO created by getStudentOrderSO function
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return so; // return SO
    }

    private static StudentOrder getStudentOrder(Node root, Integer id, int p) throws Exception { // fill SO
        List<PersonChild> children = new ArrayList<>();

        PersonAdult h = makePerson(root, id, "husband"); // call makePerson function, send the root node it, so
        PersonAdult w = makePerson(root, id, "wife");

        for (int i = 1; i < p + 1; i++) { // cycle makeChild function till it reaches set qty
            children.add(makeChild(root, id, i)); //make child
        }

        StudentOrder so = new StudentOrder(h, w, children, id); // create SO
        return so; // return SO
    }

    // extract SO ID attributes

    private static List<Integer> extractOrdersID(Node root) throws XPathExpressionException {
        XPathFactory pf = XPathFactory.newInstance(); // initiate XPath
        XPath xp = pf.newXPath(); // new XPath function
        List<Integer> result = new ArrayList<>(); // make list
        XPathExpression expr = xp.compile("student-order"); // set expression to search
        NodeList nodes = (NodeList) expr.evaluate(root, XPathConstants.NODESET); // load nodes
        for (int i = 0; i < nodes.getLength(); i++) { // cycle till reach the qty of nodes
            Node n = nodes.item(i); // assign node to n
            int id = Integer.parseInt(((Element) n).getAttribute("so-id")); // parse for attribute so-id
            result.add(id); // add result to the list
        }
        return result; // return the list of order IDs
    }

    // extract children qty attribute

    private static int extractChildrenQty(Node root, Integer id) throws XPathExpressionException {
        XPathFactory pf = XPathFactory.newInstance(); // initiate XPath
        XPath xp = pf.newXPath();
        String who = String.format("student-order[@so-id='%d']/children", id); // which instance to stop at
        XPathExpression expr = xp.compile(who);
        NodeList nodes = (NodeList) expr.evaluate(root, XPathConstants.NODESET);
        Node n = nodes.item(0); // assign node to n
        int qty = Integer.parseInt(((Element) n).getAttribute("ch-qty"));
        System.out.println("SO: " + id + ", children qty: " + qty);
        return qty;
    }

    // make adult person

    private static PersonAdult makePerson(Node root, Integer id, String type) throws XPathExpressionException {
        PersonAdult person = new PersonAdult(); // new PersonAdult function
        XPathFactory pf = XPathFactory.newInstance(); // initiate XPath
        XPath xp = pf.newXPath(); // new XPath function
        String who = String.format("student-order[@so-id='%d']/" + type, id); // which instance to stop at
        XPathExpression expr = xp.compile(who); // compile the node data
        Node nodes = (Node) expr.evaluate(root, XPathConstants.NODE); // load nodes
        NodeList names = nodes.getChildNodes(); // look into child nodes

        for (int i = 0; i < names.getLength(); i++) {
            Node name = names.item(i); // select node item with index 'i'
            if (name instanceof Element) {
                if ("surName".equals(name.getNodeName())) {
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

    private static PersonChild makeChild(Node root, Integer id, int p) throws XPathExpressionException {
        PersonChild person = new PersonChild();
        XPathFactory pf = XPathFactory.newInstance(); // initiate XPath
        XPath xp = pf.newXPath(); // new XPath function
        // select proper child node with index 'p':
        String who = String.format("student-order[@so-id='%d']/children/child[" + p + "]", id);
        XPathExpression expr = xp.compile(who); // set expression to search
        Node nodes = (Node) expr.evaluate(root, XPathConstants.NODE); // load nodes
        NodeList names = nodes.getChildNodes();

        for (int i = 0; i < names.getLength(); i++) {
            Node name = names.item(i);
            if (name instanceof Element) {
                if ("surName".equals(name.getNodeName())) {
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
        return person; // return person
    }
}
