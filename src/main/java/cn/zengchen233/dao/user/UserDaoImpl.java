package cn.zengchen233.dao.user;

import cn.zengchen233.dao.BaseDao;
import cn.zengchen233.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    //获取要登陆的学生信息
    @Override
    public User getLoginStudent(Connection connection, String userCode) throws Exception {
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
}
