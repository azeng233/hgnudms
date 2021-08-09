package cn.zengchen233.service.user;

import cn.zengchen233.dao.user.UserMapper;
import cn.zengchen233.pojo.User;
import cn.zengchen233.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    private MybatisUtils mybatisUtils;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User login(String userCode) {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User loginUser = mapper.getLoginUser(userCode);

        sqlSession.close();
        return loginUser;
    }

    @Override
    public boolean updatePwd(String userCode, String userPassword) {
        boolean flag = false;
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        int i = mapper.updatePwd(userCode, userPassword);

        if (i > 0) {
            flag = true;
        }
        sqlSession.close();
        return flag;
    }

    @Override
    public int getUserCount(Map<String, Object> map) {
        int count = 0;
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        count = mapper.getUserCount(map);

        sqlSession.close();
        return count;
    }

    @Override
    public List<User> getUserList(Map<String, Object> map) {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> userList = mapper.getUserList(map);

        sqlSession.close();
        return userList;
    }

    @Override
    public boolean add(Map<String, Object> map) {
        boolean flag = false;
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        int add = mapper.add(map);

        if (add > 0) {
            flag = true;
        }

        sqlSession.close();
        return flag;
    }

    @Override
    public boolean deleteUserById(Integer delId) {
        boolean flag = false;
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        int i = mapper.deleteUserById(delId);

        if (i > 0) {
            flag = true;
        }

        sqlSession.close();
        return flag;
    }

    @Override
    public boolean modify(Map<String, Object> map) {
        boolean flag = false;
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        int modify = mapper.modify(map);

        if (modify > 0) {
            flag = true;
        }

        sqlSession.close();
        return flag;
    }

    @Override
    public User selectUserCodeExist(String userCode) {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User loginUser = mapper.getLoginUser(userCode);
        return loginUser;
    }

    @Override
    public User getUserById(int id) {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User userById = mapper.getUserById(id);

        sqlSession.close();
        return userById;
    }

    @Test
    public void test() {
        UserServiceImpl userService = new UserServiceImpl();
        User admin = userService.login("admin");
        System.out.println(admin);
    }
}
