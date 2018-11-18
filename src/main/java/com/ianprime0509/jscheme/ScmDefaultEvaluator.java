package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmEnvironment;
import com.ianprime0509.jscheme.types.ScmManagedProcedure;
import com.ianprime0509.jscheme.types.ScmPair;
import com.ianprime0509.jscheme.types.ScmProcedure;
import com.ianprime0509.jscheme.types.ScmSymbol;
import com.ianprime0509.jscheme.types.ScmValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class ScmDefaultEvaluator implements ScmManagedEvaluator {
  ScmDefaultEvaluator() {}

  @Override
  public ScmValue evaluate(final ScmValue expression, final ScmEnvironment evaluationEnvironment) {
    final ScmExecutionManager executionManager =
        ScmExecutionManager.newDefaultExecutionManager(this);
    final Result result = evaluateInternal(expression, executionManager, evaluationEnvironment, null);

    if (result.isCompleted()) {
      return result.getCompleted();
    }
    return executionManager.execute(result.getContinuing());
  }

  @Override
  public Result evaluateManaged(
      ScmValue expression, ScmExecutionManager executionManager, ScmStackFrame context) {
    return evaluateInternal(expression, executionManager, context.getExecutionEnvironment(), context);
  }

  private ScmValue evaluateFully(
      final ScmValue expression,
      final ScmExecutionManager executionManager,
      final ScmEnvironment executionEnvironment,
      final ScmStackFrame context) {
    final Result result = evaluateInternal(expression, executionManager, executionEnvironment, context);
    if (result.isCompleted()) {
      return result.getCompleted();
    }
    return executionManager.execute(result.getContinuing());
  }
  
  private Result evaluateInternal(
      final ScmValue expression,
      final ScmExecutionManager executionManager,
      final ScmEnvironment executionEnvironment,
      final ScmStackFrame context) {
    if (expression instanceof ScmPair) {
      final ScmPair pair = (ScmPair) expression;
      if (!pair.isList()) {
        throw new IllegalArgumentException("cannot evaluate non-list pair");
      }
      final ScmValue valueToApply = evaluateFully(pair.getCar(), executionManager, executionEnvironment, null);
      if (!(valueToApply instanceof ScmProcedure)) {
        throw new IllegalArgumentException("wrong type to apply: " + pair.getCar());
      }
      final ScmProcedure procedure = (ScmProcedure) valueToApply;

      final List<ScmValue> parameters = new ArrayList<>();
      ScmValue remaining = pair.getCdr();
      while (remaining instanceof ScmPair) {
        final ScmPair remainingPair = (ScmPair) remaining;
	parameters.add(evaluateFully(remainingPair.getCar(), executionManager, executionEnvironment, null));
        remaining = remainingPair.getCdr();
      }
      final Map<ScmSymbol, ScmValue> bindings =
          procedure.getParameterList().getExecutionBindings(parameters);

      if (procedure instanceof ScmManagedProcedure) {
        final ScmStackFrame frame =
            ((ScmManagedProcedure) procedure).getExecutionStackFrame(bindings);

        return Result.ofContinuing(ScmStackFrame.builder().inherit(frame).parent(context).build());
      }
      return Result.ofCompleted(procedure.apply(bindings));
    } else if (expression instanceof ScmSymbol) {
      final Optional<ScmValue> value = executionEnvironment.get((ScmSymbol) expression);
      if (!value.isPresent()) {
        throw new IllegalArgumentException("variable not bound");
      }
      return Result.ofCompleted(value.get());
    }
    return Result.ofCompleted(expression);
  }
}
