/*
 * Created by Dmitry Melnikov 2018.
 */

/*
 * Created by Dmitry Melnikov 2018.
 */

package edu.javacourse.stp;

import edu.javacourse.stp.domain.StudentOrder;
import edu.javacourse.stp.domain.answer.CheckAnswer;

import java.io.*;
import java.util.List;

public class ApproveManager {
    public void approveOrder(StudentOrder so, List<CheckAnswer> answers) {

        // write file

        try {
            FileOutputStream fos = new FileOutputStream("student.txt", false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(so);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // read file

        try {
            FileInputStream fis = new FileInputStream("student.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            StudentOrder so1 = (StudentOrder) ois.readObject();
            System.out.println("\n" + "------- FILE ------" + "\n");
            System.out.println(so1.getHusband().getGivenName());
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void denyOrder(StudentOrder so, List<CheckAnswer> answers) {
        System.out.println("APM - order DENIED:");
        for (CheckAnswer ca : answers) {
            System.out.println(ca);
        }
    }
}
