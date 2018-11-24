package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmEnvironment;
import java.util.Optional;

public class ScmBasicStackFrame implements ScmStackFrame {
  protected final ScmEnvironment environment;

  protected final ScmStackFrame parent;

  protected ScmBasicStackFrame(final ScmEnvironment environment, final ScmStackFrame parent) {
    this.environment = environment;
    this.parent = parent;
  }

  @Override
  public ScmStackFrame copyWithParent(final ScmStackFrame parent) {
    return new ScmBasicStackFrame(environment, parent != null ? parent.copy() : null);
  }

  @Override
  public ScmEnvironment getEnvironment() {
    return environment;
  }

  @Override
  public Optional<ScmStackFrame> getParent() {
    return Optional.ofNullable(parent);
  }
}
