package de.hska.github.matcher;

import java.util.regex.Pattern;


public class ScalaPromiseMatcher extends AbstractMatcher {

    // synonyms
    private Pattern[] _patterns = {Pattern.compile("scala.concurrent.Promise"),
            Pattern.compile("scala.concurrent.impl.Promise"), Pattern.compile("scala.concurrent.promise")};

    @Override
    String getName() {
        return "scala.concurrent.Promise";
    }

    @Override
    Pattern[] getPatterns() {
        return _patterns;
    }
}
