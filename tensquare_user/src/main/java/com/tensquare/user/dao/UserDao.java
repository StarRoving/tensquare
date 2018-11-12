package com.tensquare.user.dao;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaSpecificationExecutor<User>, JpaRepository<User, String> {

    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    public User findByMobile(String mobile);

    /**
     * 更新粉丝数
     * @param userid
     * @param x
     */
    @Modifying
    @Query("update User u set u.fanscount = u.fanscount+?2 where u.id=?1")
    public void incFanscount(String userid, int x);

    /**
     * 更新关注数
     * @param userid
     * @param x
     */
    @Modifying
    @Query("update User u set u.followcount = u.followcount+?2 where u.id=?1")
    public void incFollowcount(String userid, int x);
}
