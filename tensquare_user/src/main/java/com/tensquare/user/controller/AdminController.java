package com.tensquare.user.controller;

import com.tensquare.user.pojo.Admin;
import com.tensquare.user.service.AdminService;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    /**
     * 用户登陆
     * @param loginMap
     * @return
     */

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> loginMap) {
        Admin admin = adminService.findByLoginnameAndPassword(loginMap.get("loginname"),loginMap.get("password"));
        if (admin != null) {
            //生成token
            String token = jwtUtil.createJWT(admin.getId(), admin.getLoginname(), "admin");
            Map map = new HashMap();
            map.put("token", token);
            map.put("name", admin.getLoginname());
            return new Result(true, StatusCode.OK, "登陆成功",map);
        }else {
            return new Result(false, StatusCode.LOGINERROR, "用户名或密码错误");
        }
    }

    @Autowired
    private HttpServletRequest request;

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        return null;
    }
}
