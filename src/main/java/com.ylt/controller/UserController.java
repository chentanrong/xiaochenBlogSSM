package com.ylt.controller;

import com.sun.deploy.net.HttpResponse;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import com.ylt.common.JsonResult;
import com.ylt.common.JwtUtil;
import com.ylt.common.SendEmail;
import com.ylt.config.Constants;
import com.ylt.dao.UserDao;
import com.ylt.entity.User;
import com.ylt.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.UUID;

import static com.ylt.config.Constants.EMAIL;

@Api(tags = "用户模块")
@Controller
public class UserController {

    @Autowired
    UserDao userDao;

    @Resource
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    SendEmail sendEmail;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(@RequestBody(required = true) Map<String, Object> map, HttpServletResponse response, HttpServletRequest request) {
        String name = map.get("name").toString();
        String password = map.get("password").toString();
        User login = userService.login(name, password);
        HttpSession session = request.getSession();
        if (login == null) {
            return JsonResult.failed("用户名或密码错误", "用户名或密码错误");
        }
        session.setAttribute(Constants.USERID, login.getId());
        String token = jwtUtil.generToken(UUID.randomUUID().toString(), login.getId(), null);
        Cookie cookie = new Cookie(Constants.TOKEN, token);
        cookie.setMaxAge(30 * 60);// 设置为30min
        cookie.setPath("/");
        response.addCookie(cookie);
        return JsonResult.success(login);
    }

    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getCode(@RequestParam String email,HttpServletRequest request) {
//        Integer userCount = userDao.getUserCount(name);
//        if(userCount>0){
//            return JsonResult.failed("用户名已被占用", "用户名已被占用");
//        }
        if(email.matches(Constants.EMAIL_REGEX)){
            return JsonResult.failed("邮箱格式不正确");
        }
        Integer eamilCount = userDao.getUserCount(email);
        if(eamilCount>0){
            return JsonResult.failed("该邮箱已被注册", "该邮箱已被注册");
        }
        HttpSession session = request.getSession();
        double number = Math.random();
        String i= String.valueOf((int) (number*10000));
        session.setAttribute(Constants.REGISTER_NUMBER,i );
        try {
            sendEmail.send(EMAIL,email,"小陈博客验证码","最贵的用户，您好，您在小陈博客注册的验证码是："+i+"（30分钟有效）");
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return JsonResult.failed("发送失败");
        }
        return JsonResult.success("已发送");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult register(@RequestParam String name,@RequestParam String email,@RequestParam String password,@RequestParam String code, HttpServletRequest request) {
        if(email.matches(Constants.EMAIL_REGEX)){
            return JsonResult.failed("邮箱格式不正确");
        }
        Integer userCount = userDao.getUserCount(name);
        if(userCount>0){
            return JsonResult.failed("用户名已被占用", "用户名已被占用");
        }
        Integer eamilCount = userDao.getUserCount(email);
        if(eamilCount>0){
            return JsonResult.failed("该邮箱已被注册", "该邮箱已被注册");
        }
        HttpSession session = request.getSession();
        String number =(String) session.getAttribute(Constants.REGISTER_NUMBER);
        if(StringUtils.isBlank(number)||StringUtils.isBlank(code)){
            return JsonResult.failed("验证码过期");
        }
        if(number.equals(code.trim())){
            User user=new User();
            user.setId(UUID.randomUUID().toString());
            user.setPhone(email);
            user.setPassword(password);
            user.setName(name);
            Integer integer = userDao.insertUser(user);
            session.removeAttribute(Constants.REGISTER_NUMBER);
            if(integer>0){
                return JsonResult.success(user,"注册成功");
            }

        }

        return JsonResult.failed("注册失败");
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getUserById(@RequestParam String userId) {//3ac62460-fa18-4858-9153-d0d94d25a914
        return JsonResult.success(userService.getUserById(userId));
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView student() {
        return new ModelAndView("user", "command", new User());
    }

    @ApiOperation(value = "新增用户", httpMethod = "POST")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("SpringWeb") User user, ModelMap model) {
        user.setId(UUID.randomUUID().toString());
        userDao.insertUser(user);
        User dbuser = userDao.getUser(user.getId());
        model.addAttribute("name", dbuser.getName());
        model.addAttribute("password", dbuser.getPassword());
        model.addAttribute("id", dbuser.getId());
        return "userShow";
    }

}
