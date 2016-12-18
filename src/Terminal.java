import Exceptions.*;

/**
 * Created by Vlad on 12.11.2016.
 */
public interface Terminal {
    double checkAmount() throws BadSumException, BadPinException, IsNotLoggedInException, AccountIsLockedException;

    void putMoney(double sum) throws IsNotLoggedInException, AccountIsLockedException, BadSumException;

    void withdrawMoney(double sum) throws BadSumException, IsNotLoggedInException, AccountIsLockedException;

    void checkPin(int pincode) throws BadPinException;
}
