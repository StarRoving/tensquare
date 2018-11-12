package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<String> {

    /**
     * 根据标签ID查询最新问题
     * @param labelId
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in(select problemid from Pl where labelid = ?1) order by replytime desc")
    public Page<Problem> findNewListByLabelId(String labelId, Pageable pageable);

    /**
     * 根据标签id查询热门问题
     * @param labelId
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in (select problemid from Pl where labelid=?1)order by reply desc ")
    public Page<Problem> findHotListByLabelId(String labelId, Pageable pageable);

    @Query("select p from Problem p where id in(select problemid from Pl where labelid=?1)and reply=0 order by createtime desc")
    public Page<Problem> findWaitListByLabelId(String labelId, Pageable pageable);
}
