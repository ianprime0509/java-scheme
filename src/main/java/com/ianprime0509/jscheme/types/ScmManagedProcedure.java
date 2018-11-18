package com.ianprime0509.jscheme.types;

import java.util.Map;

import com.ianprime0509.jscheme.ScmStackFrame;

public interface ScmManagedProcedure extends ScmProcedure {
  ScmStackFrame getExecutionStackFrame(Map<ScmSymbol, ScmValue> bindings);
}
