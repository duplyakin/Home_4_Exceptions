import Exceptions.*;

/**
 * Created by Vlad on 07.11.2016.
 */
public class TerminalImpl implements Terminal {
    private final TerminalServer server;
    private final PinValidator pinValidator;
    public AccountState accountState = AccountState.UNLOCKED;
    private long startTime = 0;
    private static final long SLEEP_TIME = 5000;
    private long remainedTime = startTime;

    // Dependency Injection (DI) constructor
    public TerminalImpl(TerminalServer server, PinValidator pinValidator) {
        this.server = server;
        this.pinValidator = pinValidator;
    }

    private void checkTime() {
        long passedTime = System.currentTimeMillis() - startTime;
        if (passedTime < SLEEP_TIME) {
            lockAccount();
            remainedTime = 5000 - passedTime;
        } else {
            unlockAccount();
        }
    }

    private boolean isAccountLocked() {
        return accountState == AccountState.LOCKED;

    }

    private void lockAccount() {
        accountState = AccountState.LOCKED;
    }

    private void unlockAccount() {
        accountState = AccountState.UNLOCKED;
    }


    @Override
    public void checkPin(int pincode) throws BadPinException {
        try {
            if (!pinValidator.checkPin(pincode)) {
                throw new BadPinException("Incorrect pin code!");
            }
        } catch (PinExhaustedException e) {
            lockAccount();
            startTime = System.currentTimeMillis();
        }
    }

    // лучше выкинуть(throw) этот exception потому что перехватывать должен какой-то клиентский код,
    // который отреагирует и нарисует диалог о том, что pin code неверный. А не та функция,
    // которая проверяет. Перехватывать должен UI, который рисует диалоги.
    @Override
    public double checkAmount() throws IsNotLoggedInException, AccountIsLockedException {
        if (pinValidator.isPinEntered()) {
            checkTime();
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
            checkTime();
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
            checkTime();
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
