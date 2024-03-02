package ranger.syntax.block;

import java.util.Queue;

import ranger.Utils;
import ranger.syntax.Delimiter;
import ranger.syntax.SyntaxException;
import ranger.syntax.node.*;
import ranger.syntax.token.*;

/**
 * Class reprensenting a expression block of tokens.
 * Eg: "1 + 2" is a expression block, as well as "(1 + 2 * 3)"
 */
public class ExpressionBlock {
    /**
     * Constructs a new expression block.
     */
    public ExpressionBlock() {
    }

    /**
     * Returns the root node of the expression block, as an ExpressionNode.
     * This converts the tokens into a raw tree of nodes, where nodes are not yet
     * valid.
     * 
     * @param tokens The tokens to parse.
     * @return The root node of the expression block.
     */
    public static SyntaxNode getRoot(Queue<Token> tokens) {
        ExpressionNode root = new ExpressionNode();

        while (!tokens.isEmpty()) {
            Token token = tokens.peek();

            if (token instanceof OperatorToken) {
                root.addChild(new OperatorNode((OperatorToken) tokens.poll()));
                continue;
            }

            if (token instanceof NumberToken) {
                root.addChild(new NumberNode((NumberToken) tokens.poll()));
                continue;
            }

            if (token instanceof ReferenceToken) {
                root.addChild(new ReferenceNode((ReferenceToken) tokens.poll()));
                continue;
            }

            if (token instanceof FunctionToken) {
                tokens.remove(); // Consume the function token

                if (!Utils.isNextDelimiter(tokens, Delimiter.OPEN))
                    throw new SyntaxException("Missing opening parenthesis after function call.");

                tokens.remove(); // Consume the open token

                root.addChild(FunctionBlock.getRoot(((FunctionToken) token).getName(), tokens));

                if (!Utils.isNextDelimiter(tokens, Delimiter.CLOSE))
                    throw new SyntaxException("Missing closing parenthesis after function arguments.");

                tokens.remove(); // Consume the close token
                continue;
            }

            if (token instanceof DelimiterToken && ((DelimiterToken) token).getDelimiter() == Delimiter.OPEN) {
                tokens.remove(); // Consume the open token

                root.addChild(ExpressionBlock.getRoot(tokens));

                if (!Utils.isNextDelimiter(tokens, Delimiter.CLOSE))
                    throw new SyntaxException("Missing closing parenthesis after expression.");

                tokens.remove(); // Consume the close token
                continue;
            }

            if (token instanceof DelimiterToken
                    && ((DelimiterToken) token).getDelimiter() == Delimiter.CLOSE
                    || ((DelimiterToken) token).getDelimiter() == Delimiter.SEPARATOR)
                break;

            throw new SyntaxException("Unexpected token '" + token + "' in expression.");
        }

        if (root.getChildrenCount() == 0)
            throw new SyntaxException("Premature end of expression.");

        return root;
    }
}
