package cn.zengchen233.servlet.user;

import cn.zengchen233.pojo.Role;
import cn.zengchen233.pojo.User;
import cn.zengchen233.service.role.RoleService;
import cn.zengchen233.service.role.RoleServiceImpl;
import cn.zengchen233.service.user.UserService;
import cn.zengchen233.service.user.UserServiceImpl;
import cn.zengchen233.util.Constant;
import cn.zengchen233.util.PageSupport;
import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("savepwd") && method != null) {
            this.updatePwd(req, resp);
        } else if (method.equals("pwdmodify") && method != null) {
            this.verifyPwd(req, resp);
        } else if (method.equals("query") && method != null) {
            this.query(req, resp);
        }else if(method.equals("add") && method != null) {
            this.add(req, resp);
        } else if (method.equals("deluser") && method != null) {
            this.delUser(req, resp);
        } else if(method.equals("getrolelist")&& method != null) {
            this.getRoleList(req, resp);
        } else if(method.equals("modify") && method != null){
            this.getUserById(req, resp,"usermodify.jsp");
        } else if(method.equals("modifyexe") && method != null){
            this.modify(req, resp);
        } else if(method.equals("ucexist") && method != null){
            this.userCodeExist(req, resp);
        }   else if(method.equals("view") && method != null){
            this.getUserById(req, resp,"userview.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //????????????
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp)  {
        //???Session?????????
        Object o = req.getSession().getAttribute(Constant.USER_SESSION);
        String newpassword = req.getParameter("newpassword");
        boolean flag = false;

        if (o != null && !StringUtils.isNullOrEmpty(newpassword)) { //????????????????????????????????????????????????
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) o).getUserCode(), newpassword);
            if (flag) {
                req.setAttribute(Constant.MESSAGE, "??????????????????,?????????????????????????????????!");
                //?????????????????????????????????Session
                req.getSession().removeAttribute(Constant.USER_SESSION);
                //?????????SysFilter,????????????????????????Session??????????????????????????????
            } else {
                req.setAttribute(Constant.MESSAGE, "??????????????????!");
            }
        } else {
            req.setAttribute(Constant.MESSAGE, "??????????????????!");
        }
        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //???????????????
    public void verifyPwd(HttpServletRequest req, HttpServletResponse resp) {
        Object o = req.getSession().getAttribute(Constant.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");

        //?????????map:?????????
        Map<String, String> resultMap = new HashMap<String, String>();
        if (o == null) { //Session??????
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldpassword)){ //?????????????????????
            resultMap.put("result", "error");
        } else {
            String userPassword = ((User) o).getUserPassword();//Session???????????????
            if (oldpassword.equals(userPassword)) {
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");
            }
        }
        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            //???????????????JSON?????????,???????????????
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //????????????
    //???????????????
    public void query(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //??????????????????
        //?????????????????????
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        String querydormNum = req.getParameter("querydormNum");
        System.out.println(querydormNum);
        int queryUserRole = 0;

        //??????????????????
        UserService userService = new UserServiceImpl();

        List<User> userList = null;
        //???????????????????????????????????????????????????????????????
        int pageSize = Constant.pageSize;
        int currentPageNo = 1;

        if (queryUserName == null){
            queryUserName = "";
        }
        if (temp != null && !temp.equals("")){
            queryUserRole = Integer.parseInt(temp);//???????????????
        }
        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }
        if (querydormNum == null) {
            querydormNum = "";
        }

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("userName", queryUserName);
        map.put("dormNum", querydormNum);
        map.put("userRole", queryUserRole);


        //??????????????????
        int totalCount = userService.getUserCount(map);
        //???????????????
        PageSupport pageSupport = new PageSupport();

        pageSupport.setCurrentPageNo(currentPageNo);

        pageSupport.setPageSize(pageSize);

        pageSupport.setTotalCount(totalCount);

        int totalPageCount = pageSupport.getTotalPageCount();

        //?????????????????????
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }

        //??????????????????????????????????????????????????????Dao???????????????
        int flag = (currentPageNo-1)*pageSize;

        map.put("indexPage", flag);
        map.put("pageSize", pageSize);
        //????????????????????????
        userList = userService.getUserList(map);
        req.setAttribute("userList",userList);

        RoleService roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();

        req.setAttribute("roleList",roleList);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);
        req.setAttribute("queryUserName",queryUserName);
        req.setAttribute("queryUserRole",queryUserRole);
        req.setAttribute("querydormNum",querydormNum);

        //????????????
        try {
            req.getRequestDispatcher("userlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //????????????
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("add()================");
        Map<String,Object> map = new HashMap<>();
        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String userRole = req.getParameter("userRole");
        String dormNum = req.getParameter("dormNum");

        try {
            map.put("birthday",new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        map.put("userCode",userCode);
        map.put("userName",userName);
        map.put("userPassword",userPassword);
        map.put("gender",gender);

        map.put("telephoneNum",phone);
        map.put("userRole",userRole);
        map.put("dormNum",dormNum);

        UserService userService = new UserServiceImpl();
        if(userService.add(map)){
            resp.sendRedirect(req.getContextPath()+"/jsp/user.do?method=query");
        }else{
            req.getRequestDispatcher("useradd.jsp").forward(req, resp);
        }
    }

    //????????????
    private void delUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("uid");
        Integer delId = 0;
        try{
            delId = Integer.parseInt(id);
        }catch (Exception e) {
            delId = 0;
        }
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(delId <= 0){
            resultMap.put("delResult", "notexist");
        }else{
            UserService userService = new UserServiceImpl();
            if(userService.deleteUserById(delId)){
                resultMap.put("delResult", "true");
            }else{
                resultMap.put("delResult", "false");
            }
        }

        //???resultMap?????????json????????????
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    private void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,Object> map = new HashMap<>();
        String id = request.getParameter("uid");
        String userName = request.getParameter("userName");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String dormNum = request.getParameter("dormNum");
        String userRole = request.getParameter("userRole");
        try {
            map.put("birthday",new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        map.put("id",Integer.valueOf(id));
        map.put("userName",userName);
        map.put("gender",Integer.valueOf(gender));
        map.put("telephoneNum",phone);
        map.put("userRole",Integer.valueOf(userRole));
        map.put("dormNum",dormNum);

        UserService userService = new UserServiceImpl();
        if(userService.modify(map)){
            response.sendRedirect(request.getContextPath()+"/jsp/user.do?method=query");
        }else{
            request.getRequestDispatcher("usermodify.jsp").forward(request, response);
        }

    }

    private void userCodeExist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //??????????????????????????????
        String userCode = request.getParameter("userCode");

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(userCode)){
            //userCode == null || userCode.equals("")
            resultMap.put("userCode", "exist");
        }else{
            UserService userService = new UserServiceImpl();
            User user = userService.selectUserCodeExist(userCode);
            if(null != user){
                resultMap.put("userCode","exist");
            }else{
                resultMap.put("userCode", "notexist");
            }
        }

        //???resultMap??????json????????????json???????????????
        //??????????????????????????????
        response.setContentType("application/json");
        //???response??????????????????????????????writer??????
        PrintWriter outPrintWriter = response.getWriter();
        //???resultMap??????json????????? ??????
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();//??????
        outPrintWriter.close();//?????????
    }

    private void getRoleList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Role> roleList = null;
        RoleService roleService = new RoleServiceImpl();
        roleList = roleService.getRoleList();
        //???roleList?????????json????????????
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(roleList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    private void getUserById(HttpServletRequest request, HttpServletResponse response,String url) throws ServletException, IOException {
        String id = request.getParameter("uid");
        if(!StringUtils.isNullOrEmpty(id)){
            //????????????????????????user??????
            UserService userService = new UserServiceImpl();
            User user = userService.getUserById(Integer.parseInt(id));
            request.setAttribute("user", user);
            request.getRequestDispatcher(url).forward(request, response);
        }

    }


}
