package dev.qhc99.centralibj.utils.literalLexer;



abstract class LiteralLexChecker {
  protected final  String s;
  protected int idx;

  protected LiteralLexChecker( String s) {
    this.s = s;
  }

  protected boolean isFloatConstants() {
    switch (s) {
      case "+NaN", "-NaN", "NaN", "+Infinity", "-Infinity", "Infinity" -> {
        return true;
      }
      default -> {
        return false;
      }
    }
  }

  protected boolean isFloatTypeSuffix() {
    if (isEnd()) {
      return false;
    }
    switch (s.charAt(idx)) {
      case 'f', 'F', 'd', 'D' -> {
        return idx + 1 == s.length();
      }
      default -> {
        return false;
      }
    }
  }

  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  protected boolean isSignedInteger() {
    if (isEnd()) {
      return false;
    }
    switch (s.charAt(idx)) {
      case '+':
      case '-':
        if (end()) {
          return false;
        }
      default:
        return isDigits();
    }
  }

  protected boolean isDigits() {
    if (isEnd()) {
      return false;
    }
    if (!Character.isDigit(s.charAt(idx))) {
      return false;
    }
    if (end()) {
      return true;
    }
    while (idx < s.length()) {
      char c = s.charAt(idx);
      if (c == '_' || Character.isDigit(c)) {
        idx++;
      }
      else {
        break;
      }
    }
    return Character.isDigit(s.charAt(idx - 1));
  }

  protected boolean end() {
    idx++;
    return idx >= s.length();
  }

  protected boolean isEnd() {
    return idx >= s.length();
  }
}
