package com.tensquare.user.service;

import com.tensquare.user.dao.AdminDao;
import com.tensquare.user.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import util.IdWorker;

@Service
public class AdminService {

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private IdWorker idWorker;
    public void add(Admin admin) {
        admin.setId(idWorker.nextId() + "");
        //密码加密
        String newpassword = encoder.encode(admin.getPassword());

        admin.setPassword(newpassword);
        adminDao.save(admin);
    }

    public Admin findByLoginnameAndPassword(String loginname,String password) {
        Admin admin = adminDao.findByLoginname(loginname);
        if (admin != null && encoder.matches(password, admin.getPassword())) {
            return admin;
        }else {
            return null;
        }
    }
}
