package com.xxxx.seckillmall.controller;
import com.xxxx.seckillmall.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;

/**
 * 商品controller
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    /***
     * 跳转到商品列表页面
     * @param session
     * @param model
     * @param ticket
     * @return
     */
    @RequestMapping("/toList")
    public String toList(HttpSession session, Model model,@CookieValue("userTicket") String ticket){
        if(StringUtils.isEmpty(ticket)){
            return "login";
        }
        User user = (User) session.getAttribute(ticket);
        if(null == user){
            return "login";
        }
        model.addAttribute("user",user);
        return "goodsList";
    }
}
