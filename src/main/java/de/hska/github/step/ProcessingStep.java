package de.hska.github.step;

import de.hska.github.context.ProcessContext;
import de.hska.github.step.AbstractProcessingStep.Step;


public interface ProcessingStep {

    void process(ProcessContext context);

    Step getStep();

    boolean skipStep(ProcessContext context);
}
