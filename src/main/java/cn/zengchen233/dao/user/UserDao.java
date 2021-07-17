package cn.zengchen233.dao.user;

import cn.zengchen233.pojo.User;

import java.sql.Connection;

public interface UserDao {
    public User getLoginStudent(Connection connection, String stuNum) throws Exception;
}
