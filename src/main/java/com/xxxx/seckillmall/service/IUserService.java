package com.xxxx.seckillmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckillmall.pojo.User;
import com.xxxx.seckillmall.vo.LoginVo;
import com.xxxx.seckillmall.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haoranXue
 * @since 2021-12-02
 */
public interface IUserService extends IService<User> {

    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);
}
