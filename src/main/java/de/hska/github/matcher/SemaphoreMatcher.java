package de.hska.github.matcher;

import java.util.regex.Pattern;


public class SemaphoreMatcher extends AbstractMatcher {

    // synonyms
    private Pattern[] _patterns = {Pattern.compile("java.util.concurrent.Semaphore")};

    @Override
    String getName() {
        return "Semaphore";
    }

    @Override
    Pattern[] getPatterns() {
        return _patterns;
    }
}
