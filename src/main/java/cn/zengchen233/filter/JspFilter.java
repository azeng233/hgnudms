package cn.zengchen233.filter;

import cn.zengchen233.pojo.User;
import cn.zengchen233.util.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JspFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User user = (User) request.getSession().getAttribute(Constant.USER_SESSION);

        if (user == null) {
            //此人已被注销，Session找不到她的信息
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
