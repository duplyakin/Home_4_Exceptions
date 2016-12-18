import Exceptions.BadSumException;

/**
 * Created by Vlad on 12.11.2016.
 */
public class TerminalServer {
    private double money;

    double checkAmount () {
        return money;
    }


     boolean checkSum(double sum) { //BigDecimal better
        return sum % 100 == 0;
    }

    void putMoney(double  sum) {
        if (checkSum(sum)) {
            money += sum;
        }
    }

    void withdrawMoney(double sum) throws BadSumException {
        if (checkSum(sum)) {
            if (this.money >= sum) {
                money -= sum;
            } else {
                throw new BadSumException("There is no such amount of money on your account!");
            }
        }
    }
}
