package CRUD.TechmasterCRUD.practice;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {

    private final SimpleIntegerProperty studentID;
    private final SimpleStringProperty studentname;
    private final SimpleIntegerProperty studentAge;

    public Student(Integer studentID, String studentName, Integer studentAge) {
        this.studentID = new SimpleIntegerProperty(studentID);
        this.studentname = new SimpleStringProperty(studentName);
        this.studentAge = new SimpleIntegerProperty(studentAge);
    }

    public int getStudentID() {
        return studentID.get();
    }

    public SimpleIntegerProperty studentIDProperty() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID.set(studentID);
    }

    public String getStudentname() {
        return studentname.get();
    }

    public SimpleStringProperty studentnameProperty() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname.set(studentname);
    }

    public int getStudentAge() {
        return studentAge.get();
    }

    public SimpleIntegerProperty studentAgeProperty() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge.set(studentAge);
    }
}
