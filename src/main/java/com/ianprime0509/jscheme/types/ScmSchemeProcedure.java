package com.ianprime0509.jscheme.types;

import com.ianprime0509.jscheme.ScmEvaluator;
import com.ianprime0509.jscheme.ScmExecutionManager;
import com.ianprime0509.jscheme.ScmManagedEvaluator;
import com.ianprime0509.jscheme.ScmStackFrame;
import java.util.List;
import java.util.Queue;

class ScmSchemeProcedure extends ScmAbstractProcedure implements ScmManagedProcedure {
  private final Queue<ScmValue> body;

  ScmSchemeProcedure(
      final ScmEnvironment lexicalEnvironment,
      final ScmParameterList parameterList,
      final Queue<ScmValue> body) {
    super(lexicalEnvironment, parameterList);
    if (body == null) {
      throw new IllegalArgumentException("body must not be null");
    }
    this.body = body;
  }

  @Override
  public ScmValue apply(final ScmEnvironment executionEnvironment) {
    final ScmManagedEvaluator evaluator = ScmEvaluator.newDefaultEvaluator();
    final ScmExecutionManager executionManager = ScmExecutionManager.newDefaultExecutionManager(evaluator);
    return executionManager.execute(getExecutionStackFrame(executionEnvironment));
  }

  @Override
  public ScmStackFrame getExecutionStackFrame(final List<ScmValue> parameters) {
    return getExecutionStackFrame(getExecutionEnvironment(parameters));
  }

  private ScmStackFrame getExecutionStackFrame(final ScmEnvironment executionEnvironment) {
    return ScmStackFrame.builder() //
        .executingProcedure(this) //
        .executionEnvironment(executionEnvironment) //
        .expressions(body) //
        .build();
  }
}
