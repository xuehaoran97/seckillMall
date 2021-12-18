package com.xxxx.seckillmall.controller;
import ch.qos.logback.core.util.TimeUtil;
import com.xxxx.seckillmall.mapper.GoodsMapper;
import com.xxxx.seckillmall.pojo.Goods;
import com.xxxx.seckillmall.pojo.User;
import com.xxxx.seckillmall.service.IGoodsService;
import com.xxxx.seckillmall.service.IUserService;
import com.xxxx.seckillmall.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThymeleafViewResolver thymeleafviewResolver;
    /***
     * 跳转到商品列表页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList( Model model, User user,HttpServletRequest request, HttpServletResponse responsee){

        //检查缓存中是否有商品展示列表
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String)valueOperations.get("goodsList");
        if(!StringUtils.isEmpty(html)){
            return html;
        }

        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsService.findGoodsVo());


        //如果缓存中没有商品展示列表，手动渲染
        WebContext context = new WebContext(request,responsee,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafviewResolver.getTemplateEngine().process("goodsList", context);
        if(!StringUtils.isEmpty(html)){
            valueOperations.set("goodsList",html,60, TimeUnit.SECONDS);
        }

        return html;
    }


    /**
     *
     */
    @RequestMapping(value = "/toDetail/{goodsId}", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail(Model model, User user, @PathVariable Long goodsId, HttpServletResponse response,HttpServletRequest request){
        //检查缓存中是否有商品展示详情页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String)valueOperations.get("goodDetail:"+goodsId);
        if(!StringUtils.isEmpty(html)){
            return html;
        }

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

        //如果缓存中没有商品展示详情页面，手动渲染
        WebContext webContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafviewResolver.getTemplateEngine().process("goodsDetail", webContext);
        if(!StringUtils.isEmpty(html)){
            valueOperations.set("goodsDetail:"+goodsId,html,60, TimeUnit.SECONDS);
        }

        return html;

    }
}
