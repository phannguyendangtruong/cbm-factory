package com.amitgroup.utils;

public class HTMLUtils {
    public static StringBuilder normalizeText(String text){
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(
                text.replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("&", "&amp;")
                    .replaceAll("\\\"", "&quot")
                    .replaceAll("\\'", "&#39;")
        );
    }
}
