package com.ianprime0509.jscheme;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

import com.ianprime0509.jscheme.types.ScmEnvironment;
import com.ianprime0509.jscheme.types.ScmManagedProcedure;
import com.ianprime0509.jscheme.types.ScmValue;

public class ScmStackFrame {
  private final ScmStackFrame parent;

  private final ScmManagedProcedure executingProcedure;

  private final ScmEnvironment executionEnvironment;

  private final Queue<ScmValue> expressions;

  private ScmStackFrame(
      final ScmStackFrame parent,
      final ScmManagedProcedure executingProcedure,
      final ScmEnvironment executionEnvironment,
      final Queue<ScmValue> expressions) {
    if (executingProcedure == null) {
      throw new IllegalArgumentException("executingProcedure must not be null");
    }
    if (executionEnvironment == null) {
      throw new IllegalArgumentException("executionEnvironment must not be null");
    }
    if (expressions == null) {
      throw new IllegalArgumentException("expressions must not be null");
    }
    this.parent = parent;
    this.executingProcedure = executingProcedure;
    this.executionEnvironment = executionEnvironment;
    this.expressions = expressions;
  }

  public static class Builder {
    private ScmStackFrame parent;

    private ScmManagedProcedure executingProcedure;

    private ScmEnvironment executionEnvironment;

    private Queue<ScmValue> expressions;

    private Builder() {}

    public Builder inherit(final ScmStackFrame inherited) {
      parent(inherited.getParent());
      executingProcedure(inherited.getExecutingProcedure());
      executionEnvironment(inherited.getExecutionEnvironment());
      expressions(inherited.getExpressions());
      return this;
    }

    public Builder parent(final ScmStackFrame parent) {
      this.parent = parent;
      return this;
    }

    public Builder executingProcedure(final ScmManagedProcedure executingProcedure) {
      this.executingProcedure = executingProcedure;
      return this;
    }

    public Builder executionEnvironment(final ScmEnvironment executionEnvironment) {
      this.executionEnvironment = executionEnvironment;
      return this;
    }

    public Builder expressions(final Queue<ScmValue> expressions) {
      this.expressions = new ArrayDeque<>(expressions);
      return this;
    }

    public ScmStackFrame build() {
      return new ScmStackFrame(parent, executingProcedure, executionEnvironment, expressions);
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  public ScmStackFrame getParent() {
    return parent;
  }

  public ScmManagedProcedure getExecutingProcedure() {
    return executingProcedure;
  }

  public ScmEnvironment getExecutionEnvironment() {
    return executionEnvironment;
  }

  public Queue<ScmValue> getExpressions() {
    return new ArrayDeque<>(expressions);
  }

  public boolean hasExpression() {
    return !expressions.isEmpty();
  }

  public ScmValue pollExpression() {
    return expressions.poll();
  }
}
