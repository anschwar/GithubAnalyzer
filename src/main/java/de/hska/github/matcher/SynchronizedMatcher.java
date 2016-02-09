package de.hska.github.matcher;

import java.util.regex.Pattern;


public class SynchronizedMatcher extends AbstractMatcher {

    // synonyms
    private Pattern[] _patterns = {Pattern.compile("synchronized")};

    @Override
    String getName() {
        return "Synchronized";
    }

    @Override
    Pattern[] getPatterns() {
        return _patterns;
    }
}
