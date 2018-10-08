package com.github.ivandzf.log4j2customlayout.utils;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * log4j2-custom-layout
 *
 * @author Divananda Zikry Fadilla (01 October 2018)
 * Email: divanandazf@gmail.com
 * <p>
 * Documentation here !!
 */
public class JsonUtils {

    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }

        return gson;
    }

    public static boolean isJsonObjectValid(String message) {
        try {
            new JSONObject(message);
        } catch (JSONException ex) {
            return false;
        }

        return true;
    }

}
