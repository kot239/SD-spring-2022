package main.java.ru.hse.sd.cli;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Class that parse String to the RawArgs (this is arguments that are not
 * prepared yet, that means there wasn't any substitution or equating)
 * @author German Tarabonda
 */
public class Parser {
    private final char DOUBLE_QUOTES = '\"';
    private final char SINGLE_QUOTES = '\'';


    private int cnt_of_symbols(String line, char symbol) {
        int cnt_of_symbol = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == symbol) {
                cnt_of_symbol++;
            }
        }
        return cnt_of_symbol;
    }

    private void check_correctness(String line) throws Exception {
        int single_quotes = cnt_of_symbols(line, SINGLE_QUOTES);
        int double_quotes = cnt_of_symbols(line, DOUBLE_QUOTES);
        if (single_quotes % 2 != 0 && double_quotes % 2 != 0) {
            throw new Exception("Incorrect command");
        }
    }

    private static class Borders {
        char symbol;
        int left;
        int right;
    }

    private Borders find_borders(String line, char symbol) {
        Borders borders = new Borders();
        borders.symbol = symbol;
        borders.left = 0;
        borders.right = line.length() - 1;
        while (line.charAt(borders.left) != symbol) {
            borders.left++;
        }
        while (line.charAt(borders.right) != symbol) {
            borders.right--;
        }
        return borders;
    }

    private List<RawArg> tokenize(String line) {
        StringTokenizer st = new StringTokenizer(line);
        List<RawArg> tokens = new LinkedList<>();
        while (st.hasMoreTokens()) {
            tokens.add(new RawArg(st.nextToken(), true));
        }
        return tokens;
    }

    private List<RawArg> tokenize_quotes(String line, Borders borders, boolean can_substitute) {
        List<RawArg> res = new LinkedList<>();
        List<RawArg> left = tokenize(line.substring(0, borders.left));
        res.addAll(left);
        res.add(new RawArg(line.substring(borders.left + 1, borders.right), can_substitute));
        List<RawArg> right = tokenize(line.substring(borders.right + 1));
        res.addAll(right);
        return res;
    }

    private List<RawArg> tokenize_with_quotes(String line) throws Exception {
        Borders single_borders = null;
        Borders double_borders = null;
        if (cnt_of_symbols(line, SINGLE_QUOTES) > 0) {
            single_borders = find_borders(line, SINGLE_QUOTES);
        }
        if (cnt_of_symbols(line, DOUBLE_QUOTES) > 0) {
            double_borders = find_borders(line, DOUBLE_QUOTES);
        }
        if (single_borders == null && double_borders == null) {
            return tokenize(line);
        }
        if (single_borders == null) {
            return tokenize_quotes(line, double_borders, false); // will be true
        }
        if (double_borders == null) {
            return tokenize_quotes(line, single_borders, false);
        }
        if (double_borders.left <= single_borders.left &&
                single_borders.right <= double_borders.right) {
            return tokenize_quotes(line, double_borders, false); // will be true
        }
        if (single_borders.left <= double_borders.left &&
                double_borders.right <= single_borders.right) {
            return tokenize_quotes(line, single_borders, false);
        }
        throw new Exception("Incorrect input");
    }


    private List<List<RawArg>> work_with_pipes(String input) throws Exception {
        Borders single_borders = null;
        Borders double_borders = null;
        if (cnt_of_symbols(input, SINGLE_QUOTES) > 0) {
            single_borders = find_borders(input, SINGLE_QUOTES);
        }
        if (cnt_of_symbols(input, DOUBLE_QUOTES) > 0) {
            double_borders = find_borders(input, DOUBLE_QUOTES);
        }
        List<Integer> pipes = new LinkedList<>();
        for (int i = 0; i < input.length(); i++) {
            char PIPE = '|';
            if (input.charAt(i) == PIPE) {
                if (single_borders != null) {
                    if (single_borders.left <= i &&
                        i <= single_borders.right) {
                        continue;
                    }
                }
                if (double_borders != null) {
                    if (double_borders.left <= i &&
                            i <= double_borders.right) {
                        continue;
                    }
                }
                pipes.add(i);
            }
        }
        List<List<RawArg>> res = new LinkedList<>();
        int cur_start = 0;
        for (Integer pipe_pos: pipes) {
            res.add(tokenize_with_quotes(input.substring(cur_start, pipe_pos)));
            cur_start = pipe_pos + 1;
        }
        res.add(tokenize_with_quotes(input.substring(cur_start)));
        return res;
    }

    /**
     * Method return the list of commands.
     * Each command has a list of RawArgs.
     *
     * @param input String that was given to console
     *
     * @return list of commands
     */
    public List<List<RawArg>> parse(String input) {
        try {
            check_correctness(input);
            return work_with_pipes(input);
        } catch (Exception ex) {
            return new LinkedList<>();
        }
    }
}