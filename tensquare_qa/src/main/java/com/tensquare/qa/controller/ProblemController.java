package com.tensquare.qa.controller;


import com.tensquare.qa.client.LabelClient;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.hibernate.engine.jdbc.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private LabelClient labelClient;

    @RequestMapping(value = "/label/{labelid}")
    public Result findLabelById(@PathVariable String labelid) {
        System.out.println("no.1");
        return labelClient.findById(labelid);
    }
    /**
     * 最新问题
     * @param labelid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/newlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
    public Result findNewListByLabekId(@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {

        Page<Problem> pageList = problemService.findNewListByLabelId(labelid, page, size);
        PageResult<Problem> pageResult = new PageResult<>(pageList.getTotalElements(), pageList.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 根据标签ID查询热门问题
     * @param labelid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/hoslist/{labelid}/{page}/{size}", method = RequestMethod.GET)
    public Result findHotListByLabelId(@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageList = problemService.findHotListByLabelId(labelid,page,size);
        PageResult<Problem> pageResult = new PageResult<>(pageList.getTotalElements(), pageList.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 等待回答列表
     * @param labelid
     * @param page
     * @param size
     * @return
     */
    public Result findWaitListByLabelId(@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageList = problemService.findWaitListByLabelId(labelid, page, size);
        PageResult<Problem> pageResult = new PageResult<>(pageList.getTotalElements(), pageList.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageList);
    }

    @Autowired
    private HttpServletRequest request;
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Problem problem) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims==null){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
        //
        problem.setUserid(claims.getId());
        problemService.add(problem);
        return new Result(true,StatusCode.OK,"增加成功");
    }
}
