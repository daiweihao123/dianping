package com.imooc.dianping.controller;

import com.imooc.dianping.common.BusinessException;
import com.imooc.dianping.common.CommonRes;
import com.imooc.dianping.common.CommonUtil;
import com.imooc.dianping.common.EmBusinessError;
import com.imooc.dianping.model.UserModel;
import com.imooc.dianping.request.LoginReq;
import com.imooc.dianping.request.RegisterReq;
import com.imooc.dianping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
* @Description:userController
* @Author: Dai Weihao
* @Date: 2020/2/21 1:18
* @Param:
* @Return:
**/
@Controller("/user")
@RequestMapping("/user")
public class UserController {

    public static final String CURRENT_USER_SESSION = "currentUserSession";

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    @ResponseBody
    public CommonRes getUser(@RequestParam(name="id")Integer id) throws BusinessException {
        UserModel userModel = userService.getUser(id);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.NO_OBJECT_FOUND);
        }else{
            return CommonRes.create(userModel);
        }
    }

    /**
    * @Description: 用户注册
    * @Author: Dai Weihao
    * @Date: 2020/2/23 20:44
    * @Param: [registerReq, bindingResult]
    * @Return: com.imooc.dianping.common.CommonRes
    **/
    @RequestMapping("/register")
    @ResponseBody
    public CommonRes register(@Valid @RequestBody RegisterReq registerReq, BindingResult bindingResult) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if(bindingResult.hasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, CommonUtil.processErrorString(bindingResult));
        }
        UserModel registerUser = new UserModel();
        registerUser.setTelphone(registerReq.getTelphone());
        registerUser.setPassword(registerReq.getPassword());
        registerUser.setNickName(registerReq.getNickName());
        registerUser.setGender(registerReq.getGender());

        UserModel resUserModel = userService.register(registerUser);
        return CommonRes.create(resUserModel);

    }

    /**
    * @Description: 用户登录方法
    * @Author: Dai Weihao
    * @Date: 2020/2/23 20:44
    * @Param: [loginReq, bindingResult]
    * @Return: com.imooc.dianping.common.CommonRes
    **/
    @RequestMapping("/login")
    @ResponseBody
    public CommonRes login(@RequestBody @Valid LoginReq loginReq,BindingResult bindingResult) throws UnsupportedEncodingException, NoSuchAlgorithmException, BusinessException {
        if(bindingResult.hasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, CommonUtil.processErrorString(bindingResult));
        }
        UserModel userModel = userService.login(loginReq.getTelphone(),loginReq.getPassword());
        httpServletRequest.getSession().setAttribute(CURRENT_USER_SESSION,userModel);
        return CommonRes.create(userModel);
    }

    /**
    * @Description: 用户注销方法
    * @Author: Dai Weihao
    * @Date: 2020/2/23 21:55
    * @Param: [loginReq, bindingResult]
    * @Return: com.imooc.dianping.common.CommonRes
    **/
    @RequestMapping("/logout")
    @ResponseBody
    public CommonRes login(){
        httpServletRequest.getSession().invalidate();
        return CommonRes.create(null);
    }

    /**
    * @Description: 获取当前用户信息
    * @Author: Dai Weihao
    * @Date: 2020/2/23 23:44
    * @Param: []
    * @Return: com.imooc.dianping.common.CommonRes
    **/
    @RequestMapping("/getcurrentuser")
    @ResponseBody
    public CommonRes getCurrentUser(){
        UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute(CURRENT_USER_SESSION);
        return CommonRes.create(userModel);
    }
}
