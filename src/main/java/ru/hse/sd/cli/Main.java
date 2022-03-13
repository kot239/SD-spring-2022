package ru.hse.sd.cli;

public class Main {

    public static void main(String[] args) {
        Executor executor = new Executor();
        while (true) {
            String input = System.console().readLine();
            var code = executor.execute(input);
            if (code == 1) {
                break;
            }
        }
    }

}