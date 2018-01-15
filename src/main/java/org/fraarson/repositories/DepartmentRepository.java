package org.fraarson.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.fraarson.models.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentRepository {
    private static final String READ_DEPARTMENT_QUERY = "SELECT * FROM department;";

    public ObservableList<String> readDepartmentNames(){
        Connection connection = SessionUtils.getDbConnection();

        ObservableList<String> resultList = FXCollections.observableArrayList();

        try(PreparedStatement statement = connection.prepareStatement(READ_DEPARTMENT_QUERY)){

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                resultList.add(resultSet.getString(1));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultList;
    }
}
