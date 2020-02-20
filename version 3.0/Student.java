package CRUD_Student;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {

    private final SimpleIntegerProperty studentID;
    private final SimpleIntegerProperty studentMatrNumber;
    private final SimpleStringProperty studentName;
    private final SimpleIntegerProperty studentAge;
    private final SimpleStringProperty studentMajor;

    public Student(Integer studentID, Integer studentMatrNumber, String studentName, Integer studentAge, String studentMajor) {
        this.studentID = new SimpleIntegerProperty(studentID);
        this.studentMatrNumber = new SimpleIntegerProperty(studentMatrNumber);
        this.studentName = new SimpleStringProperty(studentName);
        this.studentAge = new SimpleIntegerProperty(studentAge);
        this.studentMajor = new SimpleStringProperty(studentMajor);
    }

    public int getStudentID() {
        return studentID.get();
    }

    public SimpleIntegerProperty studentIDProperty() {
        return studentID;
    }

    public String getStudentName() {
        return studentName.get();
    }

    public SimpleStringProperty studentNameProperty() {
        return studentName;
    }

    public int getStudentAge() {
        return studentAge.get();
    }

    public SimpleIntegerProperty studentAgeProperty() {
        return studentAge;
    }

    public String getStudentMajor() {
        return studentMajor.get();
    }

    public SimpleStringProperty studentMajorProperty() {
        return studentMajor;
    }

    public int getStudentMatrNumber() {
        return studentMatrNumber.get();
    }

    public SimpleIntegerProperty studentMatrNumberProperty() {
        return studentMatrNumber;
    }
}
