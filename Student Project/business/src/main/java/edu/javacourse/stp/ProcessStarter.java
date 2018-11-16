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
import edu.javacourse.stp.db.StudentOrderDataSource;
import edu.javacourse.stp.domain.PersonChild;
import edu.javacourse.stp.domain.StudentOrder;
import edu.javacourse.stp.domain.answer.CheckAnswer;
import edu.javacourse.stp.exception.CheckException;

import java.util.ArrayList;
import java.util.List;

public class ProcessStarter {
    public static void main(String[] args) {
        System.out.println("Starting...");
        ProcessStarter t = new ProcessStarter();
        t.processList(); // start the process
    }

    public void processList() {
        StudentOrderDataSource ds = FactoryDataSource.getDataSource();
        List<StudentOrder> orderList = ds.getStudentOrders();
        for (StudentOrder so : orderList) {
            processStudentOrder(so); // for every element in the created list process the following function
        }

    }

    private void processStudentOrder(StudentOrder so) {
        List<CheckAnswer> answers = new ArrayList<>(); // creating the list of answers; TODO Make it return answers as list and put it in the file

        try {
            System.out.print("\n"+"Check GRN: ");
            answers.addAll(checkGRN(so));
            System.out.print("\n"+"Check ZAGS: ");
            answers.addAll(checkZAGS(so));
            System.out.print("\n"+"Check Students: ");
            answers.addAll(checkStudents(so));
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
        am.approveOrder(so, answers);
    }

    // -------- FUNCTIONS -------

    private List<CheckAnswer> checkGRN(StudentOrder so) throws CheckException {
        List<CheckAnswer> answers = new ArrayList<>(); // create list for answers
        GRNchecker grn = new GRNchecker(); // create checker

        grn.setPerson(so.getHusband());
        System.out.print("Husband // ");
        answers.add(grn.check());
        grn.setPerson(so.getWife());
        System.out.print("           Wife // ");
        answers.add(grn.check());

        for (PersonChild pc : so.getChildren()) {
            grn.setPerson(pc);
            System.out.print("           Child // ");
            answers.add(grn.check());
        }

        return answers;
    }

    private List<CheckAnswer> checkZAGS(StudentOrder so) throws CheckException {
        List<CheckAnswer> answers = new ArrayList<>();
        ZAGSchecker zc = new ZAGSchecker();

        zc.setParameters(so.getHusband(), so.getWife(), null);
        System.out.print("Husband+Wife // ");
        answers.add(zc.check());


        for (PersonChild pc : so.getChildren()) {
            zc.setParameters(so.getHusband(), so.getWife(), pc);
            System.out.print("            Child // ");
            answers.add(zc.check());
        }

        return answers;
    }

    private List<CheckAnswer> checkStudents(StudentOrder so) throws CheckException {
        List<CheckAnswer> answers = new ArrayList<>();
        StudentChecker sc = new StudentChecker();

        sc.setPerson(so.getHusband());
        System.out.print("Husband // ");
        answers.add(sc.check());
        sc.setPerson(so.getWife());
        System.out.print("                Wife // ");
        answers.add(sc.check());

        return answers;
    }
}