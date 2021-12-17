package com.xxxx.seckillmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckillmall.pojo.Goods;
import com.xxxx.seckillmall.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haoranXue
 * @since 2021-12-16
 */
public interface IGoodsService extends IService<Goods> {
    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
