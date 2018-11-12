package com.tensquare.qa.service;


import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {

    @Autowired
    private ProblemDao problemDao;

    /**
     * 根据ID查询问题列表
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findNewListByLabelId(String labelId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findNewListByLabelId(labelId, pageRequest);
    }

    /**
     * 根据标签ID查询热门
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findHotListByLabelId(String labelId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findHotListByLabelId(labelId,pageRequest);
    }

    /**
     * 根据标签ID查询等待回答
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findWaitListByLabelId(String labelId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findWaitListByLabelId(labelId, pageRequest);
    }

    public void add(Problem problem) {
        problemDao.save(problem);
    }
}
