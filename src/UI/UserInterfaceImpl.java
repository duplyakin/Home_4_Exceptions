package UI;

/**
 * Created by Vlad on 18.12.2016.
 */
public class UserInterfaceImpl implements UserInterface{
    @Override
    public void showError(Throwable t) {

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
