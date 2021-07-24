package cn.zengchen233.pojo;

import java.util.Date;

public class User {
    private int id;
    private String userCode;
    private String userName;
    private String userPassword;
    private int gender;
    private Date birthday;
    private int userRole;
    private int dormNum;
    private String telephoneNum;
    private String disciplinary;

    public User() {
    }

    public User(int id, String userCode, String userName, String userPassword, int gender, Date birthday, int userRole, int dormNum, String telephoneNum, String disciplinary) {
        this.id = id;
        this.userCode = userCode;
        this.userName = userName;
        this.userPassword = userPassword;
        this.gender = gender;
        this.birthday = birthday;
        this.userRole = userRole;
        this.dormNum = dormNum;
        this.telephoneNum = telephoneNum;
        this.disciplinary = disciplinary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public int getDormNum() {
        return dormNum;
    }

    public void setDormNum(int dormNum) {
        this.dormNum = dormNum;
    }

    public String getTelephoneNum() {
        return telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }

    public String getDisciplinary() {
        return disciplinary;
    }

    public void setDisciplinary(String disciplinary) {
        this.disciplinary = disciplinary;
    }
}
