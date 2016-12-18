package UI;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Vlad on 15.11.2016.
 */
public interface UserInterface {

    void showError(Throwable t);
    void showError(String message);
    void showMessage(String message);
    String askQuestion(String message);
    int getPin();
    int getCommand();
    double getAmountOfMoney();

}
