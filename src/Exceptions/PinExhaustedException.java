package Exceptions;

/**
 * Created by Vlad on 18.12.2016.
 */
public class PinExhaustedException extends Exception {
    public PinExhaustedException(String msg){
        super(msg);
    }
    public PinExhaustedException(String msg,Throwable cause){
        super(msg,cause);
    }
}
