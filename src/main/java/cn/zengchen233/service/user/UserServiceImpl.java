package cn.zengchen233.service.user;

import cn.zengchen233.dao.BaseDao;
import cn.zengchen233.dao.user.UserDao;
import cn.zengchen233.dao.user.UserDaoImpl;
import cn.zengchen233.pojo.User;
import org.junit.Test;

import java.sql.Connection;

public class UserServiceImpl implements UserService {

    //业务层都会引用dao
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    @Override
    public User login(String userCode, String userPassword) {
        Connection connection = null;
        User user = null;
        try {
            connection = BaseDao.getConnection();
            user = userDao.getLoginStudent(connection, userCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection,null,null);
        }
        return user;
    }

    @Override
    public boolean updatePwd(String userCode, String userPassword) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if (userDao.updatePwd(connection, userCode, userPassword) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, null, null);
        }
        return flag;
    }

    // @Test
    // public void test() {
    //     UserServiceImpl studentService = new UserServiceImpl();
    //     User user = studentService.login("admin", "123456");
    //     System.out.println(user.getUserPassword());
    // }
}
