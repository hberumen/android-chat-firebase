package hberumen.me.androidchat.login;

/**
 * Created by hberumen on 09/06/16.
 */
public interface LoginPresenter {
    void onDestroy();
    void checkForAuthenticatedUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);


}
