package ranger.format;

import java.text.SimpleDateFormat;

/**
 * Class providing static methods for formatting values.
 */
public class Formatter {
    /**
     * Constructs a new formatter.
     */
    public Formatter() {
    }

    /**
     * Format a value using the given format string.
     * 
     * @param format The format string.
     * @param value  The value to format.
     * @return The formatted value.
     * @throws IllegalArgumentException If the format string is invalid.
     */
    public static String format(String format, double value) {
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < format.length(); index++) {
            if (format.charAt(index) != '%') {
                builder.append(format.charAt(index));
                continue;
            }

            index++; // Skip the '%'
            if (index >= format.length())
                throw new IllegalArgumentException("Invalid format specifier.");

            index += consumeSpecifier(builder, format, index, value) - 1;
        }

        return builder.toString();
    }

    /**
     * Consumes a format specifier from the format string, and appends the
     * formatted value to the given string builder.
     * 
     * @param builder The string builder to append the formatted value to.
     * @param format  The format string.
     * @param index   The index of the specifier within the format string.
     * @param value   The value to format.
     * @return The length of the specifier.
     */
    private static int consumeSpecifier(StringBuilder builder, String format, int index, double value) {
        int specifierStart = index;

        Integer precision = null;

        if (index >= format.length()) // If we're at the end of the format string, throw an exception
            throw new IllegalArgumentException("Invalid format specifier.");

        if (format.charAt(index) == '.') { // If there is a precision specifier, consume it
            index++;

            int precisionStart = index;
            while (index < format.length() && Character.isDigit(format.charAt(index)))
                index++; // Consume the precision specifier

            if (index == precisionStart) // If there is no precision specifier, throw an exception
                throw new IllegalArgumentException("Expected precision specifier.");

            try {
                precision = Integer.parseInt(format.substring(specifierStart + 1, index));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid precision specifier.");
            }
        }

        if (index >= format.length()) // If we're at the end of the format string, throw an exception
            throw new IllegalArgumentException("Expected format specifier.");

        for (int length = FormatSpecifier.MAXIMUM_LENGTH; length > 0; length--) {
            if (index + length > format.length()) // If the specifier is too long, try a shorter one
                continue;

            String specifier = format.substring(index, index + length);

            FormatSpecifier formatSpecifier = null;
            for (FormatSpecifier specifierEnum : FormatSpecifier.values())
                if (specifierEnum.getSpecifier().equals(specifier)) {
                    formatSpecifier = specifierEnum;
                    break;
                }

            if (formatSpecifier == null) // If the specifier is invalid, try a shorter one
                continue;

            builder.append(convertSpecifier(formatSpecifier, value, precision));

            return index - specifierStart + length;
        }

        throw new IllegalArgumentException("Expected format specifier.");
    }

    /**
     * Converts a value to a string using the given format specifier.
     * 
     * @param specifier The format specifier.
     * @param value     The value to convert.
     * @param precision The precision of the conversion.
     * @return The converted value.
     */
    private static String convertSpecifier(FormatSpecifier specifier, double value, Integer precision) {
        if (precision != null)
            switch (specifier) {
                case FLOATING_POINT:
                case SCIENTIFIC:
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Precision specifier is not supported for format specifier '" + specifier + "'.");
            }

        switch (specifier) {
            case DECIMAL:
                return String.format("%d", (int) value);
            case HEXADECIMAL:
                return String.format("%x", (int) value);
            case OCTAL:
                return String.format("%o", (int) value);
            case BOOLEAN:
                return value != 0 ? "True" : "False";

            case FLOATING_POINT:
                if (precision == null)
                    return String.format("%f", value);
                else
                    return String.format("%." + precision + "f", value);
            case SCIENTIFIC:
                if (precision == null)
                    return String.format("%e", value);
                else
                    return String.format("%." + precision + "e", value);

            case YEAR:
                return new SimpleDateFormat("y").format(value * 1000.0);
            case COMPACT_YEAR:
                return new SimpleDateFormat("yy").format(value * 1000.0);
            case FULL_YEAR:
                return new SimpleDateFormat("yyyy").format(value * 1000.0);

            case MONTH:
                return new SimpleDateFormat("M").format(value * 1000.0);
            case PADDED_MONTH:
                return new SimpleDateFormat("MM").format(value * 1000.0);
            case COMPACT_MONTH:
                return new SimpleDateFormat("MMM").format(value * 1000.0);
            case FULL_MONTH:
                return new SimpleDateFormat("MMMM").format(value * 1000.0);

            case DAY:
                return new SimpleDateFormat("d").format(value * 1000.0);
            case PADDED_DAY:
                return new SimpleDateFormat("dd").format(value * 1000.0);
            case COMPACT_DAY:
                return new SimpleDateFormat("EEE").format(value * 1000.0);
            case FULL_DAY:
                return new SimpleDateFormat("EEEE").format(value * 1000.0);
            case DAY_OF_YEAR:
                return new SimpleDateFormat("D").format(value * 1000.0);

            case HOUR:
                return new SimpleDateFormat("H").format(value * 1000.0);
            case PADDED_HOUR:
                return new SimpleDateFormat("HH").format(value * 1000.0);

            case MINUTE:
                return new SimpleDateFormat("m").format(value * 1000.0);
            case PADDED_MINUTE:
                return new SimpleDateFormat("mm").format(value * 1000.0);

            case SECOND:
                return new SimpleDateFormat("s").format(value * 1000.0);
            case PADDED_SECOND:
                return new SimpleDateFormat("ss").format(value * 1000.0);

            case WEEK_OF_MONTH:
                return new SimpleDateFormat("W").format(value * 1000.0);
            case WEEK_OF_YEAR:
                return new SimpleDateFormat("w").format(value * 1000.0);

            default:
                throw new IllegalArgumentException("Unhandled format specifier '" + specifier + "'.");
        }
    }
}
