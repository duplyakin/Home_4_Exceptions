package Exceptions;

/**
 * Created by Vlad on 15.11.2016.
 */
public class BadSumException extends Exception {
    public BadSumException(String msg){
        super(msg);
    }
    public BadSumException(String msg,Throwable cause){
        super(msg,cause);
    }
}

