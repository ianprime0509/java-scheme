package com.ianprime0509.jscheme.types;

public class ScmString implements ScmValue {
  private final String value;

  private ScmString(final String value) {
    if (value == null) {
      throw new IllegalArgumentException("value must not be null");
    }
    this.value = value;
  }

  public static ScmString of(final String value) {
    return new ScmString(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (other == null || getClass() != other.getClass()) {
      return false;
    } else {
      return value.equals(((ScmString) other).value);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
