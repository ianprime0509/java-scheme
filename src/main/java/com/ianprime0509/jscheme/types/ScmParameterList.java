package com.ianprime0509.jscheme.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ScmParameterList {
  private final List<ScmSymbol> required;

  private final ScmSymbol rest;

  private ScmParameterList(final List<ScmSymbol> required, final ScmSymbol rest) {
    this.required = Collections.unmodifiableList(required);
    this.rest = rest;
  }

  public static class Builder {
    private List<ScmSymbol> required = new ArrayList<>();

    private ScmSymbol rest;

    public Builder required(final ScmSymbol... names) {
      required.addAll(Arrays.asList(names));
      return this;
    }

    public Builder rest(final ScmSymbol name) {
      if (rest != null) {
        throw new IllegalStateException("rest parameter already defined");
      }
      rest = name;
      return this;
    }

    public ScmParameterList build() {
      return new ScmParameterList(required, rest);
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  public List<ScmSymbol> getRequired() {
    return required;
  }

  public Optional<ScmSymbol> getRest() {
    return Optional.ofNullable(rest);
  }

  public Map<ScmSymbol, ScmValue> getExecutionBindings(List<ScmValue> parameters) {
    if (parameters.size() < required.size()) {
      throw new IllegalArgumentException("not enough parameters");
    }
    if (parameters.size() > required.size() && rest == null) {
      throw new IllegalArgumentException("too many parameters");
    }

    final Map<ScmSymbol, ScmValue> bindings = new HashMap<>(required.size());
    for (int i = 0; i < required.size(); i++) {
      bindings.put(required.get(i), parameters.get(i));
    }
    if (rest != null) {
      bindings.put(
          rest, ScmPair.ofList(parameters.subList(required.size(), parameters.size())));
    }

    return bindings;
  }
}
