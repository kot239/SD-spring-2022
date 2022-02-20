package ru.hse.sd.cli;


import ru.hse.sd.cli.exceptions.ParserException;

/**
 * Class that have information about argument and makes
 * substitution and assignment if it's necessary
 */
public class RawArg {
    /**
     * Field that finally should have finished command or argument of a command
     */
    public String arg;
    private final boolean can_substitute;

    private static final char EQUATION = '=';
    private static final char SUBSTITUTION = '$';

    public RawArg(String arg, boolean can_substitute) {
        this.arg = arg;
        this.can_substitute = can_substitute;
    }

    private String substitute(Memory memory) {
        while (this.arg.indexOf(SUBSTITUTION) >= 0) {
            int substitution_pos = this.arg.indexOf(SUBSTITUTION);
            int end_pos = substitution_pos + 1;
            while (end_pos < this.arg.length()
                    && this.arg.charAt(end_pos) != SUBSTITUTION
                    && this.arg.charAt(end_pos) != ' '
                    && this.arg.charAt(end_pos) != '"'
                    && this.arg.charAt(end_pos) != '\'') {
                end_pos++;
            }
            var value = memory.get(this.arg.substring(substitution_pos + 1, end_pos));
            if (value == null) {
                value = "";
            }
            this.arg = this.arg.substring(0, substitution_pos)
                    + value + this.arg.substring(end_pos);
        }
        return this.arg;
    }

    private String equate(Memory memory) {
        int equation_pos = this.arg.indexOf(EQUATION);
        memory.put(this.arg.substring(0, equation_pos), this.arg.substring(equation_pos + 1));
        return "";
    }

    /**
     * Makes finished String from RawArg by substitution or assignment
     *
     * @param memory The storage of parameters
     *
     * @return Finished String that could be used as a command or parameter
     */
    public String prepareArg(Memory memory) {
        if (!can_substitute) {
            return this.arg;
        }
        if (this.arg.indexOf(EQUATION) >= 0) {
            return equate(memory);
        }
        if (this.arg.indexOf(SUBSTITUTION) >= 0) {
            return substitute(memory);
        }
        return this.arg;
    }
}
