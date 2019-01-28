package com.ylt.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ylt.common.JsonResult;
import com.ylt.common.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.spring.web.json.Json;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class LoginIntercepter implements HandlerInterceptor {

    @Autowired
    JwtUtil jwtUtil;

    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String tokenStr = httpServletRequest.getParameter(Constants.TOKEN);
        Cookie[] cookies = httpServletRequest.getCookies();
        String token = "";
        if(cookies==null){
            dealErrorReturn(httpServletRequest, httpServletResponse, gson.toJson(JsonResult.failed("获取Cookie失败")));
        }
        for (Cookie c : cookies) {
            if (c.getName().equals(Constants.TOKEN)) {
                token = c.getValue();
            }
        }
        if (StringUtils.isBlank(token) && StringUtils.isBlank(tokenStr)) {
            dealErrorReturn(httpServletRequest, httpServletResponse, gson.toJson(JsonResult.failed("TOKEN验证失败")));
            return false;
        }
        if (!StringUtils.isBlank(tokenStr)) {
            token = tokenStr;
        }
        token = jwtUtil.updateToken(token);
        for (Cookie c : cookies) {
            if (c.getName().equals(Constants.TOKEN)) {
                c.setValue(token);
            }
        }
//        httpServletResponse.setHeader(Constants.TOKEN, token);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void dealErrorReturn(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object obj) {
        String json = (String) obj;
        PrintWriter writer = null;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/html; charset=utf-8");
        httpServletResponse.setStatus(401);
        try {
            writer = httpServletResponse.getWriter();
            writer.print(json);

        } catch (IOException ex) {
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}
