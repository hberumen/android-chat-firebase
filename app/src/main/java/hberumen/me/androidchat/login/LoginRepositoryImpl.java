package hberumen.me.androidchat.login;

import android.util.Log;

import hberumen.me.androidchat.domain.FireBaseHelper;

/**
 * Created by hberumen on 10/06/16.
 */
public class LoginRepositoryImpl implements LoginRepository {
    private FireBaseHelper helper;

    public LoginRepositoryImpl() {
        helper = FireBaseHelper.getInstance();
    }

    @Override
    public void signUp(String email, String password) {
        Log.e("LoginRepository","signup");
    }

    @Override
    public void signIn(String email, String password) {
        Log.e("LoginRepository","signin");
    }

    @Override
    public void checkSession() {
        Log.e("LoginRepository","checkSession");
    }
}
