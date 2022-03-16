package ru.hse.sd.cli;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that save information about params and current working directory
 */
public class Memory {
    private final Map<String, String> storage;
    private Path currentDirectory = Paths.get(System.getProperty("user.dir"));

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
     * Method returns all stored key-values
     *
     * @return storage
     */
    public Map<String, String> getAll() {
        return storage;
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

    /**
     * Reset current directory to the initial one
     */
    public void resetCurrentDirectory() {
        currentDirectory = Paths.get(System.getProperty("user.dir"));
    }

    /**
     * @param path
     * Change current directory according to the path
     */
    public void changeCurrentDirectory(String path) {
        currentDirectory = currentDirectory.resolve(path);
    }

    /**
     * @param path
     * @return Path
     * Do not change the value of current directory, but try to do it and return the result
     */
    public Path resolveCurrentDirectory(String path) {
        return currentDirectory.resolve(path);
    }

    /**
     * @return path of current directory
     */
    public Path getCurrentDirectory() {
        return currentDirectory;
    }
}