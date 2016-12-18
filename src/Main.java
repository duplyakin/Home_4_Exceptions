import Exceptions.AccountIsLockedException;
import Exceptions.BadPinException;
import Exceptions.BadSumException;
import Exceptions.IsNotLoggedInException;
import UI.UserInterfaceImpl;

import java.util.Scanner;

/**
 * Created by Vlad on 15.11.2016.
 */
public class Main {

    public static void main(String[] args) {
        TerminalServer terminalServer = new TerminalServer();
        PinValidator pinValidator = new PinValidator();
        UserInterfaceImpl userInterface = new UserInterfaceImpl();
        TerminalImpl terminalImpl = new TerminalImpl(terminalServer, pinValidator, userInterface);

        while (true) {
            int pincode;
            userInterface.showMessage("Enter pin code: ");
            Scanner sc = new Scanner(System.in);
            pincode = sc.nextInt();


            if (terminalImpl.checkPin(pincode)) {
                System.out.println("1 - check my cash, 2 - put money, 3 - withdraw money, 4 - exit");
                switch (sc.nextInt()) {

                    case 1:

                        try {
                            userInterface.showMessage("Your cash is: " +String.valueOf(terminalImpl.checkAmount()));
                        } catch (IsNotLoggedInException e) {
                            //e.printStackTrace();
                            userInterface.showError("You are not logged in. Enter pin code");
                        } catch (AccountIsLockedException e) {
                            //e.printStackTrace();
                            userInterface.showError("Your account is locked for " + terminalImpl.getRemainedTime() + " sec");
                        }

                        break;

                    case 2:

                        System.out.println("enter amount of money to put: ");

                        try {
                            terminalImpl.putMoney(sc.nextDouble());
                            userInterface.showMessage("Your cash is: " + String.valueOf(terminalImpl.checkAmount()));
                        } catch (IsNotLoggedInException e) {
                            //e.printStackTrace();
                            userInterface.showError("You are not logged in. Enter pin code");
                        } catch (AccountIsLockedException e) {
                            //e.printStackTrace();
                            userInterface.showError("Your account is locked for " + terminalImpl.getRemainedTime() + " sec");
                        } catch (BadSumException e) {
                            //e.printStackTrace();
                            userInterface.showError("Sum is not multiply if 100");
                        }

                        break;

                    case 3:

                        System.out.println("enter amount of money to withdraw:  ");

                        try {
                            terminalImpl.withdrawMoney(sc.nextDouble());
                            userInterface.showMessage("Your cash is: " + String.valueOf(terminalImpl.checkAmount()));
                        } catch (IsNotLoggedInException e) {
                            //e.printStackTrace();
                            userInterface.showError("You are not logged in. Enter pin code");
                        } catch (AccountIsLockedException e) {
                            //e.printStackTrace();
                            userInterface.showError("Your account is locked for " + terminalImpl.getRemainedTime() + " sec");
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
            }
        }






    }
}
