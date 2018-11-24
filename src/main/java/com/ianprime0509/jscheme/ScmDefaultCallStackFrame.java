package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmEnvironment;
import com.ianprime0509.jscheme.types.ScmValue;
import java.util.ArrayDeque;
import java.util.Queue;

class ScmSchemeCallStackFrame extends ScmBasicStackFrame implements ScmCallStackFrame {
  private final Queue<ScmValue> body;

  ScmSchemeCallStackFrame(
      final ScmEnvironment environment, final ScmStackFrame parent, final Queue<ScmValue> body) {
    super(environment, parent);
    this.body = body;
  }

  @Override
  public ScmStackFrame copyWithParent(final ScmStackFrame parent) {
    return new ScmSchemeCallStackFrame(
        environment, parent != null ? parent.copy() : null, new ArrayDeque<>(body));
  }

  @Override
  public boolean hasNextResult() {
    return !body.isEmpty();
  }

  @Override
  public ScmEvaluationResult nextResult(
      ScmEvaluator evaluator, ScmExecutionManager executionManager) {
    return evaluator.evaluate(body.poll(), executionManager, this);
  }
}
