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
import edu.javacourse.stp.db.ReadXmlForSo;
import edu.javacourse.stp.domain.PersonChild;
import edu.javacourse.stp.domain.StudentOrder;
import edu.javacourse.stp.domain.answer.CheckAnswer;
import edu.javacourse.stp.exception.CheckException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProcessStarter {
    public static void main(String[] args) {
        System.out.println("Starting...");
        ProcessStarter t = new ProcessStarter();
        t.processList(); // start the process
    }

    private void processList() {
        List<StudentOrder> orderList = ReadXmlForSo.getStudentOrders();

        for (StudentOrder so : orderList) {
            System.out.println("SO: "+so.getId());
            processStudentOrder(so); // for every element in the created list process the following function
        }
    }

    private void processStudentOrder(StudentOrder so) {
        List<CheckAnswer> answers = new ArrayList<>(); // creating the list of answers; TODO Make it return answers as list and put it in the file

        try {
            System.out.print("Check GRN: " + "\n");
            answers.addAll(checkGRN(so));
            System.out.print("Check ZAGS: " + "\n");
            answers.addAll(checkZAGS(so));
            System.out.print("Check Students: " + "\n");
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
//        System.out.println("so: "+so+" // "+answers);
        am.approveOrder(so, answers);
    }

    // -------- FUNCTIONS -------

    private List<CheckAnswer> checkGRN(StudentOrder so) throws CheckException {
        List<CheckAnswer> answers = new ArrayList<>(); // create list for answers

        ExecutorService es = Executors.newFixedThreadPool(10);
        List<Future<CheckAnswer>> result = new ArrayList<>(); // establish a list of result objects

        GRNchecker grnH = new GRNchecker(so.getHusband());
        result.add(es.submit(grnH));

        GRNchecker grnW = new GRNchecker(so.getWife());
        result.add(es.submit(grnW));

        for (PersonChild pc : so.getChildren()) {
            GRNchecker grnC = new GRNchecker(pc);
            result.add(es.submit(grnC));
        }

        for (Future<CheckAnswer> f : result) { //convert result list to answer list
            try {
                CheckAnswer answer = f.get();
                answers.add(answer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        es.shutdown();
        return answers;

    }

    private List<CheckAnswer> checkZAGS(StudentOrder so) throws CheckException {
        List<CheckAnswer> answers = new ArrayList<>();
        ExecutorService es = Executors.newFixedThreadPool(10);
        List<Future<CheckAnswer>> result = new ArrayList<>();

        ZAGSchecker zM = new ZAGSchecker(so.getHusband(), so.getWife(),null);
        result.add(es.submit(zM));

        for (PersonChild pc : so.getChildren()) {
            ZAGSchecker zC = new ZAGSchecker(so.getHusband(), so.getWife(), pc);
            result.add(es.submit(zC));
        }
        for (Future<CheckAnswer> f : result) { //convert result list to answer list
            try {
                CheckAnswer answer = f.get();
                answers.add(answer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        es.shutdown();
        return answers;
    }

    private List<CheckAnswer> checkStudents(StudentOrder so) throws CheckException {
        List<CheckAnswer> answers = new ArrayList<>();

        ExecutorService es = Executors.newFixedThreadPool(10);
        List<Future<CheckAnswer>> result = new ArrayList<>();

        StudentChecker scH = new StudentChecker(so.getHusband());
        result.add(es.submit(scH));
        StudentChecker scW = new StudentChecker(so.getWife());
        result.add(es.submit(scW));

        for (Future<CheckAnswer> f : result) {
            try {
                CheckAnswer answer = f.get();
                answers.add(answer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        es.shutdown();
        return answers;
    }
}