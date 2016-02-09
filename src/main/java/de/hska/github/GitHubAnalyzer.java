package de.hska.github;

import de.hska.github.context.DefaultProcessContext;
import de.hska.github.step.AbstractProcessingStep.Step;
import de.hska.github.step.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class GitHubAnalyzer {

    private Logger _log = Logger.getLogger(GitHubAnalyzer.class);

    public void process() {
        _log.info("start processing");
        _log.info("creating context");
        DefaultProcessContext context = new DefaultProcessContext();
        context.addStepToSkip(Step.SEARCH);
        context.addStepToSkip(Step.ANALYZE_SCALA_CONCURRENCY);
        context.addStepToSkip(Step.ANALYZE_JAVA_CONCURRENCY);
        context.addStepToSkip(Step.ANALYZE_ACTORS);
        context.addStepToSkip(Step.ANALYZE_SYNCHRONIZED);
//      context.addStepToSkip(Step.REPO_AGGREGATION);
        context.addStepToSkip(Step.OVERALL_AGGREGATION);
        context.addStepToSkip(Step.COMBINATIONS_AGGREGATION);
        _log.info("creating processing steps");
        List<ProcessingStep> steps = new ArrayList<ProcessingStep>();
        steps.add(new ScalaRepositoriesSearchStep());
        steps.add(new ScalaRepositoryScalaConcurrencyAnalyzingStep());
        steps.add(new ScalaRepositoryJavaConcurrencyAnalyzingStep());
        steps.add(new ScalaRepositoryActorsAnalyzingStep());
        steps.add(new ScalaRepositorySynchronizedAnalyzingStep());
        steps.add(new FilterBasedPerRepositoryAggregationStep());
        steps.add(new OverallAggregationStep());
        steps.add(new CombinationsAggregationStep());
        for (ProcessingStep processingStep : steps) {
            _log.info("executing processing step: " + processingStep.getStep().toString());
            processingStep.process(context);
        }
        _log.info("finished processing all steps");
        _log.info("shutting down");
        _log.info("have fun");
    }

    public static void main(String[] args) {
        GitHubAnalyzer analyzer = new GitHubAnalyzer();
        analyzer.process();
    }
}
