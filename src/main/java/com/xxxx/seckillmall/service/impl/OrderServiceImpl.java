package com.xxxx.seckillmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.seckillmall.mapper.OrderMapper;
import com.xxxx.seckillmall.pojo.Order;
import com.xxxx.seckillmall.pojo.SeckillGoods;
import com.xxxx.seckillmall.pojo.SeckillOrder;
import com.xxxx.seckillmall.pojo.User;
import com.xxxx.seckillmall.service.IOrderService;
import com.xxxx.seckillmall.service.ISeckillGoodsService;
import com.xxxx.seckillmall.service.ISeckillOrderService;
import com.xxxx.seckillmall.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haoranXue
 * @since 2021-12-16
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private ISeckillGoodsService seckillGoodsService;


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Order seckill(User user, GoodsVo goods) {

        //扣减秒杀表的库存
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
        //seckillGoodsService.updateById(seckillGoods);
        //减库存时判断库存是否足够
        seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().set("stock_count",seckillGoods.getStockCount()).eq("id",seckillGoods.getId()).gt("stock_count",0));

        Order order = new Order();
        order.setUserId(user.getId().toString());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);

        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setUserId(user.getId().toString());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);

        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + goods.getId(), JSON.toJSONString(seckillOrder));

        return order;
    }
}
