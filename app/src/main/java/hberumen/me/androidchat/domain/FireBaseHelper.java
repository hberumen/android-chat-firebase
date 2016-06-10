package hberumen.me.androidchat.domain;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hberumen on 09/06/16.
 */
public class FireBaseHelper {
    private Firebase dataReference;
    private final static String SEPARATOR = "___";
    private final static String USERS_PATH = "users";
    private final static String CHATS_PATH = "chats";
    private final static String CONTACTS_PATH = "contacts";
    private final static String FIREBASE_URL = "https://android-chat-example.firebaseio.com";

    private static class SingletonHolder {
        private static final FireBaseHelper INSTANCE = new FireBaseHelper();
    }


    public static FireBaseHelper getInstance(){
        return     SingletonHolder.INSTANCE;
    }

    public FireBaseHelper(){
        this.dataReference = new Firebase(FIREBASE_URL);
    }

    public Firebase getDataReference() {
        return dataReference;
    }

    public String getAuthUserEmail(){
        AuthData authData = dataReference.getAuth();
        String email = null;
        if(authData != null){
            Map<String, Object> providerData = authData.getProviderData();
            email = providerData.get("email").toString();
        }
        return email;
    }

    public Firebase getUserReference(String email){
        Firebase userReference = null;

        if(email == null){
            return null;
        }

        String emailKey = replaceEmailDotToUnderScore(email);
        userReference = dataReference
                            .getRoot()
                            .child(USERS_PATH)
                            .child(emailKey);

        return userReference;
    }

    public Firebase getMyUserReference(){
        return getUserReference(getAuthUserEmail());
    }

    public Firebase getOneContactReference(String mainEmail, String childEmail){
        String childKey = replaceEmailDotToUnderScore(childEmail);
        return getUserReference(mainEmail)
                    .child(CONTACTS_PATH)
                    .child(childKey);
    }

    public Firebase getChartsReference(String receiver){
        String keySender = replaceEmailDotToUnderScore(getAuthUserEmail());
        String keyReceiver = replaceEmailDotToUnderScore(receiver);
        String keyChat = keySender+SEPARATOR+keyReceiver;

        if(keySender.compareTo(keyReceiver)>0){
            keyChat = keyReceiver+SEPARATOR+keySender;
        }
        return dataReference
                    .getRoot()
                    .child(CHATS_PATH)
                    .child(keyChat);
    }

    public void changeUserConnectionStatus(boolean online){

        if(getMyUserReference() == null){
            return;
        }

        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("online", online);
        getMyUserReference().updateChildren(updates);
        notifyContactsOfConnectionChance(online);
    }

    public void notifyContactsOfConnectionChance(boolean online) {
        notifyContactsOfConnectionChance(online, false);
    }

    public void signOff(){
        notifyContactsOfConnectionChance(false, true);
    }

    private void notifyContactsOfConnectionChance(final boolean online, final boolean signoff) {
        final String myEmail = getAuthUserEmail();
        getMyUserReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()){
                            String email = child.getKey();
                            Firebase reference = getOneContactReference(email, myEmail);
                            reference.setValue(online);
                        }

                        if(!signoff){
                            return;
                        }

                        dataReference.unauth();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {}
                });

    }

    private String replaceEmailDotToUnderScore(String email) {
        return email.replace(".","_");
    }
}
