package CRUD_Student;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Login extends Stage {
    private Account account;
    private Scene scene = new Scene(new Group(), 380, 250);
    private GridPane grid = new GridPane();
    private Text scenetitle = new Text("");

    private Label lblUser = new Label("Username: ");
    private Label lblPassword = new Label("Password: ");


    private TextField txtUser = new TextField();
    private PasswordField txtPassword = new PasswordField();

    private Button btnLogin = new Button("Log in");
    private Button btnCancel = new Button("Cancel");

    boolean isLogin = false;
    String username = "";

    public Login() {
        super();

//        if (account != null) {
//            this.account = account;
//            txtUser.setText(account.getUsername());
//            txtPassword.setText(account.getPassword());
//        }
        layoutMyScene();
        String sceneTitle = "Login";
        this.scenetitle.setText(sceneTitle);
        this.scenetitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        this.scenetitle.setText(sceneTitle);
        grid.add(this.scenetitle, 0, 0, 2, 1);
        this.scene.setRoot(grid);
        this.setScene(this.scene);
    }

    private void layoutMyScene() {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(lblUser, 0, 1);
        grid.add(txtUser, 1, 1);

        grid.add(lblPassword, 0, 2);
        grid.add(txtPassword, 1, 2);

        txtUser.setPromptText("username");
        txtPassword.setPromptText("password");
        HBox hbox2Buttons = new HBox(10);
        hbox2Buttons.setAlignment(Pos.BOTTOM_RIGHT);
        hbox2Buttons.setSpacing(15);
        hbox2Buttons.getChildren().setAll(btnLogin, btnCancel);
        grid.add(hbox2Buttons, 0, 3, 2, 1);
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String typedUser = txtUser.getText();
                String typedPassword = txtPassword.getText();
                if (typedUser.trim().equals("") || typedPassword.trim().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("You must enter username and password");
                    alert.showAndWait();
                    return;
                }

                validateLogin(typedUser,typedPassword);
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closeStage();
                App.btnLogin.setText("Log in");
            }
        });
    }

    private boolean validateLogin(String typedUser, String typedPassword){
        final Set<Account> accounts = Database.getInstance().listAllAccounts();
        for (Account account : accounts) {
            if (typedUser.trim().equals(account.getUsername()) && typedPassword.trim().equals(account.getPassword())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successful Login");
                alert.setContentText("You registered");
                alert.showAndWait();
                username = account.getUsername();
                isLogin = true;
                closeStage();
                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed Login");
                alert.setContentText("You must enter correct username and password");
                alert.showAndWait();
            }
        }
        return false;

    }

    private void closeStage() {
        this.close();
    }
}

