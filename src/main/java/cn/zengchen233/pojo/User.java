package cn.zengchen233.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int id;
    private String userCode;
    private String userName;
    private String userPassword;
    private int gender;
    private Date birthday;
    private int userRole;
    private String dormNum;
    private String telephoneNum;
    private String disciplinary;
    private Integer age;
}
