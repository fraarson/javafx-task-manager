package org.fraarson.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class SessionUtils {

    private static final String CREATE_ALL_TABLES_QUERY = "CREATE TABLE department (name varchar(255) NOT NULL, PRIMARY KEY(name));" +
            "CREATE TABLE taskgiver (login varchar(255) NOT NULL, password varchar(255) NOT NULL, department_name varchar(255), PRIMARY KEY(login)," +
            "FOREIGN KEY (department_name) REFERENCES department(name));"+
            "CREATE TABLE executor (login varchar(255) NOT NULL, password varchar(255) NOT NULL, department_name varchar(255), PRIMARY KEY(login), " +
            "FOREIGN KEY (department_name) REFERENCES department(name));" +
            "CREATE TABLE task (id int AUTO_INCREMENT, name varchar(255) NOT NULL, content varchar(255) NOT NULL, creation_date DATE, deadline_date DATE, executed_date DATE, " +
            "executor_name varchar(255) NOT NULL, taskgiver_name varchar(255) NOT NULL, PRIMARY KEY(id), FOREIGN KEY (executor_name) REFERENCES executor(login), " +
            "FOREIGN KEY (taskgiver_name) REFERENCES taskgiver(login));";

    private static final String CREATE_TASKGIVER_QUERY = "INSERT INTO taskgiver (login, password, department_name) VALUES (?, ?, ?);";
    private static final String CREATE_EXECUTOR_QUERY = "INSERT INTO executor (login, password, department_name) VALUES (?, ?, ?);";
    private static final String CREATE_DEPARTMENT_QUERY = "INSERT INTO department (name) VALUES (?);";
    private static final String CREATE_TASK_QUERY = "INSERT INTO task (name, content, creation_date, deadline_date, executed_date, executor_name, taskgiver_name)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?);";


    private static final String DB_URL = "jdbc:h2:~/litv";

    public static Connection getDbConnection(){

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return connection;
    }

    public static void initDB(){

        Connection connection = getDbConnection();

        try(PreparedStatement statement = connection.prepareStatement(CREATE_ALL_TABLES_QUERY)){

            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

        try(PreparedStatement statement = connection.prepareStatement(CREATE_DEPARTMENT_QUERY)){

            statement.setString(1,"main-dep");
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

        try(PreparedStatement statement = connection.prepareStatement(CREATE_TASKGIVER_QUERY)){

            statement.setString(1,"fraarson");
            statement.setString(2,"1024");
            statement.setString(3,"main-dep");
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

        try(PreparedStatement statement = connection.prepareStatement(CREATE_EXECUTOR_QUERY)){

            statement.setString(1,"keizal");
            statement.setString(2,"1024");
            statement.setString(3,"main-dep");
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
        for(int i = 0; i <= 10; i++) {
            try (PreparedStatement statement = connection.prepareStatement(CREATE_TASK_QUERY)) {


                statement.setString(1, "Task1");
                statement.setString(2, "DO DIS DO DAT KIK ASS");
                statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                statement.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                statement.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
                statement.setString(6, "keizal");
                statement.setString(7, "fraarson");
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
