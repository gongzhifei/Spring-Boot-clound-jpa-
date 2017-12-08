package com.xxd.util;

import com.xxd.dto.user.UserInfo;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gongzhifei
 */
@Component
public class PathConversion {

    public String spliceUrl(String url, Object object) throws IllegalAccessException {
        Class obj = object.getClass();
        Field[] fields = obj.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(object) != null) {
                if (i == 0) {
                    url += "?" + field.getName() + "=" + field.get(object);
                    i++;
                } else {
                    url += "&" + field.getName() + "=" + field.get(object);
                }
            }
        }
        return url;
    }


}
