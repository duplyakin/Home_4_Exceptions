import Exceptions.AccountIsLockedException;
import Exceptions.BadPinException;
import Exceptions.BadSumException;
import Exceptions.IsNotLoggedInException;

/**
 * Created by Vlad on 12.11.2016.
 */
public interface Terminal {
    double checkAmount() throws BadSumException, BadPinException, IsNotLoggedInException, AccountIsLockedException;

    void putMoney(double sum) throws IsNotLoggedInException, AccountIsLockedException, BadSumException;

    void withdrawMoney(double sum) throws BadSumException, IsNotLoggedInException, AccountIsLockedException;

    boolean checkPin(int pincode);
}
