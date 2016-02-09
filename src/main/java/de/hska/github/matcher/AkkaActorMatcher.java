package de.hska.github.matcher;

import java.util.regex.Pattern;


public class AkkaActorMatcher extends AbstractMatcher {

    private Pattern[] _patterns = {Pattern.compile("akka.actor.Actor")};

    @Override
    String getName() {
        return null;
    }

    @Override
    Pattern[] getPatterns() {
        return _patterns;
    }
}
