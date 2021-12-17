package com.xxxx.seckillmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.seckillmall.pojo.Goods;
import com.xxxx.seckillmall.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author haoranXue
 * @since 2021-12-16
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> findGoodsVo();

    Goods check();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
