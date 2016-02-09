package de.hska.github.matcher;

import java.util.ArrayList;
import java.util.List;


public class MatcherChain {

    private TextMatcher[] _matchers;

    public MatcherChain(TextMatcher... matchers) {
        _matchers = matchers;
    }

    public List<String> doMatch(String input) {
        if (null == input || input.isEmpty()) return null;
        List<String> expand = expand(input);
        List<String> result = null;
        for (TextMatcher matcher : _matchers) {
            result = matcher.apply(expand);
            if (null != result) break;
        }
        return result;
    }

    protected List<String> expand(String input) {
        int start = input.indexOf('{');
        int end = input.indexOf('}');
        List<String> result = new ArrayList<String>();
        if (start > 0 && end > 0 && end > start) {
            String prefix = input.substring(0, start);
            String substring = input.substring(start + 1, end);
            String[] splits = substring.split(",");
            for (String split : splits) {
                result.add(prefix + removeAlias(split));
            }
        } else {
            result.add(input);
        }
        return result;
    }

    private String removeAlias(String input) {
        String alias = input;
        if (input.contains("⇒")) {
            String[] split = input.split("⇒");
            alias = split[0];
        } else if (input.contains("=>")) {
            String[] split = input.split("=>");
            alias = split[0];
        }
        return alias.trim();
    }

}
