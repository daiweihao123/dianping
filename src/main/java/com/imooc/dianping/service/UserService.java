package com.imooc.dianping.service;

import com.imooc.dianping.common.BusinessException;
import com.imooc.dianping.model.UserModel;
import org.apache.catalina.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface  UserService {

    /**
    * @Description: 通过id获取User
    * @Author: Dai Weihao
    * @Date: 2020/2/22 19:08
    * @Param: [id]
    * @Return: com.imooc.dianping.model.UserModel
    **/
    UserModel getUser(Integer id);

    /**
    * @Description: 用户注册
    * @Author: Dai Weihao
    * @Date: 2020/2/23 18:18
    * @Param: [registerUser]
    * @Return: com.imooc.dianping.model.UserModel
    **/
    UserModel register(UserModel registerUser) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
    * @Description: 用户登录方法
    * @Author: Dai Weihao
    * @Date: 2020/2/23 20:12
    * @Param: [telphone, password]
    * @Return: com.imooc.dianping.model.UserModel
    **/
    UserModel login(String telphone, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, BusinessException;
}
