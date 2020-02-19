package CRUD.TechmasterCRUD.practice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.*;

public class Database {

    private static Database instance = new Database();

    public static Database getInstance() {
        return instance;
    }

    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/javatutorial";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String MAX_POOL = "250"; // set your own limit

    // init connection object
    private Connection connection;
    // init properties object
    private Properties properties;
    private ObservableList<Student> students = FXCollections.observableArrayList();
//    private Map<String, String> accounts = new HashMap<>();


    // connect database
    public Connection connect() {
        if (connection == null) {
            try {
                properties = new Properties();
                properties.setProperty("user", USERNAME);
                properties.setProperty("password", PASSWORD);
                properties.setProperty("MaxPooledStatements", MAX_POOL);
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, properties);
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Cannot connect MySQL DB. Error : " + e.toString());
            }
        }
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<Student> getAllStudents() {
        String sql = "SELECT * FROM Students";
        students.clear();
        try {
            Statement statement = this.connect().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Integer studentID = resultSet.getInt("studentID");
                String studentName = resultSet.getString("name");
                Integer studentAge = resultSet.getInt("age");
                Student student = new Student(studentID, studentName, studentAge);
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            System.out.println("Cannot connect MySQL DB. Error : " + e.toString());
        } finally {
            this.disconnect();
            return students;
        }

    }

    public void addNewStudent(String name, Integer age) {
        String sql = "INSERT INTO Students VALUES (default, ?, ?)";
        try{
            PreparedStatement preparedStatement = this.connect().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Cannot connect MySQL DB. Error : " + e.toString());
        } finally {
            this.disconnect();
        }
    }

    public void updateStudent(Integer studentID, String name, Integer age) {
        String sql = "UPDATE Students SET "+
                "name = ?," +
                "age = ? "+
                "WHERE studentID = ?";
        try{
            PreparedStatement preparedStatement = this.connect().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setInt(3, studentID);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Cannot connect MySQL DB. Error : " + e.toString());
        } finally {
            this.disconnect();
        }
    }

    public void deleteAStudent(Integer studentID) {
        String sql = "DELETE FROM Students WHERE studentID = ?";
        try{
            PreparedStatement preparedStatement = this.connect().prepareStatement(sql);
            preparedStatement.setInt(1, studentID);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Cannot connect MySQL DB. Error : " + e.toString());
        } finally {
            this.disconnect();
        }
    }

    public void deleteStudents(Integer studentID) {

    }

    public Set<Account> listAllAccounts(){
        Set<Account> accounts = new HashSet<Account>();
        String sql = "SELECT * FROM Account";
        try{
            Statement statement = this.connect().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String userName = resultSet.getString("username");
                String userPassword = resultSet.getString("password");
                Account account = new Account(userName,userPassword);
                accounts.add(account);
                return accounts;
            }
        } catch (SQLException e){

        } finally {
            this.disconnect();
            return accounts;
        }

    }
}
