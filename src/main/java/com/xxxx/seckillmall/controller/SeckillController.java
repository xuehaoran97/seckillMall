package com.xxxx.seckillmall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.xxxx.seckillmall.pojo.Order;
import com.xxxx.seckillmall.pojo.SeckillOrder;
import com.xxxx.seckillmall.pojo.User;
import com.xxxx.seckillmall.service.IGoodsService;
import com.xxxx.seckillmall.service.IOrderService;
import com.xxxx.seckillmall.service.ISeckillOrderService;
import com.xxxx.seckillmall.service.impl.OrderServiceImpl;
import com.xxxx.seckillmall.vo.GoodsVo;
import com.xxxx.seckillmall.vo.RespBean;
import com.xxxx.seckillmall.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private IOrderService service;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, User user, Long goodsId) {
        if(user == null){
            return "login";
        }

        model.addAttribute("user",user);

        //检查库存
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        if(goods.getStockCount()<1){
            model.addAttribute("errmsg", RespBeanEnum.STOCK_EMPTY.getMessage());
            return "seckillFail";
        }
        //判断是否重复下单
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq(
                "goods_id",
                goodsId));
        if(seckillOrder!=null){
            model.addAttribute("errmsg",RespBeanEnum.REPEATE_ERROR.getMessage());
            return "seckillFail";
        }

        Order order = orderService.seckill(user,goods);
        model.addAttribute("order",order);
        model.addAttribute("goods",goods);
        return "orderDetail";

    }
}
