import Exceptions.*;
import UI.UserInterface;
import UI.UserInterfaceConsole;

import java.util.Scanner;

/**
 * Created by Vlad on 15.11.2016.
 */
public class Main {

    public static void main(String[] args) {
        TerminalServer terminalServer = new TerminalServer();
        PinValidator pinValidator = new PinValidator();
        UserInterface userInterface = new UserInterfaceConsole();
        Terminal terminal = new TerminalImpl(terminalServer, pinValidator);


        while (true) {
            int pincode;
            userInterface.showMessage("Enter pin code: ");
            Scanner sc = new Scanner(System.in);
            pincode = userInterface.getPin();


            try {
                if (terminal.checkPin(pincode)) {
                    userInterface.showMessage("1 - check my cash, 2 - put money, 3 - withdraw money, 4 - exit");
                    switch (sc.nextInt()) {

                        case 1:

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

                        case 2:

                            userInterface.showMessage("enter amount of money to put: ");

                            try {
                                terminal.putMoney(sc.nextDouble());
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

                        case 3:

                            userInterface.showMessage("enter amount of money to withdraw:  ");

                            try {
                                terminal.withdrawMoney(sc.nextDouble());
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

                        case 4:
                            break;

                    }
                } else {

                    userInterface.showError("Pin code is incorrect!");
                }
            } catch (BadPinException e) {
                e.printStackTrace();
            }
        }


    }
}
