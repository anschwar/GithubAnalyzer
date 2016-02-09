package de.hska.github.matcher;

import java.util.regex.Pattern;


public class ScalaFutureMatcher extends AbstractMatcher {

    // synonyms
    private Pattern[] _patterns = {Pattern.compile("scala.concurrent.Future"),
            Pattern.compile("scala.concurrent.impl.Future"), Pattern.compile("scala.concurrent.future")};

    @Override
    String getName() {
        return "scala.concurrent.Future";
    }

    @Override
    Pattern[] getPatterns() {
        return _patterns;
    }
}
