package com.imooc.dianping.service;

import com.imooc.dianping.model.SellerModel;

import java.util.List;

public interface SellerService {
    
    /**
    * @Description: 创建功能
    * @Author: Dai Weihao
    * @Date: 2020/3/23 1:40
    * @Param: [sellerModel]
    * @Return: com.imooc.dianping.model.SellerModel
    **/
    SellerModel create(SellerModel sellerModel);
    
    /**
    * @Description: 查询方法
    * @Author: Dai Weihao
    * @Date: 2020/3/23 1:40
    * @Param: [id]
    * @Return: com.imooc.dianping.model.SellerModel
    **/
    SellerModel get(Integer id);
    
    /**
    * @Description: 查询所有方法
    * @Author: Dai Weihao
    * @Date: 2020/3/23 1:41
    * @Param: []
    * @Return: List<SellerModel>
    **/
    List<SellerModel> selectAll();
    
    /**
    * @Description: 设置启用、禁用状态
    * @Author: Dai Weihao
    * @Date: 2020/3/23 1:42
    * @Param: [id, disabledFlag]
    * @Return: com.imooc.dianping.model.SellerModel
    **/
    SellerModel changeStatus(Integer id, Integer disabledFlag);
}
