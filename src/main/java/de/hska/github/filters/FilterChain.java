package de.hska.github.filters;


public class FilterChain {

    private Filter[] _filters;

    public FilterChain(Filter... filters) {
        _filters = filters;
    }

    public String doFilter(String input) {
        if (null == input || input.isEmpty()) return input;
        String nextInput = input;
        for (Filter filter : _filters) {
            nextInput = filter.apply(nextInput);
            if (null == nextInput) break;
        }
        return nextInput;
    }
}
