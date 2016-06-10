package hberumen.me.androidchat.login;

/**
 * Created by hberumen on 09/06/16.
 */
public interface LginInteractor {
    void checkSession();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);
}
