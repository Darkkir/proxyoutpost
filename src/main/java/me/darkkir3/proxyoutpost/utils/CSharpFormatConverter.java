package me.darkkir3.proxyoutpost.utils;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSharpFormatConverter {

    // Matches: {0:0.#%} , {1:N2} , {2:0.00}
    private static final Pattern CSHARP_FORMAT =
            Pattern.compile("\\{(\\d+):([^}]+)}");

    /**
     * Converts a C# composite format string to a Java MessageFormat string
     */
    public static String toJavaMessageFormat(String csharpFormat) {
        Matcher matcher = CSHARP_FORMAT.matcher(csharpFormat);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String index = matcher.group(1);
            String format = matcher.group(2);

            String javaNumberPattern = convertNumberPattern(format);

            matcher.appendReplacement(
                    result,
                    "{"+ index +",number,"+ javaNumberPattern +"}"
            );
        }

        matcher.appendTail(result);
        return result.toString();
    }

    /**
     * Converts C# numeric format patterns to Java DecimalFormat patterns
     */
    private static String convertNumberPattern(String csharpPattern) {

        // Standard numeric formats
        if (csharpPattern.equalsIgnoreCase("N2")) {
            return "#,##0.00";
        }
        if (csharpPattern.equalsIgnoreCase("N0")) {
            return "#,##0";
        }
        if (csharpPattern.equalsIgnoreCase("P0")) {
            return "0%";
        }
        if (csharpPattern.equalsIgnoreCase("P1")) {
            return "0.#%";
        }

        // Custom numeric formats are mostly identical
        return csharpPattern;
    }

    /**
     * Formats values using a C# format string
     */
    public static String format(String csharpFormat, Object... args) {
        String javaFormat = toJavaMessageFormat(csharpFormat);
        return MessageFormat.format(javaFormat, args);
    }
}