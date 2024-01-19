package ooproject.utils;

import java.util.List;

public class StringFormatter {
    public static String listToStringWithParentheses(List<Integer> list, int index) {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < list.size(); i++) {
            if (i == index) {
                sb.append("(").append(list.get(i)).append(")");
            } else {
                sb.append(list.get(i));
            }

            if (i < list.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");

        return sb.toString();
    }
}
