import Exceptions.*;
import UI.UserInterface;
import UI.UserInterfaceConsole;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Scanner;

/**
 * Created by Vlad on 15.11.2016.
 */
public class Main {
    public static final int COMMAND_CHECK_CASH = 1;
    private static final int COMMAND_PUT_MONEY = 2;
    private static final int COMMAND_WITHDRAW_MONEY = 3;
    private static final int COMMAND_EXIT = 4;

    public static void main(String[] args) {
        TerminalServer terminalServer = new TerminalServer();
        PinValidator pinValidator = new PinValidator();
        UserInterface userInterface = new UserInterfaceConsole();
        Terminal terminal = new TerminalImpl(terminalServer, pinValidator);


        while (true) {
            userInterface.showMessage("Enter pin code: ");

            try {
                terminal.checkPin(userInterface.getPin());
                userInterface.showMessage("1 - check my cash, 2 - put money, 3 - withdraw money, 4 - exit");
                int command = userInterface.getCommand();
                switch (command) {
                    case COMMAND_CHECK_CASH:
                        try {
                            try {
                                userInterface.showMessage("Your cash is: " + String.valueOf(terminal.checkAmount()));
                            } catch (BadSumException e) {
                                //e.printStackTrace();
                            }
                        } catch (IsNotLoggedInException e) {
                            //e.printStackTrace();
                            userInterface.showError("You are not logged in. Enter pin code");
                        } catch (AccountIsLockedException e) {
                            //e.printStackTrace();
                            userInterface.showError(e.getMessage());
                        }
                        break;

                    case COMMAND_PUT_MONEY:
                        userInterface.showMessage("enter amount of money to put: ");
                        try {
                            double money;
                            terminal.putMoney(userInterface.getAmountOfMoney());
                            userInterface.showMessage("Your cash is: " + String.valueOf(terminal.checkAmount()));
                        } catch (IsNotLoggedInException e) {
                            //e.printStackTrace();
                            userInterface.showError("You are not logged in. Enter pin code");
                        } catch (AccountIsLockedException e) {
                            //e.printStackTrace();
                            userInterface.showError("Your account is locked");
                        } catch (BadSumException e) {
                            //e.printStackTrace();
                            userInterface.showError("Sum is not multiply if 100");
                        }
                        break;

                    case COMMAND_WITHDRAW_MONEY:
                        userInterface.showMessage("enter amount of money to withdraw:  ");
                        try {
                            terminal.withdrawMoney(userInterface.getAmountOfMoney());
                            userInterface.showMessage("Your cash is: " + String.valueOf(terminal.checkAmount()));
                        } catch (IsNotLoggedInException e) {
                            //e.printStackTrace();
                            userInterface.showError("You are not logged in. Enter pin code");
                        } catch (AccountIsLockedException e) {
                            //e.printStackTrace();
                            userInterface.showError("Your account is locked");
                        } catch (BadSumException e) {
                            //e.printStackTrace();
                            if (e.getMessage().equals("There is no such amount of money on your account!")) {
                                userInterface.showError("There is no such amount of money on your account!");
                            } else {
                                userInterface.showError("Sum is not multiply if 100");
                            }
                        }

                        break;

                    case COMMAND_EXIT:
                        break;

                }

            } catch (BadPinException e) {
                //e.printStackTrace();
                userInterface.showError(e.getMessage());
            }
        }


    }
}
