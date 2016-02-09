package de.hska.github.step;

import de.hska.github.context.ProcessContext;
import de.hska.github.result.ObjectFactory;
import org.apache.log4j.Logger;


public abstract class AbstractProcessingStep implements ProcessingStep {

    protected ObjectFactory _objectFactory = new ObjectFactory();
    protected Logger _logger;

    public abstract void process(ProcessContext context);

    public abstract Step getStep();

    public boolean skipStep(ProcessContext context) {
        if (context.getStepsToSkip().contains(getStep())) {
            _logger.info("skipping step " + getStep().toString());
            return true;
        }
        return false;
    }

    protected void setLogger(Logger logger) {
        _logger = logger;
    }

    public enum Step {

        SEARCH("search"), //
        ANALYZE_SCALA_CONCURRENCY("analyzeScalaConcurrency"), //
        ANALYZE_JAVA_CONCURRENCY("analyzeJavaConcurrency"), //
        ANALYZE_ACTORS("analyzeActors"), //
        ANALYZE_SYNCHRONIZED("analyzeSynchronized"), //
        REPO_AGGREGATION("aggregationPerRepo"), //
        COMBINATIONS_AGGREGATION("combinationsOverall"), //
        OVERALL_AGGREGATION("aggregationOverall");

        private final String _name;

        Step(String name) {
            _name = name;
        }

        public String toString() {
            return _name;
        }
    }
}
