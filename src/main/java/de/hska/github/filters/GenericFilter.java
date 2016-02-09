package de.hska.github.filters;

public class GenericFilter implements Filter {

    private String _sign;

    public GenericFilter(String sign) {
        _sign = sign;
    }

    @Override
    public String apply(String input) {
        if (input.startsWith(_sign)) return null;
        return input;
    }
}
