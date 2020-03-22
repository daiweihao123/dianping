package com.imooc.dianping.controller.admin;

import com.imooc.dianping.common.AdminPermission;
import com.imooc.dianping.common.BusinessException;
import com.imooc.dianping.common.CommonUtil;
import com.imooc.dianping.common.EmBusinessError;
import com.imooc.dianping.model.SellerModel;
import com.imooc.dianping.request.SellerCreateReq;
import com.imooc.dianping.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller("/admin/seller")
@RequestMapping("/admin/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    /**
    * @Description: 商户列表
    * @Author: Dai Weihao
    * @Date: 2020/3/23 1:44
    * @Param: []
    * @Return: org.springframework.web.servlet.ModelAndView
    **/
    @RequestMapping("/index")
    @AdminPermission
    public ModelAndView index(){
        List<SellerModel> sellerModelList = sellerService.selectAll();

        ModelAndView modelAndView = new ModelAndView("/admin/seller/index.html");
        modelAndView.addObject("data",sellerModelList);
        modelAndView.addObject("CONTROLLER_NAME","seller");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;

    }

    /**
    * @Description: 创建商家
    * @Author: Dai Weihao
    * @Date: 2020/3/23 2:02
    * @Param:
    * @Return:
    **/
    @RequestMapping("/createpage")
    @AdminPermission
    public ModelAndView createPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/seller/create.html");
        modelAndView.addObject("CONTROLLER_NAME","seller");
        modelAndView.addObject("ACTION_NAME","create");
        return modelAndView;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @AdminPermission
    public String create(@Valid SellerCreateReq sellerCreateReq, BindingResult bindingResult) throws BusinessException {
        if(bindingResult.hasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, CommonUtil.processErrorString(bindingResult));
        }

        SellerModel sellerModel = new SellerModel();
        sellerModel.setName(sellerCreateReq.getName());
        sellerService.create(sellerModel);
        return "redirect:/admin/seller/index";
    }
}