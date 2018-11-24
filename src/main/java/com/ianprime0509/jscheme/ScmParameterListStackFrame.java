package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmProcedure;
import com.ianprime0509.jscheme.types.ScmValue;

public interface ScmParameterListStackFrame extends ScmStackFrame {
  ScmProcedure getProcedure();

  boolean hasNextParameter();

  ScmValue nextParameter(ScmEvaluator evaluator, ScmExecutionManager executionManager);

  ScmCallStackFrame prepareCall();
}
