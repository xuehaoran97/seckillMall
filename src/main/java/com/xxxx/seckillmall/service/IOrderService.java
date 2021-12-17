package com.xxxx.seckillmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckillmall.pojo.Order;
import com.xxxx.seckillmall.pojo.User;
import com.xxxx.seckillmall.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haoranXue
 * @since 2021-12-16
 */
public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goods);
}
