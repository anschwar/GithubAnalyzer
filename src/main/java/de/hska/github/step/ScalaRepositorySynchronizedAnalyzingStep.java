package de.hska.github.step;

import de.hska.github.context.DefaultProcessContext;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScalaRepositorySynchronizedAnalyzingStep extends ScalaRepositoryAnalyzingStep {

    private static final String QUERY = "synchronized";
    private static final Pattern PATTERN = Pattern.compile(".*synchronized.*");


    public ScalaRepositorySynchronizedAnalyzingStep() {
        setLogger(Logger.getLogger(ScalaRepositorySynchronizedAnalyzingStep.class));
    }

    protected String getSearchResultName() {
        return DefaultProcessContext.SYNCHRONIZED_SEARCH_RESULT;
    }

    @Override
    public Step getStep() {
        return Step.ANALYZE_SYNCHRONIZED;
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
