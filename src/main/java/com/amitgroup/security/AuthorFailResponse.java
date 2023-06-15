package com.amitgroup.security;

import io.vertx.core.json.JsonObject;

public class AuthorFailResponse {
    private static String jsonReturn;

    static {
        JsonObject response = new JsonObject();
        response.put("error_description" ,"The access token is invalid or has expired");
        response.put("error","invalid_token");
        jsonReturn = response.toString();
    }

    public static String toJson() {
        return jsonReturn;
    }
}
