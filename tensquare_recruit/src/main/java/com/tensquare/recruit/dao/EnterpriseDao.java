package com.tensquare.recruit.dao;


import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 */
public interface EnterpriseDao extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise> {

    /**
     * 按是否人查询企业信息
     * @param isHot
     * @return
     */
    public List<Enterprise> findByIsHot(String isHot);
}
