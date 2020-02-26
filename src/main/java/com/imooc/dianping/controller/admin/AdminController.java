package com.imooc.dianping.controller.admin;

import com.imooc.dianping.common.AdminPermission;
import com.imooc.dianping.common.BusinessException;
import com.imooc.dianping.common.EmBusinessError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller("/admin/admin")
@RequestMapping("/admin/admin")
public class AdminController {

    @Value("${admin.email}")
    private String email;

    @Value("${admin.encrptyPassword}")
    private String encrptyPassword;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public static final String CURRENT_ADMIN_SESSION = "currentAdminSession";

    /**
    * @Description: 后台管理首页
    * @Author: Dai Weihao
    * @Date: 2020/2/26 1:57
    * @Param: []
    * @Return: org.springframework.web.servlet.ModelAndView
    **/
    @RequestMapping("/index")
    @AdminPermission
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("/admin/admin/index");
        modelAndView.addObject("CONTROLLER_NAME","admin");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;
    }

    /**
    * @Description: loginpage,返回登录界面
    * @Author: Dai Weihao
    * @Date: 2020/2/26 1:58
    * @Param: []
    * @Return: org.springframework.web.servlet.ModelAndView
    **/
    @RequestMapping("/loginpage")
    public ModelAndView loginpage(){
        ModelAndView modelAndView = new ModelAndView("/admin/admin/login");
        return modelAndView;
    }

    /**
    * @Description: 登录方法
    * @Author: Dai Weihao
    * @Date: 2020/2/26 2:06
    * @Param: [email, password]
    * @Return: java.lang.String
    **/
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(name="email")String email,
                        @RequestParam(name="password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户名密码不能为空");
        }
        if(email.equals(this.email) && encodeByMd5(password).equals(this.encrptyPassword)){
            //登录成功
            httpServletRequest.getSession().setAttribute(CURRENT_ADMIN_SESSION,email);
            return "redirect:/admin/admin/index";
        }else{
            //登录失败
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户名密码错误");
        }
    }

    /**
    * @Description: 添加MD5方法
    * @Author: Dai Weihao
    * @Date: 2020/2/26 2:03
    * @Param: [str]
    * @Return: java.lang.String
    **/
    private String encodeByMd5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //确认计算MD5的方法
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(messageDigest.digest(str.getBytes("utf-8")));
    }
}