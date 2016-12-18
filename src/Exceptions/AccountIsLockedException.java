package Exceptions;

/**
 * Created by Vlad on 18.12.2016.
 */
public class AccountIsLockedException extends Exception {

    public AccountIsLockedException(long time) {

        throw new IllegalArgumentException("Account is locked still " + time + " sec");

    }

}
