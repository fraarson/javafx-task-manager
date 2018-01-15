package org.fraarson.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import org.fraarson.models.Executor;
import org.fraarson.models.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private static final String CREATE_TASK_QUERY = "INSERT INTO task (name, content, creation_date, deadline_date," +
            " executed_date, executor_name, taskgiver_name) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String READ_TASK_BY_TASKGIVER_QUERY = "SELECT * FROM task WHERE taskgiver_name = ?;";
    private static final String READ_TASK_BY_EXECUTOR_QUERY = "SELECT * FROM task WHERE executor_name = ?;";
    private static final String UPDATE_TASK_BY_ID_QUERY = "UPDATE task SET executed_date = ? WHERE id = ?;";
    private static final String UPDATE_TASK_QUERY = "UPDATE task SET name = ?, content = ?, deadline_date =?," +
            " executor_name = ? WHERE id = ?;";


    public void createTask(String name, String content, LocalDate creationDate, LocalDate deadlineDate, LocalDate executedDate,
                           String executorName, String taskgiverName) throws SQLException{

        Connection connection = SessionUtils.getDbConnection();

        try(PreparedStatement statement = connection.prepareStatement(CREATE_TASK_QUERY)){
            statement.setString(1, name);
            statement.setString(2, content);
            if(executedDate == null) {
                statement.setDate(5, null);

            }else {
                statement.setDate(5, java.sql.Date.valueOf(executedDate));
            }

            statement.setDate(3, java.sql.Date.valueOf(creationDate));
            statement.setDate(4, java.sql.Date.valueOf(deadlineDate));
            statement.setString(6, executorName);
            statement.setString(7, taskgiverName);
            statement.execute();
        }catch (SQLException e){
            throw new SQLException();
        }
    }

    public ObservableList<Task> readAllTaskByTaskgiver(String taskgiver){
        Connection connection = SessionUtils.getDbConnection();

        ObservableList<Task> resultList = FXCollections.observableArrayList();

        try(PreparedStatement statement = connection.prepareStatement(READ_TASK_BY_TASKGIVER_QUERY)){

            statement.setString(1, taskgiver);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){

                Task task = new Task();
                task.setId(resultSet.getLong(1));
                task.setTaskName(resultSet.getString(2));
                task.setContent(resultSet.getString(3));
                task.setCreationDate(resultSet.getDate(4).toLocalDate());
                task.setDeadlineDate(resultSet.getDate(5).toLocalDate());
                if(resultSet.getDate(6) != null) {
                    task.setExecutedDate(resultSet.getDate(6).toLocalDate());
                }else {
                    task.setExecutedDate(null);
                }
                task.setExecutor_name(resultSet.getString(7));
                task.setTaskgiver_name(resultSet.getString(8));
                resultList.add(task);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultList;
    }

    public void updateTask(long id, String name, String content, LocalDate deadline, String executor){
        Connection connection = SessionUtils.getDbConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE_TASK_QUERY)){

            statement.setString(1, name);
            statement.setString(2, content);
            statement.setString(4, executor);
            statement.setLong(5, id);
            statement.setDate(3, java.sql.Date.valueOf(deadline));
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ObservableList<Task> readTaskByExecutor(String executorName){

        Connection connection = SessionUtils.getDbConnection();

        ObservableList<Task> resultList = FXCollections.observableArrayList();

        try(PreparedStatement statement = connection.prepareStatement(READ_TASK_BY_EXECUTOR_QUERY)){

            statement.setString(1, executorName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Task task = new Task();
                task.setId(resultSet.getLong(1));
                task.setTaskName(resultSet.getString(2));
                task.setContent(resultSet.getString(3));
                task.setCreationDate(resultSet.getDate(4).toLocalDate());
                task.setDeadlineDate(resultSet.getDate(5).toLocalDate());
                if(resultSet.getDate(6) != null) {
                    task.setExecutedDate(resultSet.getDate(6).toLocalDate());
                }else {
                    task.setExecutedDate(null);
                }
                task.setExecutor_name(resultSet.getString(7));
                task.setTaskgiver_name(resultSet.getString(8));
                resultList.add(task);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultList;
    }

    public void updateTaskById(Long id, LocalDate executedDate){
        Connection connection = SessionUtils.getDbConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE_TASK_BY_ID_QUERY)){
            statement.setLong(2, id);
            statement.setDate(1, java.sql.Date.valueOf(executedDate));
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
