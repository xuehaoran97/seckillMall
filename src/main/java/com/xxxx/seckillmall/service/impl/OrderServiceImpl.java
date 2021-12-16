package com.xxxx.seckillmall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.seckillmall.mapper.OrderMapper;
import com.xxxx.seckillmall.pojo.Order;
import com.xxxx.seckillmall.service.IOrderService;
import org.springframework.stereotype.Service;

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

}
