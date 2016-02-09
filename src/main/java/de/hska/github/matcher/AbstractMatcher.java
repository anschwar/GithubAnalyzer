package de.hska.github.matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class AbstractMatcher implements TextMatcher {

    @Override
    public List<String> apply(List<String> input) {
        List<String> result = null;
        for (String token : input) {
            for (Pattern pattern : getPatterns()) {
                Matcher matcher = pattern.matcher(token);
                if (matcher.find()) {
                    result = new ArrayList<String>();
                    if (null != getName()) {
                        result.add(getName());
                    } else {
                        result.add(matcher.group());
                    }
                }
            }
        }
        return result;
    }

    abstract String getName();

    abstract Pattern[] getPatterns();
}
