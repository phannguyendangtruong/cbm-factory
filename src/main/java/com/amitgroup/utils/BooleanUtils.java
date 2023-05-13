package com.amitgroup.utils;

public class BooleanUtils {
    public static boolean safeUnboxing(Boolean value){
        if (value == null){
            return false;
        }
        return value;
    }
}
