package org.fraarson.controllers;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.fraarson.models.Executor;
import org.fraarson.models.Task;
import org.fraarson.models.Taskgiver;
import org.fraarson.repositories.DepartmentRepository;
import org.fraarson.repositories.ExecutorRepository;
import org.fraarson.repositories.TaskRepository;
import org.fraarson.repositories.TaskgiverRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class Main extends Application implements Initializable{

    private String currentUserName = null;
    private String currentUserDepartment = null;
    private String currentUserStatus = null;

    @FXML private Label loginErrorLabel;
    @FXML private Pane loginPane;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox loginDepartmentField;
    @FXML private Button loginExecutorButton;
    @FXML private Button loginTaskgiverButton;
    @FXML private Pane mainPane;
    @FXML private Button logoutButton;
    @FXML private Button aboutButton;
    @FXML private TableView taskTable;
    @FXML private TableColumn<Task, Integer> idCol;
    @FXML private TableColumn<Task, String> titleCol;
    @FXML private TableColumn<Task, String> contentCol;
    @FXML private TableColumn<Task, String> creationDateCol;
    @FXML private TableColumn<Task, String> deadlineCol;
    @FXML private TableColumn<Task, String> executedDateCol;
    @FXML private TableColumn<Task, String> executorCol;
    @FXML private TableColumn<Task, String> taskgiverCol;
    @FXML private Button updateListButton;
    @FXML private Button finishButton;
    @FXML private Button createNewButton;
    @FXML private TextField showTitleField;
    @FXML private TextField showTaskgiverField;
    @FXML private TextArea showContentField;
    @FXML private DatePicker createdDateView;
    @FXML private DatePicker deadlineDateView;
    @FXML private Button updateThisButton;
    @FXML private Pane creationPane;
    @FXML private TextField createNameField;
    @FXML private ComboBox createExecutorField;
    @FXML private TextArea createContentField;
    @FXML private DatePicker createDeadlineDate;
    @FXML private Button createButton;
    @FXML private Label createErrorLabel;
    @FXML private Label personLabel;
    @FXML private Label taskId;
    @FXML private Button showTodayButton;
    @FXML private Button showAllButton;
    @FXML private Label filterLabel;
    @FXML private DatePicker dateFilter;

    @Override
    public void start(Stage primaryStage) throws Exception{

        //SessionUtils.initDB();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("application.fxml"));
        primaryStage.setTitle("Task Manager v0.1");
        primaryStage.setScene(new Scene(root, 1221,763));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loginExecutorButton.setOnAction(event -> {
            ExecutorRepository repository = new ExecutorRepository();

            Executor executor = repository.getExecutorByLogin(loginField.getText(), (String) loginDepartmentField.getSelectionModel().getSelectedItem());

            if(executor != null) {
                if (currentUserName == null && executor.getPassword().equals(passwordField.getText())) {
                    currentUserName = executor.getLogin();
                    currentUserDepartment = executor.getDepartment();
                    currentUserStatus = "executor";
                    loginPane.setVisible(false);
                    mainPane.setVisible(true);
                    finishButton.setVisible(true);
                    updateThisButton.setVisible(false);
                    createNewButton.setVisible(false);
                    showTitleField.setEditable(false);
                    showTaskgiverField.setEditable(false);
                    showContentField.setEditable(false);
                    deadlineDateView.setEditable(false);
                    executorCol.setVisible(false);
                    taskgiverCol.setVisible(true);
                    loginErrorLabel.setVisible(false);
                    personLabel.setText("Taskgiver:");
                    showAllButton.setVisible(true);
                    showTodayButton.setVisible(true);
                    filterLabel.setVisible(true);
                    dateFilter.setVisible(false);

                    ObservableList<Task> tasks = new TaskRepository().readTaskByExecutor(currentUserName);
                    taskTable.setItems(tasks);

                } else {
                    loginErrorLabel.setVisible(true);
                }
            }else {
                loginErrorLabel.setVisible(true);
            }

        });

        loginTaskgiverButton.setOnAction(event -> {
            TaskgiverRepository repository = new TaskgiverRepository();
            ExecutorRepository executorRepository = new ExecutorRepository();

            Taskgiver taskgiver = repository.getTaskgiverByLogin(loginField.getText(), (String) loginDepartmentField.getSelectionModel().getSelectedItem());

            if(taskgiver != null) {
                if (currentUserName == null && taskgiver.getPassword().equals(passwordField.getText())) {
                    currentUserName = taskgiver.getLogin();
                    currentUserDepartment = taskgiver.getDepartment();
                    currentUserStatus = "taskgiver";
                    loginPane.setVisible(false);
                    mainPane.setVisible(true);
                    finishButton.setVisible(false);
                    updateThisButton.setVisible(true);
                    createNewButton.setVisible(true);
                    showTitleField.setEditable(true);
                    showTaskgiverField.setEditable(false);
                    showContentField.setEditable(true);
                    deadlineDateView.setEditable(true);
                    executorCol.setVisible(true);
                    taskgiverCol.setVisible(false);
                    loginErrorLabel.setVisible(false);
                    personLabel.setText("Executor:");
                    showAllButton.setVisible(false);
                    showTodayButton.setVisible(false);
                    filterLabel.setVisible(false);
                    dateFilter.setVisible(true);

                    createExecutorField.getItems().addAll(executorRepository.getExecutersByDepartment((String) loginDepartmentField.getSelectionModel().getSelectedItem()));

                    ObservableList<Task> tasks = new TaskRepository().readAllTaskByTaskgiver(currentUserName);
                    taskTable.setItems(tasks);
                } else {
                    loginErrorLabel.setVisible(true);
                }
            }else {
                loginErrorLabel.setVisible(true);
            }

        });

        logoutButton.setOnAction(event -> {

            currentUserName = null;
            currentUserDepartment = null;
            currentUserStatus = null;

            mainPane.setVisible(false);
            loginPane.setVisible(true);

            loginField.setText(null);
            passwordField.setText(null);

        });

        aboutButton.setOnAction(event -> {
            try {
                Parent rootAbout = FXMLLoader.load(getClass().getClassLoader().getResource("about.fxml"));
                Stage stage = new Stage();

                stage.setTitle("About");
                stage.setScene(new Scene(rootAbout, 445, 260));

                stage.setResizable(false);
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        showTodayButton.setOnAction(event -> {

            TableColumn column = (TableColumn) taskTable.getColumns().get(5);

            ObservableList<Task> filtringList = taskTable.getItems();

            ObservableList<Task> resultList = FXCollections.observableArrayList();

            filtringList.forEach(task -> {
                if(task.getExecutedDate() == null){
                    resultList.add(task);
                }
            });
            taskTable.getItems().setAll(resultList);
        });

        dateFilter.setOnAction(event -> {
            TableColumn column = (TableColumn) taskTable.getColumns().get(5);

            ObservableList<Task> filtringList = taskTable.getItems();

            ObservableList<Task> resultList = FXCollections.observableArrayList();

            filtringList.forEach(task -> {
                if(task.getDeadlineDate().equals(dateFilter.getValue())){
                    resultList.add(task);
                }
            });
            taskTable.getItems().setAll(resultList);
        });

        showAllButton.setOnAction(event -> {
            taskTable.setItems(new TaskRepository().readTaskByExecutor(currentUserName));
        });

        updateListButton.setOnAction(event -> {
            ObservableList<Task> tasks;
            if(currentUserStatus.equals("executor")){
                tasks = new TaskRepository().readTaskByExecutor(currentUserName);
            }else {
                tasks = new TaskRepository().readAllTaskByTaskgiver(currentUserName);

            }
            taskTable.setItems(tasks);
        });

        createNewButton.setOnAction(event -> {
            mainPane.setVisible(false);
            creationPane.setVisible(true);
            createNameField.setText(null);
            createContentField.setText(null);
            createDeadlineDate.setValue(null);
        });

        createButton.setOnAction(event -> {
            TaskRepository taskRepository = new TaskRepository();

            if(createNameField.getText() != null && createContentField.getText() != null && createDeadlineDate.getValue() != null &&
                    createExecutorField.getSelectionModel().getSelectedItem() != null) {
                try {
                    taskRepository.createTask(createNameField.getText(), createContentField.getText(), LocalDate.now(),
                            createDeadlineDate.getValue(), null, (String) createExecutorField.getSelectionModel().getSelectedItem(), currentUserName);
                    creationPane.setVisible(false);
                    mainPane.setVisible(true);
                } catch (SQLException e) {
                    createErrorLabel.setVisible(true);
                }
            }else {
                createErrorLabel.setVisible(true);
            }
        });

        updateThisButton.setOnAction(event -> {
            TaskRepository repository = new TaskRepository();

            repository.updateTask(Long.valueOf(taskId.getText()), showTitleField.getText(), showContentField.getText(),
                    deadlineDateView.getValue(), showTaskgiverField.getText());

        });

        taskTable.getSelectionModel().selectedItemProperty().addListener(event -> {

            Task task = (Task) taskTable.getSelectionModel().getSelectedItem();
            if(task != null) {
                showTitleField.setText(task.getTaskName());
                if(currentUserStatus.equals("executor")) {
                    showTaskgiverField.setText(task.getTaskgiver_name());
                }else {
                    showTaskgiverField.setText(task.getExecutor_name());
                }
                showContentField.setText(task.getContent());
                createdDateView.setValue(task.getCreationDate());
                deadlineDateView.setValue(task.getDeadlineDate());
                taskId.setText(String.valueOf(task.getId()));
            }

        });

        finishButton.setOnAction(event -> {
            TaskRepository repository = new TaskRepository();
            Task task = (Task) taskTable.getSelectionModel().getSelectedItem();
            repository.updateTaskById(task.getId(), LocalDate.now());
        });


        loginDepartmentField.getItems().addAll(new DepartmentRepository().readDepartmentNames());
        createdDateView.setEditable(false);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        contentCol.setCellValueFactory(new PropertyValueFactory<>("content"));
        creationDateCol.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        taskgiverCol.setCellValueFactory(new PropertyValueFactory<>("taskgiver_name"));
        deadlineCol.setCellValueFactory(new PropertyValueFactory<>("deadlineDate"));
        executorCol.setCellValueFactory(new PropertyValueFactory<>("executor_name"));
        executedDateCol.setCellValueFactory(new PropertyValueFactory<>("executedDate"));
        taskTable.getColumns().clear();
        taskTable.getColumns().addAll(idCol, titleCol, contentCol, creationDateCol, deadlineCol, executedDateCol, taskgiverCol, executorCol);
    }
}
