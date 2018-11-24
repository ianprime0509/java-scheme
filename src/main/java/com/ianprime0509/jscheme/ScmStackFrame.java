package com.ianprime0509.jscheme;

import com.ianprime0509.jscheme.types.ScmEnvironment;
import java.util.Optional;

public interface ScmStackFrame {
  default ScmStackFrame copy() {
    return copyWithParent(getParent().orElse(null));
  }

  ScmStackFrame copyWithParent(ScmStackFrame parent);

  default ScmStackFrame copyWithOwnParent() {
    return copyWithParent(getParent().flatMap(ScmStackFrame::getParent).orElse(null));
  }

  ScmEnvironment getEnvironment();

  Optional<ScmStackFrame> getParent();
}
