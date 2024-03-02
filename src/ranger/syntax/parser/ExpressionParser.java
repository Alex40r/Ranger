package ranger.syntax.parser;

import ranger.syntax.node.ExpressionNode;

/**
 * Interface for expression parsers.
 */
public interface ExpressionParser {
    /**
     * Parses the expression node.
     * This process will collapse the expression tree.
     * 
     * @param node The expression node.
     */
    public void parse(ExpressionNode node);
}
