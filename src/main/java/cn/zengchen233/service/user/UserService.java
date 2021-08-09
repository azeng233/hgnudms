package cn.zengchen233.service.user;

import cn.zengchen233.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //用户登录
    public User login(String userCode);

    //修改密码
    public boolean updatePwd(String userCode, String userPassword);

    //查询用户数量
    public int getUserCount(Map<String,Object> map);

    //根据条件查询用户列表
    public List<User> getUserList(Map<String,Object> map);
    //增加用户
    public boolean add(Map<String,Object> map);

    //根据ID删除user
    public boolean deleteUserById(Integer delId);

    // 修改用户信息
    public boolean modify(Map<String,Object> map);

    public User selectUserCodeExist(String userCode);

    public User getUserById(int id);
}
