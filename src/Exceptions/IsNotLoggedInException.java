package Exceptions;

/**
 * Created by Vlad on 18.12.2016.
 */
public class IsNotLoggedInException extends Exception {
    public IsNotLoggedInException(String msg){
        super(msg);
    }
    public IsNotLoggedInException(String msg,Throwable cause){
        super(msg,cause);
    }
}
