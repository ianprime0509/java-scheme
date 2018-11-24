package com.ianprime0509.jscheme.types;

import com.ianprime0509.jscheme.ScmBasicStackFrame;
import com.ianprime0509.jscheme.ScmCallStackFrame;
import com.ianprime0509.jscheme.ScmEvaluationResult;
import com.ianprime0509.jscheme.ScmEvaluator;
import com.ianprime0509.jscheme.ScmExecutionManager;
import com.ianprime0509.jscheme.ScmStackFrame;
import java.util.function.Function;

/**
 * An implementation of {@link ScmProcedure} that derives its functionality from a native Java
 * method (namely, an implementation of {@code Function<ScmEnvironment, ScmValue>}).
 */
class ScmJavaProcedure extends ScmAbstractProcedure {
  private final Function<ScmEnvironment, ScmValue> body;

  ScmJavaProcedure(
      final ScmEnvironment lexicalEnvironment,
      final ScmParameterList parameterList,
      final Function<ScmEnvironment, ScmValue> body) {
    super(lexicalEnvironment, parameterList);
    if (body == null) {
      throw new IllegalArgumentException("body must not be null");
    }
    this.body = body;
  }

  @Override
  public ScmCallStackFrame apply(
      final ScmEnvironment executionEnvironment, final ScmStackFrame context) {
    return new ScmJavaCallStackFrame(executionEnvironment, context, body);
  }
}

class ScmJavaCallStackFrame extends ScmBasicStackFrame implements ScmCallStackFrame {
  private final Function<ScmEnvironment, ScmValue> body;

  private boolean executed = false;

  ScmJavaCallStackFrame(
      final ScmEnvironment environment,
      final ScmStackFrame parent,
      final Function<ScmEnvironment, ScmValue> body) {
    super(environment, parent);
    this.body = body;
  }

  @Override
  public ScmStackFrame copyWithParent(ScmStackFrame parent) {
    final ScmJavaCallStackFrame copy =
        new ScmJavaCallStackFrame(environment, parent != null ? parent.copy() : null, body);
    copy.executed = executed;
    return copy;
  }

  @Override
  public boolean hasNextResult() {
    return !executed;
  }

  @Override
  public ScmEvaluationResult nextResult(
      ScmEvaluator evaluator, ScmExecutionManager executionManager) {
    if (executed) {
      throw new IllegalStateException("Procedure has already executed");
    }
    executed = true;
    final ScmValue result = body.apply(environment);
    return ScmEvaluationResult.ofCompleted(result);
  }
}
