package cn.zengchen233.servlet.user;

import cn.zengchen233.pojo.User;
import cn.zengchen233.service.user.UserService;
import cn.zengchen233.service.user.UserServiceImpl;
import cn.zengchen233.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        User user = null;

        //和数据库中的做对比,调用业务层
        UserService userService = new UserServiceImpl();
        user = userService.login(userName, userPassword);

        if (user != null && userPassword.equals(user.getUserPassword())) {
            req.getSession().setAttribute(Constant.USER_SESSION, user);
            //重定向
            resp.sendRedirect("jsp/frame.jsp");
        } else {
            //转发
            req.setAttribute("error","用户名或密码不正确");
            req.getRequestDispatcher("index.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
