package cn.zengchen233.dao.user;

import cn.zengchen233.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao {
    //获取登录的用户
    public User getLoginStudent(Connection connection, String stuNum) throws Exception;

    //修改密码
    public int updatePwd(Connection connection, String userCode, String userPassword) throws Exception;
}
