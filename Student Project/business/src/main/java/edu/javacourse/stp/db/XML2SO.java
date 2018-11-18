//package edu.javacourse.stp.db;
//
//import edu.javacourse.stp.domain.PersonChild;
//import edu.javacourse.stp.domain.StudentOrder;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import javax.xml.bind.*;
//
//public class XML2SO {
//    public static void main(String[] args) {
//        try (InputStream is = new FileInputStream("student_orders.xml")) {
//            StudentOrders sos = readXML(is);
//            writeXML(sos);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static StudentOrders readXML(InputStream is) throws JAXBException {
//        JAXBContext jc = JAXBContext.newInstance(StudentOrders.class);
//        Unmarshaller u = jc.createUnmarshaller();
//        StudentOrders po = (StudentOrders) u.unmarshal(is);
//
//        for (StudentOrder so : po.getStudentOrders()) {
//            System.out.println("\n" + "Order #:" + so.getStudentOrderId());
//            System.out.println("\n" + "Family: " + so.getHusband().getSurName() + ", " + so.getHusband().getGivenName() + " and " + so.getWife().getSurName() + ", " + so.getWife().getGivenName());
//            System.out.println("with kids(s): ");
//            int i = 0;
//            for (PersonChild pc : so.getChildren()) {
//                if (i == 0) {
//                    System.out.print(pc.getSurName() + ", " + pc.getGivenName());
//                    i = i + 1;
//                } else {
//                    System.out.print(" and " + pc.getSurName() + ", " + pc.getGivenName());
//                    i = 0;
//                }
//            }
//            System.out.println("\n" + "----------------------------------------------");
//        }
//        return po;
//    }
//
//    private static void writeXML(StudentOrders sos) throws JAXBException, IOException {
//        JAXBContext jc = JAXBContext.newInstance(StudentOrders.class);
//        Marshaller m = jc.createMarshaller();
//
//        for (StudentOrder so : sos.getStudentOrders()) {
//            so.setStudentOrderId(so.getStudentOrderId() + 10);
//        }
//        try (FileOutputStream os = new FileOutputStream("result.xml");){ // Closable, thus will be auto-closed
//            m.marshal(sos, os);
//        } catch (IOException e) {
//            throw e;
//        }
//    }
//}
