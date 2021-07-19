package cn.zengchen233.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//操作数据库的公共类
public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    //静态代码块，类加载的时候就会自动初始化
    static {
        Properties properties = new Properties();
        //本地数据库
        // InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("local.properties");
        //要部署到服务器就是用云服务器
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("database.properties");

        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    //获取数据库的链接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // System.out.println("数据库初始化中...");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            // System.out.println("数据库连接成功!");
        } catch (Exception e) {
            // System.out.println("数据库连接失败...");
        }
        return connection;
    }

    //查询公共类
    public static ResultSet execute(Connection connection, PreparedStatement preparedStatement, String sql, Object[] params, ResultSet resultSet) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        //根据sql里面的问号来遍历，从1开始
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    //增删改公共类
    public static int execute(Connection connection, PreparedStatement preparedStatement, String sql, Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        //根据sql里面的问号来遍历，从1开始
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeUpdate();
    }

    //释放资源类
    public static boolean closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        boolean flag = true;
        if (resultSet != null) {
            try {
                resultSet.close();
                resultSet = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                preparedStatement = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        return true;
    }

}
