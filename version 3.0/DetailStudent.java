package CRUD_Student;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class DetailStudent extends Stage {
    private Student student;
    private Scene scene = new Scene(new Group(), 380,250);
    private GridPane grid = new GridPane();
    private Text scenetitle = new Text("");

    private Label lblName = new Label("Student's name");
    private Label lblMatrNumber = new Label("Matriculation Number");
    private Label lblAge = new Label("Student's age");
    private Label lblMajor = new Label("Student's major");

    private TextField txtName = new TextField();
    private TextField txtMatrNumber = new TextField();
    private TextField txtAge = new TextField();
    private TextField txtMajor = new TextField();

    private Button btnOk = new Button("OK");
    private Button btnCancel = new Button("Cancel");

    public DetailStudent(Student student, String sceneTitle){
        super();
        if(student != null){
            this.student = student;
            txtName.setText(student.getStudentName());
            txtMatrNumber.setText(String.valueOf(student.getStudentMatrikelNumber()));
            txtAge.setText(String.valueOf(student.getStudentAge()));
            txtMajor.setText(student.getStudentMajor());
        }
        layoutMyScene();
        this.scenetitle.setText(sceneTitle);
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.scenetitle.setText(sceneTitle);
        grid.add(this.scenetitle, 0,0,2,1);
        this.scene.setRoot(grid);
        this.setScene(this.scene);

    }

    private void layoutMyScene(){
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        grid.add(lblName, 0,1);
        grid.add(txtName,1,1);

        grid.add(lblAge, 0,2);
        grid.add(txtAge,1,2);

        grid.add(lblMajor, 0,3);
        grid.add(txtMajor,1,3);

        grid.add(lblMatrNumber, 0,4);
        grid.add(txtMatrNumber,1,4);

        txtName.setPromptText("Enter student's name");
        txtMatrNumber.setPromptText("Enter student's matriculation number");
        txtAge.setPromptText("Enter student's age");
        txtMajor.setPromptText("Enter student's major");
        HBox hbox2Buttons = new HBox(10);
        hbox2Buttons.setAlignment(Pos.BOTTOM_RIGHT);
        hbox2Buttons.setSpacing(15);
        hbox2Buttons.getChildren().setAll(btnOk, btnCancel);
        grid.add(hbox2Buttons,0,5,2,1);
        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String typedText = txtName.getText();
                Integer typedMatrNumber = Integer.parseInt(txtMatrNumber.getText());
                String typedAge = txtAge.getText();
                String typedMajor = txtMajor.getText();

                if(typedText.trim().equals("") || typedAge.trim().equals("") || typedMajor.trim().equals("") || typedMatrNumber <= 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("You must enter student's name and age and major");
                    alert.showAndWait();
                    return;
                }

                if(!validateMajor(typedMajor.trim())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("You must enter an valid major");
                    alert.showAndWait();
                    return;
                }

                if(validateMatrikelNumber(typedMatrNumber)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Matriculation is already exist");
                    alert.showAndWait();
                    return;
                }

                if(student == null){
                    Database.getInstance().addNewStudent(
                            Integer.parseInt(txtMatrNumber.getText()),
                            txtName.getText(),
                            Integer.parseInt(txtAge.getText()),
                            txtMajor.getText());
                } else {
                    Database.getInstance().updateStudent(student.getStudentID(),
                            Integer.parseInt(txtMatrNumber.getText()),
                            txtName.getText(),
                            Integer.parseInt(txtAge.getText()),
                            txtMajor.getText());
                }

                closeStage();
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closeStage();
            }
        });
    }

    private boolean validateMajor(String major){
        ObservableList<String> majors = Database.getInstance().listAllMajors();
        if(majors.contains(major)){
            return true;
        }
        return false;
    }

    private boolean validateMatrikelNumber(int matrikelNr){
        ObservableList<Integer> matrikelNumbers = Database.getInstance().listAllMatriculationNumbers();
        if(matrikelNumbers.contains(matrikelNr)){
            return true;
        }
        return false;
    }

    private void closeStage(){
        this.close();
    }
}
