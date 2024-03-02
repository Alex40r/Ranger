package ranger.syntax.node;

import ranger.Utils;
import ranger.operator.Operator;
import ranger.operator.OperatorUsage;
import ranger.syntax.EvaluationContext;
import ranger.syntax.token.OperatorToken;

/**
 * Class representing an operator node.
 */
public class OperatorNode extends SyntaxNode {
    /**
     * The operator.
     */
    private final Operator operator;

    /**
     * Constructs a new operator node.
     * 
     * @param operator The operator.
     */
    public OperatorNode(Operator operator) {
        super(false);

        this.operator = operator;
    }

    /**
     * Constructs a new operator node.
     * 
     * @param token The token representing the operator.
     */
    public OperatorNode(OperatorToken token) {
        this(token.getOperator());
    }

    /**
     * Returns the operator.
     * 
     * @return The operator.
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * Returns the string representation of this operator node.
     * 
     * @return The string representation of this operator node.
     */
    @Override
    public String toString() {
        return operator.toString();
    }

    /**
     * Evaluates the operator node.
     * 
     * @param context The evaluation context.
     * @return The result of the evaluation.
     */
    @Override
    public double evaluate(EvaluationContext context) {
        if (getChildrenCount() != 2)
            throw new IllegalStateException(
                    "Missing operands for operator '" + operator.toString() + "'.");

        SyntaxNode left = getChild(0);
        SyntaxNode right = getChild(1);

        if (left != null && right != null)
            return evaluateBinary(left, right, context);
        else if (left != null && right == null)
            return evaluateLeftUnary(left, context);
        else if (left == null && right != null)
            return evaluateRightUnary(right, context);
        else
            throw new IllegalStateException(
                    "Operator '" + operator.toString() + "' was incorrectly initialized.");

    }

    /**
     * Evaluates the operator node as a binary operator.
     * 
     * @param left The left operand.
     * @param right The right operand.
     * @param context The evaluation context.
     * @return The result of the evaluation.
     */
    private double evaluateBinary(SyntaxNode left, SyntaxNode right, EvaluationContext context) {
        if (!operator.supportsUsage(OperatorUsage.BINARY))
            throw new IllegalStateException(
                    "Operator '" + operator.toString() + "' does not support binary usage.");

        double leftValue = left.evaluate(context);
        double rightValue = right.evaluate(context);

        switch (operator) {
            case POWER_PRIMARY:
            case POWER_SECONDARY:
                return Math.pow(leftValue, rightValue);

            case MULTIPLICATION:
                return leftValue * rightValue;
            case DIVISION:
                if (rightValue == 0)
                    throw new ArithmeticException("Division operation with zero divisor is undefined.");
                return leftValue / rightValue;
            case MODULO:
                if (rightValue == 0)
                    throw new ArithmeticException("Modulo operation with zero divisor is undefined.");
                return leftValue % rightValue;

            case ADDITION:
                return leftValue + rightValue;
            case SUBTRACTION:
                return leftValue - rightValue;

            case LESS_THAN:
                return leftValue < rightValue ? 1 : 0;
            case LESS_THAN_OR_EQUAL_TO:
                return leftValue <= rightValue ? 1 : 0;
            case GREATER_THAN:
                return leftValue > rightValue ? 1 : 0;
            case GREATER_THAN_OR_EQUAL_TO:
                return leftValue >= rightValue ? 1 : 0;
            case EQUAL_TO:
                return leftValue == rightValue ? 1 : 0;
            case NOT_EQUAL_TO:
                return leftValue != rightValue ? 1 : 0;
            case LOGICAL_AND:
                return (leftValue != 0 && rightValue != 0) ? 1 : 0;
            case LOGICAL_OR:
                return (leftValue != 0 || rightValue != 0) ? 1 : 0;

            default:
                throw new IllegalStateException(
                        "Operator '" + operator.toString() + "' is not supported.");
        }
    }

    /**
     * Evaluates the operator node as a left unary operator.
     * 
     * @param operand The operand.
     * @param context The evaluation context.
     * @return The result of the evaluation.
     */
    private double evaluateLeftUnary(SyntaxNode operand, EvaluationContext context) {
        if (!operator.supportsUsage(OperatorUsage.LEFT_UNARY))
            throw new IllegalStateException(
                    "Operator '" + operator.toString() + "' does not support left unary usage.");

        double operandValue = operand.evaluate(context);

        switch (operator) {
            case ADDITION:
                return operandValue;
            case SUBTRACTION:
                return -operandValue;

            default:
                throw new IllegalStateException(
                        "Operator '" + operator.toString() + "' is not supported.");
        }
    }

    /**
     * Evaluates the operator node as a right unary operator.
     * 
     * @param operand The operand.
     * @param context The evaluation context.
     * @return The result of the evaluation.
     */
    private double evaluateRightUnary(SyntaxNode operand, EvaluationContext context) {
        if (!operator.supportsUsage(OperatorUsage.RIGHT_UNARY))
            throw new IllegalStateException(
                    "Operator '" + operator.toString() + "' does not support right unary usage.");

        double operandValue = operand.evaluate(context);

        switch (operator) {
            case FACTORIAL:
                if (operandValue < 0)
                    throw new ArithmeticException("Factorial of a negative number.");
                return Utils.factorial(operandValue);

            default:
                throw new IllegalStateException(
                        "Operator '" + operator.toString() + "' is not supported.");
        }
    }
}
