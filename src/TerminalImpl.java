import Exceptions.*;
import UI.UserInterface;

/**
 * Created by Vlad on 07.11.2016.
 */
public class TerminalImpl implements Terminal {
    private final TerminalServer server;
    private final PinValidator pinValidator;
    private final UserInterface userInterface;
    public AccountState accountState = AccountState.UNLOCKED;
    private long startTime = 0;
    private long sleepTime = 5000;
    private long remainedTime = startTime;

    // Dependency Injection (DI) constructor
    public TerminalImpl(TerminalServer server, PinValidator pinValidator, UserInterface userInterface) {
        this.server = server;
        this.pinValidator = pinValidator;
        this.userInterface = userInterface;
    }

    public long getRemainedTime() {
        return remainedTime;
    }

    private boolean isAccountLocked() {
        long passedTime = System.currentTimeMillis() - startTime;
        if (passedTime < sleepTime) {
            accountState = AccountState.LOCKED;
            remainedTime = 5000 - passedTime;
            return true;
        } else {
            accountState = AccountState.UNLOCKED;
            return false;
        }

    }

    private void lockAccount() {
        accountState = AccountState.LOCKED;
        startTime = System.currentTimeMillis();
    }


    @Override
    public boolean checkPin(int pincode) {
        try {
            pinValidator.checkPin(pincode);
        } catch (BadPinException e) {
            //e.printStackTrace();
            userInterface.showError("Pin code is incorrect!");
            return false;
        } catch (PinExhaustedException e) {
            //e.printStackTrace();
            userInterface.showError("Three wrong tries! Your account is locked for 5 seconds.");
            return false;
        }
        return true;
    }

    // лучше выкинуть(throw) этот exception потому что перехватывать должен какой-то клиентский код,
    // который отреагирует и нарисует диалог о том, что pin code неверный. А не та функция,
    // которая проверяет. Перехватывать должен UI, который рисует диалоги.
    @Override
    public double checkAmount() throws IsNotLoggedInException, AccountIsLockedException {
        if (pinValidator.isPinEntered()) {
            if (!isAccountLocked()) {
                return server.checkAmount();
            } else {
                throw new AccountIsLockedException(remainedTime / 1000);
            }

        } else {
            throw new IsNotLoggedInException("Not logged in!");
        }
    }

    @Override
    public void putMoney(double sum) throws IsNotLoggedInException, BadSumException, AccountIsLockedException {
        if (pinValidator.isPinEntered()) {
            if (!isAccountLocked()) {
                if (server.checkSum(sum)) {
                    server.putMoney(sum);
                } else {
                    throw new BadSumException("Sum is not multiply if 100");
                }
            } else {
                throw new AccountIsLockedException(remainedTime / 1000);
            }
        } else {
            throw new IsNotLoggedInException("Not logged in!");
        }

    }

    @Override
    public void withdrawMoney(double sum) throws IsNotLoggedInException, BadSumException, AccountIsLockedException {
        if (pinValidator.isPinEntered()) {
            if (!isAccountLocked()) {
                if (server.checkSum(sum)) {
                    server.withdrawMoney(sum);
                } else {
                    throw new BadSumException("Sum is not multiply if 100");
                }

            } else {
                throw new AccountIsLockedException(remainedTime / 1000);
            }
        } else {
            throw new IsNotLoggedInException("Not logged in!");
        }
    }
}
