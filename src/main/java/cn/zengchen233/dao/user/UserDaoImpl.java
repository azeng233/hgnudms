package cn.zengchen233.dao.user;

import cn.zengchen233.dao.BaseDao;
import cn.zengchen233.pojo.User;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    //获取要登陆的学生信息
    @Override
    public User getLoginUser(Connection connection, String userCode) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;

        if (connection != null) {
            String sql = "select * from user where userCode=?";
            Object[] params = {userCode};
            rs = BaseDao.execute(connection, pstm, sql, params, rs);

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
            }
            BaseDao.closeResources(null, pstm, rs);
        }
        return user;
    }

    @Override
    public int updatePwd(Connection connection, String userCode, String userPassword) throws Exception {
        PreparedStatement pstm = null;
        int execute = 0;

        if (connection != null) {
            String sql = "update user set userPassword = ? where userCode = ?";
            Object[] params = {userPassword, userCode};
            execute = BaseDao.execute(connection, pstm, sql, params);
            //在Dao层不关闭数据库的连接，因为还有业务层在调用他，在业务层关闭数据库的连接
            BaseDao.closeResources(null, pstm, null);
        }
        return execute;
    }

    @Override
    public int getUserCount(Connection connection, String userName, int userRole) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from user u,user_role r where u.userRole = r.id");

            List<Object> list = new ArrayList<Object>();

            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");//index = 0
            }

            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);//index = 1
            }

            //把list转化为数组
            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());

            rs = BaseDao.execute(connection, pstm, sql.toString(), params, rs);
            if (rs.next()) {
                count = rs.getInt("count");//从结果集获取最终的数量
            }
            BaseDao.closeResources(null, pstm, rs);
        }
        return count;
    }

    //获取用户列表
    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();

        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from user u,user_role r where u.userRole = r.id");
            ArrayList<Object> list = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName like ?");
                list.add("%"+userName+"%");
            }
            if(userRole > 0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            sql.append(" order by id DESC limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, sql.toString(), params, rs);
            while(rs.next()){
                User _user = new User();
                _user.setId(rs.getInt("id"));
                _user.setUserCode(rs.getString("userCode"));
                _user.setUserName(rs.getString("userName"));
                _user.setGender(rs.getInt("gender"));
                _user.setBirthday(rs.getDate("birthday"));
                _user.setTelephoneNum(rs.getString("telephoneNum"));
                _user.setUserRole(rs.getInt("userRole"));
                userList.add(_user);
            }
            BaseDao.closeResources(null, pstm, rs);
        }
        return userList;
    }

    @Override
    public int add(Connection connection, User user) throws Exception {
        PreparedStatement pstm = null;
        int updateRows = 0;
        if(connection != null){
            String sql = "insert into user (userCode,userName,userPassword,gender," +
                    "birthday,userRole,telephoneNum)" +
                    "values(?,?,?,?,?,?,?)";
            Object[] params = {user.getUserCode(),user.getUserName(),user.getUserPassword(),
                    user.getGender(),user.getBirthday(),user.getUserRole(),
                    user.getTelephoneNum()};
            updateRows = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResources(null, pstm, null);
        }
        return updateRows;
    }

    @Override
    public int deleteUserById(Connection connection, Integer delId) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if(null != connection){
            String sql = "delete from user where id = ?";
            Object[] params = {delId};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResources(null, pstm, null);
        }
        return flag;
    }

    @Override
    public int modify(Connection connection, User user) throws Exception {
        int flag = 0;
        PreparedStatement pstm = null;
        if(null != connection){
            String sql = "update user set userName=?,"+
                    "gender=?,birthday=?,telephoneNum=?,userRole=? where id = ? ";
            Object[] params = {user.getUserName(),user.getGender(),user.getBirthday(),
                    user.getTelephoneNum(),user.getUserRole(),user.getId()};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResources(null, pstm, null);
        }
        return flag;
    }

    @Override
    public User getUserById(Connection connection, String id) throws Exception {
        User user = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if(null != connection){
            String sql = "select u.*,r.roleName as userRoleName from user u,user_role r where u.id=? and u.userRole = r.id";
            Object[] params = {id};
            rs = BaseDao.execute(connection, pstm, sql, params, rs);
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setUserRole(rs.getInt("userRole"));
                user.setTelephoneNum(rs.getString("telephoneNum"));
            }
            BaseDao.closeResources(null, pstm, rs);
        }
        return user;
    }
}
