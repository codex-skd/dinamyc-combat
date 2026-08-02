package com.skd.dinamyccombat.api.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Publisher<T> {

    private final List<Consumer<T>> listeners = new ArrayList<>();

    public void register(Consumer<T> listener) {
        listeners.add(listener);
    }

    public void unregister(Consumer<T> listener) {
        listeners.remove(listener);
    }

    public void publish(T event) {
        for (Consumer<T> listener : listeners) {
            listener.accept(event);
        }
    }

    public void clear() {
        listeners.clear();
    }

    public int listenerCount() {
        return listeners.size();
    }
}
