import cn.zengchen233.pojo.User;
import cn.zengchen233.service.user.UserService;
import cn.zengchen233.service.user.UserServiceImpl;
import cn.zengchen233.util.Constant;
import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


//实现Servlet复用
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("savepwd")) {
            this.modifyPsw(req, resp);
        } else  if (method.equals("pwdmodify")) {
            this.verifyPsw(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         doGet(req, resp);
    }

    //修改密码
    protected void modifyPsw(HttpServletRequest req, HttpServletResponse resp) {
        Object userSession = req.getSession().getAttribute(Constant.USER_SESSION);
        boolean success = false;
        String newpassword = req.getParameter("newpassword");
        if (userSession != null && !StringUtils.isNullOrEmpty(newpassword)) {
            UserService userService = new UserServiceImpl();
            success = userService.updatePwd(((User) userSession).getUserCode(), newpassword);
            if (success) {
                req.setAttribute("message", "修改密码成功，请退出，使用新密码重新登录");
                req.getSession().removeAttribute(Constant.USER_SESSION);//移除当前Session，让用户重新登录
                try {
                    resp.sendRedirect("/login.jsp");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                req.setAttribute("message", "密码修改失败");
            }
        }
        else {
            req.setAttribute("message", "新密码设置有误");
        }
//        req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
    }

    //验证旧密码
    protected  void verifyPsw(HttpServletRequest req, HttpServletResponse resp) {
        Object userSession = req.getSession().getAttribute(Constant.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");

        Map<String, String> resultMap = new HashMap<>();
        if (userSession == null) { //说明Session已经过期了
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldpassword)){ //说明旧密码为空，即密码错误
            resultMap.put("result", "error");
        } else {

            String userPassword = ((User)userSession).getUserPassword();
            if (userPassword.equals(oldpassword)) {
                //说明旧密码与该回话的密码是相同的
                resultMap.put("result", "true");
            } else {
                //说明输入密码错误
                resultMap.put("result", "false");
            }
        }
        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            //JSONArray   是Alibaba的JSON工具类 ， 转换格式
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
