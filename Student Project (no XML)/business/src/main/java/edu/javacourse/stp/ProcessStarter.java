/*
 * Created by Dmitry Melnikov 2018.
 */

/*
 * Created by Dmitry Melnikov 2018.
 */

package edu.javacourse.stp;

import edu.javacourse.stp.checkers.GRNchecker;
import edu.javacourse.stp.checkers.StudentChecker;
import edu.javacourse.stp.checkers.ZAGSchecker;
import edu.javacourse.stp.db.FactoryDataSource;
import edu.javacourse.stp.db.FakeDataSource;
import edu.javacourse.stp.db.StudentOrderDataSource;
import edu.javacourse.stp.domain.PersonChild;
import edu.javacourse.stp.domain.StudentOrder;
import edu.javacourse.stp.domain.answer.CheckAnswer;
import edu.javacourse.stp.exception.CheckException;

import java.util.ArrayList;
import java.util.List;

public class ProcessStarter {
    public static void main(String[] args) {
        ProcessStarter t = new ProcessStarter();
        t.processList(); // start the process


    }

    public void processList() {
        System.out.println("processList: OK");
        StudentOrderDataSource ds = FactoryDataSource.getDataSource();
        List<StudentOrder> orderList = ds.getStudentOrders();
        for (StudentOrder so : orderList) {
            System.out.println("prcoessStudentOrder called");
            processStudentOrder(so); // for every element in the created list process the following function
        }
    }

    private void processStudentOrder(StudentOrder so) {
        List<CheckAnswer> answers = new ArrayList<>(); // creating the list of answers; TODO Make it return answers as list and put it in the file

        try {
            System.out.println("calling for checkGRN");
            answers.addAll(checkGRN(so));
//            answers.addAll(checkZAGS(so));
//            answers.addAll(checkStudents(so));
        } catch (CheckException e) {
            // TODO Make Exception processing
            return;
        }

        ApproveManager am = new ApproveManager();
        for (CheckAnswer ca : answers) {
            if (!ca.getResult()) {
                am.denyOrder(so, answers);
                return;
            }
        }
         am.approveOrder (so, answers);
    }

    // -------- FUNCTIONS -------

    private List<CheckAnswer> checkGRN(StudentOrder so) throws CheckException {
        List<CheckAnswer> answers = new ArrayList<>(); // create list for answers
        GRNchecker grn = new GRNchecker("localhost", 7777, "3", "4"); // create checker

        System.out.println("checkGRN starting check");

        grn.setPerson(so.getHusband());
        answers.add(grn.check());
//        grn.setPerson(so.getWife());
//        answers.add(grn.check());
//        for (PersonChild pc : so.getChildren()) {
//            grn.setPerson(pc);
//            answers.add(grn.check());
//        }

        return answers;
    }

    private List<CheckAnswer> checkZAGS(StudentOrder so) throws CheckException {
        List<CheckAnswer> answers = new ArrayList<>();
        ZAGSchecker zc = new ZAGSchecker("1", 2, "3", "4");

        zc.setParameters(so.getHusband(), so.getWife(), null);
        answers.add(zc.check());
        for (PersonChild pc : so.getChildren()) {
            zc.setParameters(so.getHusband(), so.getWife(), pc);
            answers.add(zc.check());
        }

        return answers;
    }

    private List<CheckAnswer> checkStudents(StudentOrder so) throws CheckException {
        List<CheckAnswer> answers = new ArrayList<>();
        StudentChecker sc = new StudentChecker("1", 2, "3", "4");

        sc.setPerson(so.getHusband());
        answers.add(sc.check());
        sc.setPerson(so.getWife());
        answers.add(sc.check());

        return answers;
    }
}