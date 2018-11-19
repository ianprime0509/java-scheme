package com.ianprime0509.jscheme.types;

import java.util.List;

import com.ianprime0509.jscheme.ScmStackFrame;

public interface ScmManagedProcedure extends ScmProcedure {
  ScmStackFrame getExecutionStackFrame(List<ScmValue> parameters);
}
