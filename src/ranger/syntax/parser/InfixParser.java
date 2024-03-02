package ranger.syntax.parser;

import ranger.operator.OperatorPrecedence;
import ranger.operator.OperatorUsage;
import ranger.syntax.SyntaxException;
import ranger.syntax.node.ExpressionNode;
import ranger.syntax.node.OperatorNode;
import ranger.syntax.node.SyntaxNode;

/**
 * Class representing an infix parser.
 */
public class InfixParser implements ExpressionParser {

    /**
     * Constructs a new infix parser.
     */
    public InfixParser() {
    }

    /**
     * Parses the expression node.
     * This process will collapse the expression tree.
     * 
     * @param node The expression node.
     */
    @Override
    public void parse(ExpressionNode node) {
        collapseRightUnaryOperators(node);
        collapseLeftUnaryOperators(node);

        collapseBinaryOperators(node, OperatorPrecedence.EXPONENTIAL);
        collapseBinaryOperators(node, OperatorPrecedence.MULTIPLICATIVE);
        collapseBinaryOperators(node, OperatorPrecedence.ADDITIVE);
        collapseBinaryOperators(node, OperatorPrecedence.RELATIONAL);

        if (node.getChildrenCount() == 0)
            throw new SyntaxException("Error during expression parsing. This is a bug in the parser.");

        if (node.getChildrenCount() > 1)
            throw new SyntaxException("Invalid expression.");
    }

    /**
     * Collapses all left unary operators.
     * 
     * @param node The expression node.
     */
    private void collapseLeftUnaryOperators(ExpressionNode node) {
        for (int i = node.getChildrenCount() - 1; i >= 0; i--) {
            SyntaxNode child = node.getChild(i);

            if (!(child instanceof OperatorNode))
                continue;
            OperatorNode operator = (OperatorNode) child;

            if (operator.getChildrenCount() != 0)
                continue;

            boolean left = i <= 0 || (node.getChild(i - 1) instanceof OperatorNode
                    && ((OperatorNode) node.getChild(i - 1)).getChildrenCount() == 0);
            if (!left)
                continue;

            if (!operator.getOperator().supportsUsage(OperatorUsage.LEFT_UNARY))
                continue;

            if (i + 1 >= node.getChildrenCount())
                throw new SyntaxException("Operator '" + operator.getOperator() + "' is missing an operand.");

            SyntaxNode operand = node.getChild(i + 1);

            operator.addChild(operand);
            operator.addChild(null);

            node.removeChild(i + 1);
        }
    }

    /**
     * Collapses all right unary operators.
     * 
     * @param node The expression node.
     */
    private void collapseRightUnaryOperators(ExpressionNode node) {
        for (int i = 0; i < node.getChildrenCount(); i++) {
            SyntaxNode child = node.getChild(i);

            if (!(child instanceof OperatorNode))
                continue;
            OperatorNode operator = (OperatorNode) child;

            if (operator.getChildrenCount() != 0)
                continue;

            boolean right = i >= node.getChildrenCount() - 1 || (node.getChild(i + 1) instanceof OperatorNode
                    && ((OperatorNode) node.getChild(i + 1)).getChildrenCount() == 0);
            if (!right)
                continue;

            if (!operator.getOperator().supportsUsage(OperatorUsage.RIGHT_UNARY))
                continue;

            if (i == 0)
                throw new SyntaxException("Operator '" + operator.getOperator() + "' is missing an operand.");

            SyntaxNode operand = node.getChild(i - 1);

            operator.addChild(null);
            operator.addChild(operand);

            node.removeChild(i - 1);
            i--;
        }
    }

    /**
     * Collapses all binary operators with the specified precedence.
     * 
     * @param node       The expression node.
     * @param precedence The precedence.
     */
    private void collapseBinaryOperators(ExpressionNode node, OperatorPrecedence precedence) {
        for (int i = 0; i < node.getChildrenCount(); i++) {
            SyntaxNode child = node.getChild(i);

            if (!(child instanceof OperatorNode))
                continue;
            OperatorNode operator = (OperatorNode) child;

            if (operator.getChildrenCount() != 0 || operator.getOperator().getPrecedence() != precedence)
                continue;

            if (!operator.getOperator().supportsUsage(OperatorUsage.BINARY))
                continue;

            if (i == 0 || i == node.getChildrenCount() - 1)
                throw new SyntaxException("Operator '" + operator.getOperator() + "' is missing an operand.");

            SyntaxNode leftOperand = node.getChild(i - 1);
            SyntaxNode rightOperand = node.getChild(i + 1);

            operator.addChild(leftOperand);
            operator.addChild(rightOperand);

            node.removeChild(i - 1);
            node.removeChild(i);

            i--;
        }
    }
}
