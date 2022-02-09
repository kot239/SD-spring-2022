package ru.hse.sd.cli;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that save information about params
 */
public class Memory {
    private final Map<String, String> storage;

    public Memory() {
        storage = new HashMap<>();
        Map<String, String> map = System.getenv();
        storage.putAll(map);
    }

    /**
     * Method return the value of parameter by the name
     *
     * @param key name of the parameter
     *
     * @return value of parameter
     */
    public String get(String key) {
        return storage.get(key);
    }

    /**
     * Put the value of parameter by the name
     *
     * @param key name of the parameter
     * @param value value of parameter
     */
    public void put(String key, String value) {
        storage.put(key, value);
    }

    /**
     * Put all the values of parameters by their names
     */
    public void putAll(Map<String, String> m) {
        storage.putAll(m);
    }
}