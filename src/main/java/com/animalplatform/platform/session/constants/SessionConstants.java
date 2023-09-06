package com.animalplatform.platform.session.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SessionConstants {

    public static final String SESSION_KEY = "loginMember";
    public static final String COOKIE_KEY = "LOGIN_COOKIE";

    public static final int SESSION_MAX_INTERVAL = 1800;
    public static final int COOKIE_MAX_AGE = 1800;
}
