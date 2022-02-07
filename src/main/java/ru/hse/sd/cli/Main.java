package main.java.ru.hse.sd.cli;

public class Main {

    public static void main(String[] args) {
        String input = System.console().readLine();
        Executor executor = new Executor();
        executor.execute(input);
    }

}