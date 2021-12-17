package com.xxxx.seckillmall.controller;


import com.xxxx.seckillmall.pojo.User;
import com.xxxx.seckillmall.vo.RespBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器 测试接口
 * </p>
 *
 * @author haoranXue
 * @since 2021-12-02
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @RequestMapping("/info")
    @ResponseBody
    public RespBean getUser(User user){
        return RespBean.success(user);
    }

}
