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

public class StudentChecker extends BasicChecker {

    private Person person;

    public StudentChecker(String host, int port, String login, String password) {
        super(host, port, login, password);
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    protected CheckAnswer sendAndGetData() throws SendGetDataException {
        return new BasicCheckerAnswer(true, "SC // " + person.getSurName() + ", " + person.getGivenName());
    }
}
