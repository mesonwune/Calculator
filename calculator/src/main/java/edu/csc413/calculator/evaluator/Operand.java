package edu.csc413.calculator.evaluator;

import edu.csc413.calculator.operators.Operator;

/**
 * Operand class used to represent an operand
 * in a valid mathematical expression.
 */
public class Operand {
  /**
  * construct operand from string token.
  */
  private int operand;

  public Operand( String token )
  {
    operand = Integer.parseInt(token);
  }
  /**
   * construct operand from integer
   */
  public Operand( int value )
  {
    operand = value;
  }
  /**
   * return value of operand
   */
  public int getValue() {
      return operand;
  }
  /**
   * Check to see if given token is a valid
   * operand.
   */
  public static boolean check( String token )
  {
    if (!Operator.check(token))
    {
      return true;
    }
    return false;
  }
}
