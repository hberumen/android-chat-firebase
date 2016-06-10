package hberumen.me.androidchat.login;

/**
 * Created by hberumen on 09/06/16.
 */
public interface LoginRepository {
    void signUp(String email, String password);
    void signIn(String email, String password);
    void checkSession();
}
