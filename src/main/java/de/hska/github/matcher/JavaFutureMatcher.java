package de.hska.github.matcher;

import java.util.regex.Pattern;


public class JavaFutureMatcher extends AbstractMatcher {

    private Pattern[] _patterns = {Pattern.compile("java.util.concurrent.Future"),
            Pattern.compile("java.util.concurrent.RunnableFuture"), Pattern.compile("java.util.concurrent.FutureTask")};

    @Override
    String getName() {
        return "java.util.concurrent.Future";
    }

    @Override
    Pattern[] getPatterns() {
        return _patterns;
    }
}
