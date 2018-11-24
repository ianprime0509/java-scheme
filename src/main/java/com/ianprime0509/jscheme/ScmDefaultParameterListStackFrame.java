package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmEnvironment;
import com.ianprime0509.jscheme.types.ScmProcedure;
import com.ianprime0509.jscheme.types.ScmValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class ScmDefaultParameterListStackFrame extends ScmBasicStackFrame
    implements ScmParameterListStackFrame {
  private final Queue<ScmValue> parameters;

  private final List<ScmValue> evaluated;

  private final ScmProcedure procedure;

  ScmDefaultParameterListStackFrame(
      final ScmEnvironment environment,
      final ScmStackFrame parent,
      final Queue<ScmValue> parameters,
      final List<ScmValue> evaluated,
      final ScmProcedure procedure) {
    super(environment, parent);
    this.parameters = parameters;
    this.evaluated = evaluated;
    this.procedure = procedure;
  }

  @Override
  public ScmStackFrame copyWithParent(final ScmStackFrame parent) {
    return new ScmDefaultParameterListStackFrame(
        environment, parent != null ? parent.copy() : null, parameters, evaluated, procedure);
  }

  @Override
  public ScmProcedure getProcedure() {
    return procedure;
  }

  @Override
  public boolean hasNextParameter() {
    return !parameters.isEmpty();
  }

  @Override
  public ScmValue nextParameter(ScmEvaluator evaluator, ScmExecutionManager executionManager) {
    final ScmValue value = evaluator.evaluateFully(parameters.poll(), executionManager, this);
    evaluated.add(value);
    return value;
  }

  @Override
  public ScmCallStackFrame prepareCall() {
    return procedure.apply(new ArrayList<>(evaluated));
  }
}
