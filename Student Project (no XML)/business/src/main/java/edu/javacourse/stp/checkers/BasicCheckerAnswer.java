/*
 * Created by Dmitry Melnikov 2018.
 */

/*
 * Created by Dmitry Melnikov 2018.
 */

package edu.javacourse.stp.checkers;

import edu.javacourse.stp.domain.answer.CheckAnswer;

public class BasicCheckerAnswer implements CheckAnswer {

    private boolean result;
    private String message;

    public BasicCheckerAnswer(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    @Override
    public boolean getResult() {
        return result;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
