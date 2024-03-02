package ranger.format;

/**
 * Enum representing a format specifier.
 * Format specifiers are used to format numerical values and dates.
 */
public enum FormatSpecifier {
    /**
     * Specifier for converting a numerical value into a decimal string.
     * Example: [%d] 123.456 -> 123
     */
    DECIMAL("d"),

    /**
     * Specifier for converting a numerical value into a hexadecimal string.
     * Example: [%x] 123.456 -> 7b
     */
    HEXADECIMAL("x"),

    /**
     * Specifier for converting a numerical value into an octal string.
     * Example: [%o] 123.456 -> 173
     */
    OCTAL("o"),

    /**
     * Specifier for converting a numerical value into a binary string.
     * Example: [%b] 123.456 -> 1111011
     */
    BOOLEAN("b"),

    /**
     * Specifier for converting a numerical value into a floating point string.
     * Example: [%f] 123.456 -> 123.456
     * Example: [%.4f] 123.456 -> 123.4560
     */
    FLOATING_POINT("f"),

    /**
     * Specifier for converting a numerical value into a scientific notation string.
     * Example: [%e] 123.456 -> 1.23456e+02
     * Example: [%.4e] 123.456 -> 1.2346e+02
     */
    SCIENTIFIC("e"),

    /**
     * Specifier for converting a numerical value into the year of a date.
     * Dates are in Unix Epoch time.
     * Example: [%y] 123.456 -> 1970
     */
    YEAR("Y"),

    /**
     * Specifier for converting a numerical value into the compact year of a date.
     * Dates are in Unix Epoch time.
     * Example: [%yy] 123.456 -> 70
     */
    COMPACT_YEAR("YY"),

    /**
     * Specifier for converting a numerical value into the full year of a date.
     * Dates are in Unix Epoch time.
     * Example: [%yyy] 123.456 -> 1970
     */
    FULL_YEAR("YYYY"),

    /**
     * Specifier for converting a numerical value into the month of a date.
     * Dates are in Unix Epoch time.
     * Example: [%M] 123.456 -> 1
     */
    MONTH("M"),

    /**
     * Specifier for converting a numerical value into the padded month of a date.
     * Dates are in Unix Epoch time.
     * Example: [%MM] 123.456 -> 01
     */
    PADDED_MONTH("MM"),

    /**
     * Specifier for converting a numerical value into the compact month of a date.
     * Dates are in Unix Epoch time.
     * Example: [%MMM] 123.456 -> Jan
     */
    COMPACT_MONTH("MMM"),

    /**
     * Specifier for converting a numerical value into the full month of a date.
     * Dates are in Unix Epoch time.
     * Example: [%MMMM] 123.456 -> January
     */
    FULL_MONTH("MMMM"),

    /**
     * Specifier for converting a numerical value into the day of a date.
     * Dates are in Unix Epoch time.
     * Example: [%d] 123.456 -> 1
     */
    DAY("D"),

    /**
     * Specifier for converting a numerical value into the padded day of a date.
     * Dates are in Unix Epoch time.
     * Example: [%dd] 123.456 -> 01
     */
    PADDED_DAY("DD"),

    /**
     * Specifier for converting a numerical value into the compact day of a date.
     * Dates are in Unix Epoch time.
     * Example: [%ddd] 123.456 -> Mon
     */
    COMPACT_DAY("DDD"),

    /**
     * Specifier for converting a numerical value into the full day of a date.
     * Dates are in Unix Epoch time.
     * Example: [%dddd] 123.456 -> Monday
     */
    FULL_DAY("DDDD"),

    /**
     * Specifier for converting a numerical value into the day of the year of a
     * date.
     * Dates are in Unix Epoch time.
     * Example: [%D] 123.456 -> 1
     */
    DAY_OF_YEAR("y"),

    /**
     * Specifier for converting a numerical value into the hour of a date.
     * Dates are in Unix Epoch time.
     * Example: [%h] 123.456 -> 0
     */
    HOUR("h"),

    /**
     * Specifier for converting a numerical value into the padded hour of a date.
     * Dates are in Unix Epoch time.
     * Example: [%hh] 123.456 -> 00
     */
    PADDED_HOUR("hh"),

    /**
     * Specifier for converting a numerical value into the minute of a date.
     * Dates are in Unix Epoch time.
     * Example: [%m] 123.456 -> 2
     */
    MINUTE("m"),

    /**
     * Specifier for converting a numerical value into the padded minute of a date.
     * Dates are in Unix Epoch time.
     * Example: [%mm] 123.456 -> 02
     */
    PADDED_MINUTE("mm"),

    /**
     * Specifier for converting a numerical value into the second of a date.
     * Dates are in Unix Epoch time.
     * Example: [%s] 123.456 -> 3
     */
    SECOND("s"),

    /**
     * Specifier for converting a numerical value into the padded second of a date.
     * Dates are in Unix Epoch time.
     * Example: [%ss] 123.456 -> 03
     */
    PADDED_SECOND("ss"),

    /**
     * Specifier for converting a numerical value into the week of the month of a
     * date. Dates are in Unix Epoch time.
     * Example: [%w] 123.456 -> 1
     */
    WEEK_OF_MONTH("w"),

    /**
     * Specifier for converting a numerical value into the week of the year of a
     * date. Dates are in Unix Epoch time.
     * Example: [%W] 123.456 -> 1
     */
    WEEK_OF_YEAR("W"),

    ;

    /**
     * The maximum length of a format specifier.
     */
    public static final int MAXIMUM_LENGTH = getMaximumLength();

    /**
     * The specifier of the format specifier.
     */
    private String specifier;

    /**
     * Constructs a new format specifier.
     *
     * @param specifier The specifier of the format specifier.
     */
    private FormatSpecifier(String specifier) {
        this.specifier = specifier;
    }

    /**
     * Returns the specifier of the format specifier.
     *
     * @return The specifier of the format specifier.
     */
    public String getSpecifier() {
        return specifier;
    }

    /**
     * Returns the maximum length of a format specifier.
     * The value should already be present in the MAXIMUM_LENGTH constant.
     *
     * @return The maximum length of a format specifier.
     */
    private static int getMaximumLength() {
        int maximumLength = 0;

        for (FormatSpecifier specifier : FormatSpecifier.values())
            if (specifier.getSpecifier().length() > maximumLength)
                maximumLength = specifier.getSpecifier().length();

        return maximumLength;
    }
}