package de.hska.github.matcher;

import java.util.List;


public interface TextMatcher {

    List<String> apply(List<String> input);
}
