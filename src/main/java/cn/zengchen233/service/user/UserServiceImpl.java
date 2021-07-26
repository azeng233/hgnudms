package cn.zengchen233.service.user;

import cn.zengchen233.dao.BaseDao;
import cn.zengchen233.dao.user.UserDao;
import cn.zengchen233.dao.user.UserDaoImpl;
import cn.zengchen233.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
            user = userDao.getLoginUser(connection, userCode);
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

    @Override
    public int getUserCount(String userName, String userDormNum, int userRole) {
        Connection connection = null;
        int count = 0;
        try {
            connection = BaseDao.getConnection();
            count = userDao.getUserCount(connection, userName,userDormNum ,userRole);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, null, null);
        }
        return count;
    }

    //获取用户列表
    @Override
    public List<User> getUserList(String queryUserName, String userDormNum, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;

        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserList(connection, queryUserName,userDormNum,queryUserRole,currentPageNo,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResources(connection, null, null);
        }
        return userList;
    }

    @Override
    public boolean add(User user) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false); //开启JDBC事务管理
            int updateRows = userDao.add(connection,user);
            connection.commit();
            if(updateRows > 0){
                flag = true;
                System.out.println("add success!");
            }else{
                System.out.println("add failed!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println("rollback==================");
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally{
            //在service层进行connection连接的关闭
            BaseDao.closeResources(connection, null, null);
        }
        return flag;
    }

    @Override
    public boolean deleteUserById(Integer delId) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if(userDao.deleteUserById(connection,delId) > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResources(connection, null, null);
        }
        return flag;
    }

    @Override
    public boolean modify(User user) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if(userDao.modify(connection,user) > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResources(connection, null, null);
        }
        return flag;
    }

    @Override
    public User selectUserCodeExist(String userCode) {
        Connection connection = null;
        User user = null;
        try {
            connection = BaseDao.getConnection();
            user = userDao.getLoginUser(connection, userCode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResources(connection, null, null);
        }
        return user;
    }

    @Override
    public User getUserById(String id) {
        User user = null;
        Connection connection = null;
        try{
            connection = BaseDao.getConnection();
            user = userDao.getUserById(connection,id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            user = null;
        }finally{
            BaseDao.closeResources(connection, null, null);
        }
        return user;
    }


}
