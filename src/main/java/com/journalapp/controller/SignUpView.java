package main.java.com.journalapp.controller;

import main.java.com.journalapp.controller.MainController;
import main.java.com.journalapp.util.Session;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SignUpView {
    private final MainController mainController;
    private final VBox mainContainer;

    // Made fields class-level variables so they can be accessed by the clear method
    private final TextField usernameField;
    private final TextField emailField;
    private final PasswordField passField;
    private final Label errorLabel;

    public SignUpView(MainController mainController) {
        this.mainController = mainController;

        mainContainer = new VBox();
        mainContainer.setStyle("-fx-background-color: linear-gradient(to bottom right, #CFE3F3, #FAD0C4);");
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setMaxWidth(Double.MAX_VALUE);
        mainContainer.setMaxHeight(Double.MAX_VALUE);

        VBox formContainer = new VBox(15);
        formContainer.setMaxWidth(350);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.4);" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.8);" +
                        "-fx-border-radius: 15;" +
                        "-fx-border-width: 1px;" +
                        "-fx-padding: 40;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 4);"
        );

        Label titleLabel = new Label("Create Account");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        String inputStyle = "-fx-background-color: rgba(255,255,255,0.7); " +
                "-fx-background-radius: 5; -fx-padding: 10; -fx-font-size: 14px; " +
                "-fx-border-color: rgba(0,0,0,0.1); -fx-border-radius: 5;";

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle(inputStyle);

        emailField = new TextField();
        emailField.setPromptText("Email address");
        emailField.setStyle(inputStyle);

        passField = new PasswordField();
        passField.setPromptText("Create Password");
        passField.setStyle(inputStyle);

        errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-font-size: 14px; -fx-cursor: hand;");
        signUpBtn.setMaxWidth(Double.MAX_VALUE);

        signUpBtn.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passField.getText();
            String username = usernameField.getText().trim();

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                errorLabel.setText("All fields must be filled!");
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);
                return;
            }

            if (Session.signup(username, email, password)) {
                clearFields(); // Clear data before switching
                mainController.showMainApp();
            } else {
                errorLabel.setText("Email already exists!");
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);
            }
        });

        Button loginLink = new Button("Already have an account? Login");
        loginLink.setStyle("-fx-background-color: transparent; -fx-text-fill: #3498db; -fx-cursor: hand;");
        loginLink.setOnAction(e -> {
            clearFields(); // Clear data before switching
            mainController.showLoginView();
        });

        formContainer.getChildren().addAll(titleLabel, usernameField, emailField, passField, errorLabel, signUpBtn, loginLink);
        mainContainer.getChildren().add(formContainer);
    }

    public void clearFields() {
        usernameField.clear();
        emailField.clear();
        passField.clear();
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    public VBox getView() {
        return mainContainer;
    }
}