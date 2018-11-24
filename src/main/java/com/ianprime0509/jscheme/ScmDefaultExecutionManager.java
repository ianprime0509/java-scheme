package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmNil;
import com.ianprime0509.jscheme.types.ScmValue;

class ScmDefaultExecutionManager implements ScmExecutionManager {
  ScmDefaultExecutionManager() {}

  @Override
  public ScmValue execute(final ScmStackFrame stackFrame, final ScmEvaluator evaluator) {
    ScmEvaluationResult result = executePartial(stackFrame, evaluator);
    while (!result.isCompleted()) {
      result = executePartial(result.getContinuing(), evaluator);
    }
    return result.getCompleted();
  }

  private ScmEvaluationResult executePartial(final ScmStackFrame stackFrame, final ScmEvaluator evaluator) {
    return stackFrame instanceof ScmParameterListStackFrame
      ? executeParameterList((ScmParameterListStackFrame) stackFrame, evaluator)
      : stackFrame instanceof ScmCallStackFrame
      ? executeCall((ScmCallStackFrame) stackFrame, evaluator)
      : ScmEvaluationResult.ofCompleted(ScmNil.get());
  }

  private ScmEvaluationResult executeParameterList(final ScmParameterListStackFrame stackFrame, final ScmEvaluator evaluator) {
    while (stackFrame.hasNextParameter()) {
      stackFrame.nextParameter(evaluator, this);
    }
    return executeCall(stackFrame.prepareCall(), evaluator);
  }

  private ScmEvaluationResult executeCall(final ScmCallStackFrame stackFrame, final ScmEvaluator evaluator) {
    while (stackFrame.hasNextResult()) {
      final ScmEvaluationResult result = stackFrame.nextResult(evaluator, this);
      if (!stackFrame.hasNextResult()) {
	// Result is in tail position.
	if (!result.isCompleted()) {
	  return ScmEvaluationResult.ofContinuing(result.getContinuing().copyWithOwnParent());
	}
	return result;
      }
      if (!result.isCompleted()) {
	execute(result.getContinuing(), evaluator);
      }
    }
    return ScmEvaluationResult.ofCompleted(ScmNil.get());
  }
}
