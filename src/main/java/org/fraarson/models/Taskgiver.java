package org.fraarson.models;

public class Taskgiver {

    private String login;

    private String password;

    private String department;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getDepartment() {
        return department;
    }
}