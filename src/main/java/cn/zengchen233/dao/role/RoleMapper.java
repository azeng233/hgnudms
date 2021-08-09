package cn.zengchen233.dao.role;

import cn.zengchen233.pojo.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleMapper {
    //获取角色列表
    public List<Role> getRoleList();
}
