package org.fraarson.repositories;

import org.fraarson.models.Taskgiver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskgiverRepository {

    private static final String CREATE_TASKGIVER_QUERY = "INSERT INTO taskgiver (login, password, department_name) VALUES (?, ?, ?);";
    private static final String READ_TASKGIVER_QUERY = "SELECT * FROM taskgiver WHERE login = ? AND department_name = ?;";

    public Taskgiver getTaskgiverByLogin(String login, String department){

        Connection connection = SessionUtils.getDbConnection();

        try(PreparedStatement statement = connection.prepareStatement(READ_TASKGIVER_QUERY)){
            statement.setString(1, login);
            statement.setString(2, department);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Taskgiver taskgiver = new Taskgiver();
                taskgiver.setLogin(resultSet.getString(1));
                taskgiver.setPassword(resultSet.getString(2));
                taskgiver.setDepartment(resultSet.getString(3));
                return taskgiver;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
