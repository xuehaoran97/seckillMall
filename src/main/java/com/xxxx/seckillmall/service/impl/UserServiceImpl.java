package com.xxxx.seckillmall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.seckillmall.exception.GlobalException;
import com.xxxx.seckillmall.mapper.UserMapper;
import com.xxxx.seckillmall.pojo.User;
import com.xxxx.seckillmall.service.IUserService;
import com.xxxx.seckillmall.utils.CookieUtil;
import com.xxxx.seckillmall.utils.MD5Utils;
import com.xxxx.seckillmall.utils.UUIDUtil;
import com.xxxx.seckillmall.vo.LoginVo;
import com.xxxx.seckillmall.vo.RespBean;
import com.xxxx.seckillmall.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haoranXue
 * @since 2021-12-02
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录功能的一堆校验
     * @param loginVo
     * @param request
     * @param response
     * @return
     */


    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        //获取用户名，密码
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        User user = userMapper.selectById(mobile);
        if(user == null){
            throw new GlobalException(RespBeanEnum.LOHGIN_ERROR);
        }
        //判断密码是否正确
        if(!MD5Utils.formPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            throw new GlobalException(RespBeanEnum.LOHGIN_ERROR);
        }


        //生成cookie
        String ticket = UUIDUtil.uuid();
        request.getSession().setAttribute(ticket,user);


        CookieUtil.setCookie(request,response,"userTicket",ticket);


        return RespBean.success(ticket);
    }
}
