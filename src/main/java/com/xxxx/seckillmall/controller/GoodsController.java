package com.xxxx.seckillmall.controller;
import com.xxxx.seckillmall.pojo.User;
import com.xxxx.seckillmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 商品controller
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;
    /***
     * 跳转到商品列表页面
     * @param model
     * @return
     */
    @RequestMapping("/toList")
    public String toList( Model model, User user){

        model.addAttribute("user",user);
        return "goodsList";
    }
}
