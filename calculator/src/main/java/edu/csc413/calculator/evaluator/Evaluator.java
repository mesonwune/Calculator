package edu.csc413.calculator.evaluator;



import edu.csc413.calculator.operators.Operator;

import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {
  private Stack<Operand> operandStack;
  private Stack<Operator> operatorStack;
  private StringTokenizer tokenizer;
  private static String DELIMITERS = Operator.getAllOperators();

  public Evaluator() {
    operandStack = new Stack<>();
    operatorStack = new Stack<>();
  }

  public int eval( String expression ) {
    operandStack.clear();;
    operatorStack.clear();
    int result = 0;

    String token;

    // The 3rd argument is true to indicate that the delimiters should be used
    // as tokens, too. But, we'll need to remember to filter out spaces.
    this.tokenizer = new StringTokenizer( expression, DELIMITERS, true );

    // initialize operator stack - necessary with operator priority schema
    // the priority of any operator in the operator stack other than
    // the usual mathematical operators - "+-*/" - should be less than the priority
    // of the usual operators



    while ( this.tokenizer.hasMoreTokens() )
    {
      // filter out spaces
      if ( !( token = this.tokenizer.nextToken().trim()).equals( "" ))
      {
        // check if token is an operand
        if ( Operand.check( token ))
        {
          operandStack.push( new Operand( token ));
        }
        else
          {
          if ( ! Operator.check( token ))
          {
            System.out.println( "*****invalid token******" );
            throw new RuntimeException("*****invalid token******");
          }


          // TODO Operator is abstract - these two lines will need to be fixed:
          // The Operator class should contain an instance of a HashMap,
          // and values will be instances of the Operators.  See Operator class
          // skeleton for an example.
          Operator newOperator = Operator.getOperator(token);


            while (!operatorStack.isEmpty() && operatorStack.peek().priority() >= newOperator.priority() )
            {
              // note that when we eval the expression 1 - 2 we will
              // push the 1 then the 2 and then do the subtraction operation
              // This means that the first number to be popped is the
              // second operand, not the first operand - see the following code
              if (operatorStack.peek().equals("("))
              {
                int firstIndex = expression.indexOf("(");
                int secondIndex = expression.indexOf(")");

                String rest = expression.substring(secondIndex + 1);

                String parentheses = expression.substring(firstIndex + 1, secondIndex);
                int summ = eval(parentheses);

                expression = Integer.toString(summ) + rest;
                System.out.println(expression);
              }
              else
              {
                Operator oldOpr = operatorStack.pop();
                //System.out.println(oldOpr);
                Operand op2 = operandStack.pop();
                //System.out.println(op2);
                Operand op1 = operandStack.pop();
                //System.out.println(op1);
                operandStack.push(oldOpr.execute(op1, op2));
              }
            }

          operatorStack.push( newOperator );
            //System.out.println(operatorStack.);
        }
      }
    }

    calculate(operatorStack.peek());
    Operand total = operandStack.pop();
    result = total.getValue();
    // Control gets here when we've picked up all of the tokens; you must add
    // code to complete the evaluation - consider how the code given here
    // will evaluate the expression 1+2*3
    // When we have no more tokens to scan, the operand stack will contain 1 2
    // and the operator stack will have + * with 2 and * on the top;
    // In order to complete the evaluation we must empty the stacks (except
    // the init operator on the operator stack); that is, we should keep
    // evaluating the operator stack until it only contains the init operator;
    // Suggestion: create a method that takes an operator as argument and
    // then executes the while loop.



    return result;
  }

  public void calculate (Operator operator)
  {
    while (operatorStack.size() > 0)
    {
      Operator currentOperator = operatorStack.pop();
      Operand op2 = operandStack.pop();
      Operand op1 = operandStack.pop();
      operandStack.push(currentOperator.execute(op1, op2));
    }


  }
}
