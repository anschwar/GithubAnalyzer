package de.hska.github.matcher;

import java.util.regex.Pattern;


public class ScalaStmMatcher extends AbstractMatcher {

    private Pattern[] _patterns = {Pattern.compile("scala.concurrent.Lock")};

    @Override
    String getName() {
        return null;
    }

    @Override
    Pattern[] getPatterns() {
        return _patterns;
    }
}
