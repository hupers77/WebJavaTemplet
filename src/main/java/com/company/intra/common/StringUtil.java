package com.company.intra.common;

/** 사내용 String 유틸 - 외부 라이브러리 없이 JDK만 사용 */
public final class StringUtil {

    private StringUtil() {}

    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static String nvl(String s, String def) {
        return isEmpty(s) ? def : s;
    }

    public static String escapeHtml(String s) {
        if (s == null) return null;
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
