package dev.qhc99.centralibj.utils.literalLexer;



public class FloatingPointLiterals {
  static boolean isFloatingPointLiteral( String source) {
    if (source.length() <= 1) {
      return false;
    }
    else if (source.length() == 2) {
      return new FloatDecimalLiteral(source).isTheLiteral();
    }
    if (source.startsWith("+") || source.startsWith("-")) {
      if (source.startsWith("0x", 1) || source.startsWith("0X", 1)) {
        return new FloatHexadecimalLiteral(source).isTheLiteral();
      }
      else {
        return new FloatDecimalLiteral(source).isTheLiteral();
      }
    }
    else {
      if (source.startsWith("0x") || source.startsWith("0X")) {
        return new FloatHexadecimalLiteral(source).isTheLiteral();
      }
      else {
        return new FloatDecimalLiteral(source).isTheLiteral();
      }
    }
  }

  /**
   * @param source string to check
   * @return whether a double literal
   * @see <a href="https://docs.oracle.com/javase/specs/jls/se15/html/jls-3.html#jls-3.10.2">java 15 specification</a>
   */
  public static boolean isDoubleParsable( String source) {
    return isFloatingPointLiteral(source);
  }

  /**
   * @param source string to check
   * @return whether a float literal
   * @see <a href="https://docs.oracle.com/javase/specs/jls/se15/html/jls-3.html#jls-3.10.2">java 15 specification</a>
   */
  public static boolean isFloatParsable( String source) {
    return (source.endsWith("f") || source.endsWith("F")) && isFloatingPointLiteral(source);
  }
}
