package me.solution.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.solution.model.reqresp.ResultResp;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * util for login user
 *
 * @author davincix
 * @since 2023/5/22 01:34
 */
@Slf4j
public abstract class WebUtil {

    public static void writeFailMsg(ResultResp<?> result, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
