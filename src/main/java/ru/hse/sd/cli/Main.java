package main.java.ru.hse.sd.cli;

public class Main {

    public static void main(String[] args) {
        Executor executor = new Executor();
        while (true) {
            String input = System.console().readLine();
            executor.execute(input);
        }
    }

}