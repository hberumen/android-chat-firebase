package hberumen.me.androidchat.login;

import android.util.Log;

import hberumen.me.androidchat.domain.FireBaseHelper;
import hberumen.me.androidchat.lib.EventBus;
import hberumen.me.androidchat.lib.GreenRobotEventBus;

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
        postEvent(LoginEvent.onSignUpSuccess);
    }

    @Override
    public void signIn(String email, String password) {
        postEvent(LoginEvent.onSignInSuccess);
    }

    @Override
    public void checkSession() {
        postEvent(LoginEvent.onFailedToRecoverSession);
    }

    private void postEvent(int typeEvent, String errorMessage){
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(typeEvent);
        if(errorMessage != null){
            loginEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

    private void postEvent(int typeEvent){
        postEvent(typeEvent,null);
    }
}
