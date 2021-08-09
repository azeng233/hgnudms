package cn.zengchen233.dao.user;

import cn.zengchen233.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserMapper {
    //获取登录的用户
    public User getLoginUser(@Param("userCode") String userCode);

    //修改密码
    public int updatePwd(@Param("userCode") String userCode,@Param("userPassword") String userPassword);

    //查询用户总数
    public int getUserCount(Map<String,Object> map);

    //通过条件查询userList
    public List<User> getUserList(Map<String,Object> map);

    //增加用户信息
    public int add(Map<String,Object> map);

    //通过userId删除user
    public int deleteUserById(@Param("id") int delId);

    //修改用户信息
    public int modify(Map<String,Object> map);

    public User getUserById(@Param("id") int id);
}
