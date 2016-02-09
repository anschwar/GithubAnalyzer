package de.hska.github.matcher;

import java.util.regex.Pattern;


public class ScalaLockMatcher extends AbstractMatcher {

    private Pattern[] _patterns = {Pattern.compile("scala.concurrent.stm")};

    @Override
    String getName() {
        return null;
    }

    @Override
    Pattern[] getPatterns() {
        return _patterns;
    }
}
