package me.darkkir3.proxyoutpost.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DBUtils {
    private static final String FIELD_DELIMITER = ",";
    private DBUtils(){}

    /**
     * Convert the delimiter separated string into a list of boolean values
     * @param value the value to convert
     * @return a list containing the booleans
     */
    public static List<Boolean> convertStringToBooleanList(String value) {
        List<Boolean> result = new ArrayList<>();
        Arrays.stream(value.split(FIELD_DELIMITER)).forEach(t -> {
            try
            {
                Boolean b = Boolean.parseBoolean(t);
                result.add(b);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        });

        return result;
    }

    /**
     * Convert the delimiter separated string into a list of int values
     * @param value the value to convert
     * @return a list containing the integers
     */
    public static List<Integer> convertStringToIntegerList(String value) {
        List<Integer> result = new ArrayList<>();
        Arrays.stream(value.split(FIELD_DELIMITER)).forEach(t -> {
            try
            {
                Integer i = Integer.parseInt(t);
                result.add(i);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        });

        return result;
    }

    /**
     * Convert a list of objects into a delimiter separated string
     * @param values the values to convert
     * @return a delimiter separated string with the passed values
     */
    public static String convertListToField(List<?> values)
    {
        StringBuilder result = new StringBuilder();
        String delimiter = "";
        for(Object value : values)
        {
            result.append(delimiter);
            delimiter = FIELD_DELIMITER;
            result.append(String.valueOf(value));
        }

        return result.toString();
    }
}
