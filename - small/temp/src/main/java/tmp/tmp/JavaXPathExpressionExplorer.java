package tmp.tmp;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaXPathExpressionExplorer {
    public static final String DEFAULT_XML_FILENAME = "inventory.xml";

    public static void main(String... args) {

        // Setup an InputStreamReader to read from the keyboard
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);

        // Instantiate the factory that supplies the DOM parser
        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();

        DocumentBuilder domParser = null;
        try {
            // Instantiate the DOM parser
            domParser = builderFactory.newDocumentBuilder();

            // Load the DOM Document from the XML data using the parser
            Document domDocument =
                    domParser.parse(getFileInputStreamName(in));

            // Instantiate an XPath object which compiles
            // and evaluates XPath expressions.
            XPath xPath = XPathFactory.newInstance().newXPath();

            while (true) {
                System.out.print("Enter expression (blank line to exit): ");
                String expr = in.readLine(); // Holds the XPath expressiom

                try {
                    if ((expr == null) || (expr.length() == 0)) {
                        System.exit(0); // User is done entering expressions
                    }
                    System.out.println(expr + " evaluates to:");

                    String resString = (String) xPath.compile(expr).evaluate(domDocument, XPathConstants.STRING);
                    if (resString != null) {
                        System.out.println("String: " + resString);
                    }

                    Number resNumber = (Number) xPath.compile(expr).evaluate(domDocument, XPathConstants.NUMBER);
                    if (resNumber != null) {
                        System.out.println("Number: " + resNumber);
                    }

                    Boolean resBoolean = (Boolean) xPath.compile(expr).evaluate(domDocument, XPathConstants.BOOLEAN);
                    if (resNumber != null) {
                        System.out.println("Boolean: " + resBoolean);
                    }

                    Node resNode = (Node) xPath.compile(expr).evaluate(domDocument, XPathConstants.NODE);
                    if (resNode != null) {
                        System.out.println("Node: " + resNode);
                    }

                    NodeList resNodeList = (NodeList) xPath.compile(expr).evaluate(domDocument, XPathConstants.NODESET);
                    if (resNodeList != null) {
                        int lenList = resNodeList.getLength();
                        System.out.println("Number of nodes in NodeList: " + lenList);
                        for (int i = 1; i <= lenList; i++) {
                            resNode = resNodeList.item(i-1);
                            String resNodeNameStr = resNode.getNodeName();
                            String resNodeTextStr = resNode.getTextContent();
                            System.out.println(i + ": " + resNode + "  (NodeName:'" + resNodeNameStr + "'    NodeTextContent:'" + resNodeTextStr + "')");
                        }
                    }

                    outputSeparator();

                } catch (Exception e) {
                    // Do nothing. This prevents output to console if expression result type is not appropriate
                    // for the XPath expression being compiled and evaluated
                }

            } // end  while (true)

        } catch (Exception e) {
            // Even though we are using a DOM parser a SAXException is thrown
            // if the DocumentBuilder cannot parse the XML file
            e.printStackTrace();
        }
    }

    // Helper method to load the XML file into the DOM Document
    public static String getFileInputStreamName(BufferedReader inputReader) {
        System.out.print("Enter XML file name (abc.xml) or leave blank to use "+DEFAULT_XML_FILENAME+": ");
        String fileName = null;
        try {
            fileName = inputReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if ((fileName == null) || (fileName.length() == 0)) {
            return DEFAULT_XML_FILENAME;
        }
        return fileName;
    }

    // Helper method to pretty up the output
    public static void outputSeparator() {
        System.out.println("=+=+=+=+=+=+=+=+");
    }

}

