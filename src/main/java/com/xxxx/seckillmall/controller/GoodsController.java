package com.xxxx.seckillmall.controller;
import com.xxxx.seckillmall.mapper.GoodsMapper;
import com.xxxx.seckillmall.pojo.Goods;
import com.xxxx.seckillmall.pojo.User;
import com.xxxx.seckillmall.service.IGoodsService;
import com.xxxx.seckillmall.service.IUserService;
import com.xxxx.seckillmall.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 商品controller
 *
 * 5000*10
 * windows: 1454/sec
 * linux  785.3/sec
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private GoodsMapper goodsMapper;
    /***
     * 跳转到商品列表页面
     * @param model
     * @return
     */
    @RequestMapping("/toList")
    public String toList( Model model, User user){
        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsService.findGoodsVo());
        return "goodsList";
    }


    /**
     *
     */
    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable Long goodsId){
        model.addAttribute("user",user);

        GoodsVo goodsVo =  goodsService.findGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goodsVo);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date curTime = new Date();
        System.out.println(curTime);
        System.out.println(startDate);

        //秒杀状态 0未开始 1进行中 2结束
        int secKillStatus = 0;
        //剩余开始时间
        int remainSeconds = 0;
        if(curTime.before(startDate)){
            remainSeconds = (int) ((startDate.getTime()-curTime.getTime())/1000);
        }else if(curTime.after(endDate)){
            secKillStatus =2;
            remainSeconds = -1;
        }else{
            secKillStatus=1;
            remainSeconds=0;
        }
        model.addAttribute("secKillStatus",secKillStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        return "goodsDetail";

    }
}
