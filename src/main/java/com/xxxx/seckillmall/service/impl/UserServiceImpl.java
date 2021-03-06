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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xxxx.seckillmall.pojo.User;
import org.thymeleaf.util.StringUtils;

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

    @Autowired
    private RedisTemplate redisTemplate;

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
            throw new GlobalException(RespBeanEnum.MOBILE_ERROR);
        }


        //生成cookie
        String ticket = UUIDUtil.uuid();
        redisTemplate.opsForValue().set("user:"+ticket,user);

        //request.getSession().setAttribute(ticket,user);

        CookieUtil.setCookie(request,response,"userTicket",ticket);


        return RespBean.success(ticket);
    }

    /**
     * 根据cookie获取用户
     * @param userTicket
     * @return
     */

    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isEmpty(userTicket)){
            return null;
        }

        User user = (User)redisTemplate.opsForValue().get("user:" + userTicket);
        if(user!=null){
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }

        return user;
    }

    /**
     * 更新密码
     *
     * @param userTicket
     * @param id
     * @param password
     * @return
     */
    @Override
    public RespBean updatePassword(String userTicket, Long id, String password) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);
        }
        user.setPassword(MD5Utils.inputPassToDBPass(password, user.getSalt()));
        int result = userMapper.updateById(user);
        if (1 == result) {
            //删除Redis
            redisTemplate.delete("user:" + userTicket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);
    }
}
