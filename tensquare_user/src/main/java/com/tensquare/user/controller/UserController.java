package com.tensquare.user.controller;

import com.tensquare.user.filter.JwtFilter;
import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 增加关注数
     * @param userid
     * @param x
     * @return
     */
    @RequestMapping(value = "/incFollow/{userid}/{x}",method = RequestMethod.POST)
    public void incFollowcount(@PathVariable String userid, @PathVariable int x) {
        userService.incFollowcount(userid, x);
    }

    /**
     * 增加粉丝
     * @param userid
     * @param x
     */
    @RequestMapping(value = "/incfans/{userid}/{x}", method = RequestMethod.POST)
    public void incFanscount(@PathVariable String userid, @PathVariable int x) {
        userService.incFanscount(userid, x);
    }

    /**
     * 查询所有
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 用户登陆
     * @param loginmap
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,String> loginmap) {
        User user = userService.findByMobileAndPassword(loginmap.get("mobile"), loginmap.get("password"));
        if (user != null) {
            String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "user");
            Map map = new HashMap();
            map.put("token", token);
            map.put("name", user.getNickname()); //昵称
            map.put("avatar", user.getAvatar()); //头像
            return new Result(true, StatusCode.OK,"登陆成功");
        }else {
            return new Result(true, StatusCode.LOGINERROR,"用户名或者密码错误");
        }
    }
    /**
     * 用户注册
     * @param user
     * @param code
     * @return
     */
    @RequestMapping(value = "/register/{code}", method = RequestMethod.POST)
    public Result register(@RequestBody User user, @PathVariable String code) {
        userService.add(user, code);
        return new Result(true, StatusCode.OK, "注册成功");
    }

    /**
     * 发送短信
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/sendsms/{mobile}", method = RequestMethod.POST)
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }

    @Autowired
    private HttpServletRequest request;
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        Claims claims = (Claims) request.getAttribute("admin_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
