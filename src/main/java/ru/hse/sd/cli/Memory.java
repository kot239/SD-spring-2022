package main.java.ru.hse.sd.cli;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    private final Map<String, String> storage;

    public Memory() {
        storage = new HashMap<>();
        Map<String, String> map = System.getenv();
        storage.putAll(map);
    }


    public String get(String key) {
        return storage.get(key);
    }

    public void put(String key, String value) {
        storage.put(key, value);
    }

    public void putAll(Map<String, String> m) {
        storage.putAll(m);
    }
}