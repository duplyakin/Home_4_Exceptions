package Exceptions;

/**
 * Created by Vlad on 15.11.2016.
 */
public class BadPinException extends Exception {
    public BadPinException(String msg){
        super(msg);
    }
    public BadPinException(String msg,Throwable cause){
        super(msg,cause);
    }
}

