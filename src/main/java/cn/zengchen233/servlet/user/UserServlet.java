package cn.zengchen233.servlet.user;

import cn.zengchen233.pojo.User;
import cn.zengchen233.service.user.UserService;
import cn.zengchen233.service.user.UserServiceImpl;
import cn.zengchen233.util.Constant;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newpassword = req.getParameter("newpassword");
        String renewpassword = req.getParameter("renewpassword");
        Object o = req.getSession().getAttribute(Constant.USER_SESSION);
        boolean flag = false;

        if (o != null && !StringUtils.isNullOrEmpty(newpassword) ) { //如果这个人取到了，且新密码不为空
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) o).getUserCode(), newpassword);
            System.out.println(flag);
            if (flag) {
                req.setAttribute(Constant.MESSAGE, "密码修改成功,请退出并使用新密码登录!");
                //密码修改成功，移除当前Session
                req.getSession().removeAttribute(Constant.USER_SESSION);
                //因为有SysFilter,只要发现没有这个Session就立马跳转到错误页面
            } else {
                req.setAttribute(Constant.MESSAGE, "密码修改失败!");
            }
        } else {
            req.setAttribute(Constant.MESSAGE, "新密码有问题!");
        }
        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
