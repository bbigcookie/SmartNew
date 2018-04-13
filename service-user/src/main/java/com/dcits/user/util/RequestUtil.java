package com.dcits.user.util;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by gaojunc on 2018/3/13 10:19.
 * Created Reason:
 */
public class RequestUtil {

    public static String getRequestBody(HttpServletRequest request) throws IOException {
        if (request != null) {
            ServletInputStream stream = request.getInputStream();
            int len = request.getContentLength();
            byte[] buffer = new byte[len];
            stream.read(buffer, 0, len);
            return new String(buffer, "utf-8");
        }
        return null;
    }

}
