package ranger.syntax.block;

import java.util.Queue;

import ranger.Utils;
import ranger.syntax.Delimiter;
import ranger.syntax.node.FunctionNode;
import ranger.syntax.node.SyntaxNode;
import ranger.syntax.token.Token;

/**
 * Class reprensenting a function block of tokens.
 * Eg: "func(1, 2)" is a function block.
 */
public class FunctionBlock {
    /**
     * Constructs a new function block.
     */
    public FunctionBlock() {
    }

    /**
     * Returns the root node of the function block, as a FunctionNode.
     * This converts the tokens into a raw tree of nodes, where nodes are not yet
     * valid.
     * 
     * @param name   The name of the function.
     * @param tokens The tokens to parse.
     * @return The root node of the function block.
     */
    public static SyntaxNode getRoot(String name, Queue<Token> tokens) {
        FunctionNode root = new FunctionNode(name);

        while (!tokens.isEmpty()) {
            if (Utils.isNextDelimiter(tokens, Delimiter.CLOSE))
                break;

            if (Utils.isNextDelimiter(tokens, Delimiter.SEPARATOR))
                throw new RuntimeException("Empty function argument.");

            root.addChild(ExpressionBlock.getRoot(tokens));

            if (Utils.isNextDelimiter(tokens, Delimiter.SEPARATOR)) {
                tokens.remove(); // Consume the separator token

                if (Utils.isNextDelimiter(tokens, Delimiter.CLOSE))
                    throw new RuntimeException("Empty function argument.");

                continue;
            }

        }

        return root;
    }
}
