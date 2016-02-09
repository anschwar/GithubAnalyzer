package de.hska.github.step;

import de.hska.github.context.DefaultProcessContext;
import de.hska.github.context.ProcessContext;
import de.hska.github.result.AggregationResult;
import de.hska.github.result.AggregationResultEntry;
import de.hska.github.result.Feature;
import de.hska.github.result.Features;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class OverallAggregationStep extends AbstractProcessingStep {

    // the name of the feature and the corresponding count in how much repos is was used
    private Map<String, ResultEntry> featureMap = new HashMap<String, ResultEntry>();

    public OverallAggregationStep() {
        setLogger(Logger.getLogger(OverallAggregationStep.class));
    }

    @Override
    public void process(ProcessContext context) {
        if (skipStep(context)) return;
        if (!(context instanceof DefaultProcessContext)) {
            throw new IllegalArgumentException("Wrong context passed to overall aggregator");
        }
        DefaultProcessContext defaultContext = (DefaultProcessContext) context;
        aggregateData(defaultContext);
        List<ResultEntry> list = new ArrayList<>(featureMap.values());
        Collections.sort(list);
        // the create the result object
        storeResult(defaultContext, list);
    }

    private void storeResult(DefaultProcessContext context, List<ResultEntry> results) {
        AggregationResult aggregationResult = _objectFactory.createAggregationResult();
        context.setAggregationResult(aggregationResult, DefaultProcessContext.AGGREGATION_ALL);
        AggregationResultEntry entry = _objectFactory.createAggregationResultEntry();
        aggregationResult.getAggregationResultEntry().add(entry);
        entry.setRepositoryName("ALL");
        Features features = _objectFactory.createFeatures();
        entry.setFeatures(features);
        for (ResultEntry resultEntry : results) {
            Feature feature = _objectFactory.createFeature();
            feature.setValue(resultEntry.name.replaceAll("&gt;", ">"));
            feature.setCount(resultEntry.count.intValue());
            features.getFeature().add(feature);
        }
        context.storeAggregationResult(DefaultProcessContext.AGGREGATION_ALL);
    }

    private void aggregateData(DefaultProcessContext context) {
        AggregationResult aggregationResult = context.getAggregationResult(DefaultProcessContext.AGGREGATION_REPOS);
        if (null != aggregationResult) {
            List<AggregationResultEntry> aggregationEntry = aggregationResult.getAggregationResultEntry();
            for (AggregationResultEntry entry : aggregationEntry) {
                Features features = entry.getFeatures();
                if (null != features) {
                    for (Feature feature : features.getFeature()) {
                        if (null == featureMap.get(feature.getValue())) {
                            featureMap.put(feature.getValue(), new ResultEntry(feature.getValue()));
                        } else {
                            featureMap.get(feature.getValue()).increment();
                        }
                    }
                }
            }
        }
    }

    private class ResultEntry implements Comparable<ResultEntry> {

        protected String name;
        protected AtomicInteger count = new AtomicInteger(1);

        protected ResultEntry(String name) {
            this.name = name;
        }

        protected void increment() {
            count.incrementAndGet();
        }

        @Override
        public int compareTo(ResultEntry other) {
            return Integer.compare(other.count.intValue(), count.intValue());
        }

        @Override
        public String toString() {
            return name + "=" + count;
        }

    }

    @Override
    public Step getStep() {
        return Step.OVERALL_AGGREGATION;
    }

}
