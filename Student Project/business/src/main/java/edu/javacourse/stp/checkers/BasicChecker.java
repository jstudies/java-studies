/*
 * Created by Dmitry Melnikov 2018.
 */

/*
 * Created by Dmitry Melnikov 2018.
 */

package edu.javacourse.stp.checkers;

import edu.javacourse.stp.domain.answer.CheckAnswer;
import edu.javacourse.stp.exception.CheckException;
import edu.javacourse.stp.exception.SendGetDataException;
import edu.javacourse.stp.exception.ConnectException;

import java.io.*;
import java.net.Socket;

public abstract class BasicChecker {

    public static final String ERROR = "\u001B[31m"; // ERROR colour
    public static final String ERROR_END = "\u001B[0m"; // REG colour

    protected String host;
    protected int port;
    protected String login;
    protected String password;

    protected Socket socket;

    public BasicChecker(String host, int port, String login, String password) { // received settings for connection
        this.host = host;
        this.login = login;
        this.password = password;
        this.port = port;
    }

    public CheckAnswer check() throws CheckException { // function to check answers
        try {
            connect();
            try {
                CheckAnswer result = sendAndGetData();
                return result;
            } catch (SendGetDataException e) {
                throw new CheckException(e.getMessage(), e);
            } finally {
                disconnect();
            }
        } catch (ConnectException e) {
            throw new CheckException(e.getMessage(), e);

        }
    }

    private void connect() throws ConnectException {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            throw new ConnectException(e.getMessage(), e);
        }
    }

    protected abstract CheckAnswer sendAndGetData() throws SendGetDataException;

    private void disconnect() throws ConnectException {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConnectException(e.getMessage(), e);
        }
    }
}
