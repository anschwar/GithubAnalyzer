package de.hska.github.step;

import de.hska.github.context.DefaultProcessContext;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScalaRepositoryJavaConcurrencyAnalyzingStep extends ScalaRepositoryAnalyzingStep {

    private static final String QUERY = "\"java.util.concurrent\"";
    private static final Pattern PATTERN = Pattern.compile(".*java.util.concurrent.*");


    public ScalaRepositoryJavaConcurrencyAnalyzingStep() {
        setLogger(Logger.getLogger(ScalaRepositoryJavaConcurrencyAnalyzingStep.class));
    }

    protected String getSearchResultName() {
        return DefaultProcessContext.JAVA_SEARCH_RESULT;
    }

    @Override
    public Step getStep() {
        return Step.ANALYZE_JAVA_CONCURRENCY;
    }

    @Override
    protected String getQuery() {
        return QUERY;
    }

    @Override
    protected Matcher matcher(String input) {
        return PATTERN.matcher(input);
    }

    @Override
    protected boolean skipRepo(String repo) {
        return false;
    }
}
