/*
 * Created by Dmitry Melnikov 2018.
 */

/*
 * Created by Dmitry Melnikov 2018.
 */

package edu.javacourse.stp.checkers;

import edu.javacourse.stp.domain.Person;
import edu.javacourse.stp.domain.answer.CheckAnswer;
import edu.javacourse.stp.exception.SendGetDataException;

public class ZAGSchecker extends BasicChecker {
    private Person husband;
    private Person wife;
    private Person child;

    public ZAGSchecker(String host, int port, String login, String password) {
        super(host, port, login, password);
    }

    public void setParameters(Person husband, Person wife, Person child) {
        this.husband = husband;
        this.wife = wife;
        this.child = child;
    }

    @Override
    protected CheckAnswer sendAndGetData() throws SendGetDataException {
        if (child == null) {
            return checkWedding();
        } else {
            return checkChild();
        }
    }

    private CheckAnswer checkWedding() {
        return new BasicCheckerAnswer(true, "ZAGS // " + husband.getSurName() + ", " + husband.getGivenName() + " и " + wife.getSurName() + ", " + wife.getGivenName());
    }

    private CheckAnswer checkChild() {
        return new BasicCheckerAnswer(true, "ZAGS // " + child.getSurName() + ", " + child.getGivenName());
    }
}
