package com.amitgroup.models;

public class ShareConstants {

    public static final String DELETED_KEYWORD = "_DELETED";

    public static class ApplicationInformation{
        public static final String VERSION = "202302211430";
        public static final String NAME = "Sample Project";
    }

    public static class ApplicationSetting {
        public static final Boolean IS_ENABLE_RESPONSE_TRACE_ID = true;
        public static final Boolean IS_ENABLE_HASHIDS = false;
    }

    public static class RedisHash {
        public static final String LOGIN_SESSION = "login_session";
        public static final String SIGN_UP_SESSION = "signup_session";
        public static final String VERIFICATION_SESSION = "verification_session";
    }

    public static class Header {
        public static final String TRACE_ID = "X-B3-TraceId";
        public static final String DEVICE_Id = "device-id";
        public static final String TOKEN_HEADER = "Authorization";
        public static final String AGENT_HEADER = "platform";
        public static final String TOKEN_PREFIX = "bearer ";
    }

    public static class Template {
        public static final String ERROR_EMAIL = "template/mail/error.vm";

    }
}
