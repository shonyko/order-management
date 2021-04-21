package org.alexk.utils;

/**
 * Clasa care faciliteaza formatarea stringurilor
 */
public class StringFormatter {
    /**
     * Blocheaza crearea instantelor
     */
    private StringFormatter() {

    }

    /**
     * @param s string-ul de tip camel case care urmeaza sa fie despartit
     * @return un string despartit
     */
    public static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    /**
     * @param s string-ul a carei prim caracter urmeaza sa fie scris cu majuscula
     * @return string-ul modificat
     */
    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

}
