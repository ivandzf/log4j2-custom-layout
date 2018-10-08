package com.github.ivandzf.log4j2customlayout.message;

import com.github.ivandzf.log4j2customlayout.utils.JsonUtils;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Map;

/**
 * log4j2-custom-json-layout
 *
 * @author Divananda Zikry Fadilla (30 September 2018)
 * Email: divanandazf@gmail.com
 * <p>
 * Documentation here !!
 */
@Value
@Builder
public class CustomMessage implements Serializable {

    private String message;
    private Map<String, Object> newField;

    public String toJson() {
        return JsonUtils.getGson().toJson(this);
    }

}
