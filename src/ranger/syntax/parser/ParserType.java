package ranger.syntax.parser;

/**
 * Enum representing the type of parser.
 */
public enum ParserType {
    /**
     * Infix parser. Eg. 1 + 2
     */
    INFIX,

    /**
     * Postfix parser. Eg. 1 2 +
     */
    POSTFIX,

    /**
     * Prefix parser. Eg. + 1 2
     */
    PREFIX;

    /**
     * Returns the parser associated with this parser type.
     * 
     * @return The parser associated with this parser type.
     */
    public ExpressionParser getParser() {
        switch (this) {
            case INFIX:
                return new InfixParser();
            case POSTFIX:
                return new PostfixParser();
            case PREFIX:
                return new PrefixParser();
            default:
                return null;
        }
    }
}
