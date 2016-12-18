package UI;

import java.util.Scanner;

/**
 * Created by Vlad on 18.12.2016.
 */
public class UserInterfaceConsole implements UserInterface{
    @Override
    public void showError(Throwable t) {

    }

    @Override
    public int getPin() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    @Override
    public double getAmountOfMoney() {
        Scanner sc = new Scanner(System.in);
        return sc.nextDouble();
    }

    public int getCommand() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }


    @Override
    public void showError(String message) {
        System.out.println(message);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String askQuestion(String message) {
        return null;
    }
}
