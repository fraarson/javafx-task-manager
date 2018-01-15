package org.fraarson.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.fraarson.models.Executor;
import org.fraarson.models.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExecutorRepository {

    private static final String CREATE_EXECUTOR_QUERY = "INSERT INTO executor (login, password, department_name) VALUES (?, ?, ?);";
    private static final String READ_EXECUTOR_QUERY = "SELECT * FROM executor WHERE login = ? AND DEPARTMENT_NAME = ?;";
    private static final String READ_EXECUTORS_BY_DEPARTMENT_QUERY = "SELECT * FROM executor WHERE DEPARTMENT_NAME = ?;";

    public Executor getExecutorByLogin(String login, String department){

        Connection connection = SessionUtils.getDbConnection();

        try(PreparedStatement statement = connection.prepareStatement(READ_EXECUTOR_QUERY)){
            statement.setString(1, login);
            statement.setString(2, department);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Executor taskgiver = new Executor();
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

    public ObservableList<String> getExecutersByDepartment(String department){
        Connection connection = SessionUtils.getDbConnection();

        ObservableList<String> resultList = FXCollections.observableArrayList();

        try(PreparedStatement statement = connection.prepareStatement(READ_EXECUTORS_BY_DEPARTMENT_QUERY)){

            statement.setString(1, department);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){

                Executor executor = new Executor();

                resultList.add(resultSet.getString(1));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultList;
    }

}
