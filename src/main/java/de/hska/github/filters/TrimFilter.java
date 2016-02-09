package de.hska.github.filters;


public class TrimFilter implements Filter {

    @Override
    public String apply(String input) {
        return input.trim();
    }

}
