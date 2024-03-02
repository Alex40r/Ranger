package ranger.syntax.parser;

import ranger.operator.OperatorUsage;
import ranger.syntax.SyntaxException;
import ranger.syntax.node.ExpressionNode;
import ranger.syntax.node.OperatorNode;
import ranger.syntax.node.SyntaxNode;

/**
 * Class representing a postfix parser.
 */
public class PostfixParser implements ExpressionParser {

    /**
     * Constructs a new postfix parser.
     */
    public PostfixParser() {
    }

    /**
     * Parses the expression node.
     * This process will collapse the expression tree.
     * 
     * @param node The expression node.
     */
    @Override
    public void parse(ExpressionNode node) {
        for (int i = 0; i < node.getChildrenCount(); i++) {
            SyntaxNode child = node.getChild(i);

            if (!(child instanceof OperatorNode))
                continue;
            OperatorNode operator = (OperatorNode) child;

            if (operator.getChildrenCount() != 0)
                continue;

            if (operator.getOperator().supportsUsage(OperatorUsage.BINARY)) {
                if (i - 1 < 0)
                    throw new SyntaxException("Operator '" + operator.getOperator()
                            + "' is missing both operands.");

                SyntaxNode rightOperand = node.getChild(i - 1);

                if (i - 2 < 0)
                    throw new SyntaxException("Operator '" + operator.getOperator()
                            + "' is missing its left operand.");

                SyntaxNode leftOperand = node.getChild(i - 2);

                operator.addChild(leftOperand);
                operator.addChild(rightOperand);

                node.removeChild(i - 1);
                node.removeChild(i - 2);

                i -= 2;
            } else if (operator.getOperator().supportsUsage(OperatorUsage.RIGHT_UNARY)
                    || operator.getOperator().supportsUsage(OperatorUsage.LEFT_UNARY)) {
                if (i - 1 < 0)
                    throw new SyntaxException("Operator '" + operator.getOperator()
                            + "' is missing its operand.");

                SyntaxNode operand = node.getChild(i - 1);

                if (operator.getOperator().supportsUsage(OperatorUsage.RIGHT_UNARY)) {
                    operator.addChild(null);
                    operator.addChild(operand);
                } else if (operator.getOperator().supportsUsage(OperatorUsage.LEFT_UNARY)) {
                    operator.addChild(operand);
                    operator.addChild(null);
                }

                node.removeChild(i - 1);

                i--;
            } else
                throw new IllegalArgumentException("Operator '" + operator.getOperator()
                        + "' is not supported by this parser.");
        }

        if (node.getChildrenCount() > 1)
            throw new SyntaxException("Invalid expression.");
    }
}
