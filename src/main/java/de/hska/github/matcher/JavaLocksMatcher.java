package de.hska.github.matcher;

import java.util.regex.Pattern;


public class JavaLocksMatcher extends AbstractMatcher {

    private Pattern _pattern = Pattern.compile("java.util.concurrent.locks");

    @Override
    String getName() {
        return null;
    }

    @Override
    Pattern[] getPatterns() {
        return new Pattern[]{_pattern};
    }
}
