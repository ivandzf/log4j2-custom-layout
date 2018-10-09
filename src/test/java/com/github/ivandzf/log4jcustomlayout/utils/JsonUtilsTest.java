package com.github.ivandzf.log4jcustomlayout.utils;

import com.github.ivandzf.log4j2customlayout.utils.JsonUtils;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * log4j-custom-layout
 *
 * @author Divananda Zikry Fadilla (02 October 2018)
 * Email: divanandazf@gmail.com
 * <p>
 * Documentation here !!
 */
@RunWith(JUnit4.class)
public class JsonUtilsTest {

    @Test
    public void gsonTest() {
        Gson gson = JsonUtils.getGson();
        Assert.assertNotNull(gson);
    }

    @Test
    public void isJsonObjectValidTestSuccess() {
        String message = "{\"message\":\"test message\",\"newField\":{\"test key\":\"test value\"}}";
        Assert.assertNotNull(JsonUtils.generateCustomMessage(message));
    }

    @Test
    public void isJsonObjectValidTestFailed() {
        String message = "[{\"message\":\"test message\",\"newField\":{\"test key\":\"test value\"}}]";
        Assert.assertNull(JsonUtils.generateCustomMessage(message));
    }

}
