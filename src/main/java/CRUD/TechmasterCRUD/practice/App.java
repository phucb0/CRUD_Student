package CRUD.TechmasterCRUD.practice;

//import CRUD.TechmasterCRUD.app.Database;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {
    public static void main(String[] args) {
        System.out.println("Connect SQL Database using Java");
        Application.launch(args);
    }

    private Label lblTitle = new Label("TableView Student");
    private Label lblRegistered = new Label("Not yet registered");

    private Button btnRefresh = new Button("Refresh");
    private Button btnAdd = new Button("Add new Student");
    protected static Button btnLogin = new Button("Log in");
    private TableView<Student> tableView = new TableView<Student>();

    private TableColumn columnStudentID = new TableColumn("Student's ID");
    private TableColumn columnStudentName = new TableColumn("Student's name");
    private TableColumn columnStudentAge = new TableColumn("Student's age");

    private Student selectedStudent;

    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem menuItemDelete = new MenuItem("Delete");

    private MenuItem menuItemProperty = new MenuItem("Properties");

    private VBox vbox = new VBox();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TableView of students");
        btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Database.getInstance().getAllStudents();
            }
        });

        // WARNING: properties of class Student!!!
        columnStudentID.setMinWidth(100);
        columnStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));

        columnStudentName.setMinWidth(200);
        columnStudentName.setCellValueFactory(new PropertyValueFactory<>("studentname"));

        columnStudentAge.setMinWidth(200);
        columnStudentAge.setCellValueFactory(new PropertyValueFactory<>("studentAge"));

        tableView.getColumns().addAll(columnStudentID, columnStudentName, columnStudentAge);


        tableView.setRowFactory(tv -> {
            final TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    contextMenu.hide();

                    if (event.getClickCount() == 2 && !row.isEmpty()) {
                        System.out.println("You double-clicked a row");
                        showDetailStudent(row.getItem());
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        selectedStudent = row.getItem();
                        contextMenu.show(tableView, event.getScreenX(), event.getScreenY());
                        System.out.println("You right-clicked a row");
                    }
                }
            });
            return row;
        });
        contextMenu.getItems().addAll(menuItemDelete, menuItemProperty);
        menuItemProperty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("You pressed: Properties of "
                        + selectedStudent.getStudentname());
                showDetailStudent(selectedStudent);
            }
        });
        menuItemDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("You pressed: Delete of "
                        + selectedStudent.getStudentname());
                Database.getInstance().deleteAStudent(selectedStudent.getStudentID());
                Database.getInstance().getAllStudents();
            }
        });

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (btnLogin.getText().equals("Log in")) {
                    showLogin();
//                    btnLogin.setText("Log out");
                } else if (btnLogin.getText().equals("Log out")) {
                    btnLogin.setText("Log in");
                    tableView.getItems().clear();
                    lblRegistered.setText("Not yet registered");
                    vbox.getChildren().remove(btnAdd);
                    vbox.getChildren().remove(btnRefresh);
                }
            }
        });



        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(lblTitle, lblRegistered, btnLogin, tableView);
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showDetailStudent(null);
            }
        });

        lblTitle.setFont(new Font("Arial", 25));
        lblRegistered.setFont(new Font("Arial", 25));
        Scene scene = new Scene(vbox, 600, 320);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showDetailStudent(Student selectedStudent) {
        DetailStudent detailStudent = new DetailStudent(selectedStudent, "Detail student");

        detailStudent.initModality(Modality.APPLICATION_MODAL);
        detailStudent.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Detail student is hiding");
                Database.getInstance().getAllStudents();
            }
        });
        detailStudent.showAndWait();
    }

    private void showLogin() {
        Login login = new Login();
        login.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (login.isLogin == true) {
                    lblRegistered.setText("Registered by " + login.username);
                    tableView.setItems(Database.getInstance().getAllStudents());
                    btnLogin.setText("Log out");
                    vbox.getChildren().add(btnAdd);
                    vbox.getChildren().add(btnRefresh);
                } else {
                    btnLogin.setText("Log in");
                }
            }
        });
        login.showAndWait();
    }


}
