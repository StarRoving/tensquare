package com.tensquare.recruit.service;


import com.tensquare.recruit.dao.EnterpriseDao;
import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

@Service
public class EnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 按照热门状态查询
     * @param isHot
     * @return
     */
    public List<Enterprise> findByIsHot(String isHot) {
        return enterpriseDao.findByIsHot(isHot);
    }
}
