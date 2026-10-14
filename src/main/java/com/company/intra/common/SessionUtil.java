package com.company.intra.common;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** 세션 사용자 헬퍼 - 순수 JDK + Spring만 사용 */
public final class SessionUtil {

    public static final String SESSION_KEY = "LOGIN_USER";

    private SessionUtil() {}

    public static void setLoginUser(SessionUser user) {
        session().setAttribute(SESSION_KEY, user);
    }

    public static SessionUser getLoginUser() {
        Object o = session().getAttribute(SESSION_KEY);
        return (o instanceof SessionUser) ? (SessionUser) o : null;
    }

    public static void invalidate() {
        session().invalidate();
    }

    private static HttpSession session() {
        ServletRequestAttributes attr =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }
}
