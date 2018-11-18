/*
 * Created by Dmitry Melnikov 2018.
 */

/*
 * Created by Dmitry Melnikov 2018.
 */

package edu.javacourse.stp.db;

import edu.javacourse.stp.domain.PersonAdult;
import edu.javacourse.stp.domain.PersonChild;
import edu.javacourse.stp.domain.StudentOrder;

import java.util.ArrayList;
import java.util.List;

public class FakeDataSource implements StudentOrderDataSource {
    private static final int CHILD_COUNT = 3;
    private static final int FAMILY = 2;

//    public FakeDataSource(String s) {
//
//    }

    @Override
    public List<StudentOrder> getStudentOrders() {
        List<StudentOrder> result = new ArrayList<>();
        for (int f = 1; f < FAMILY; f++) {
            result.add(getStudentOrder("Husband #" + f,
                    "Wife #" + f,
                    "Child"));
        }
        return result;
    }

    public static StudentOrder getStudentOrder(String hName,
                                                String wName,
                                                String cName) {
        PersonAdult h = new PersonAdult();
        h.setGivenName(hName);
        PersonAdult w = new PersonAdult();
        w.setGivenName(wName);
        // create list for children
        List<PersonChild> children = new ArrayList();
        for (int i = 1; i < CHILD_COUNT; i++) {
            PersonChild c = new PersonChild();
            // generate name id
            c.setGivenName(cName + " #" + i);
            // add to the list
            children.add(c);
        }
        StudentOrder so = new StudentOrder(h, w, children,115);
        return so;
    }
}
