package hberumen.me.androidchat.lib;

/**
 * Created by hberumen on 10/06/16.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
