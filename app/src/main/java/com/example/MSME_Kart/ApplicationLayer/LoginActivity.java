package com.example.MSME_Kart.ApplicationLayer;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MSME_Kart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {


        private TextView loginText; // The login text at the top of the application
        private EditText emailAddressField; // The E-mail Address field.

        private EditText passwordField; // The Password field
        private Button loginButton; // Variable for the login button
        private Button registerButton;

    private FirebaseFirestore firebaseFirestore;
        private FirebaseAuth auth; // Firebase authentication variable.

        private Pattern regexPatterns = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]"); // Regex patterns that will be used to check if the entered e-mail address and password is valid.
        private final int LOGOUT_BUTTON_ID = 101;

        private boolean isAdded = false; // Determines if the details have been added.
        public boolean isLoggedIn = false; // Determines if the user is logged in
        private Button logoutBtn; // Logout button variable.

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            this.emailAddressField = findViewById(R.id.emailAddressField);
            this.passwordField = findViewById(R.id.passwordField);
            this.loginButton = findViewById(R.id.loginBtn);
            this.registerButton = findViewById(R.id.registerBtn);

            this.firebaseFirestore = FirebaseFirestore.getInstance();
            this.auth = FirebaseAuth.getInstance();

            this.logoutBtn = findViewById(R.id.logout_button);

            this.loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateEmailAddress(); // Invoke routine to validate the e-mail address
                    validatePassword();
                }
            });
            this.registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(regIntent);
                }
            });
        }



        private boolean validateEmailAddress() { // Routine that validates the e-mail address when logging in.
            String emailEntry = emailAddressField.getText().toString().trim(); // Get the email address entry
            Context context = getApplicationContext();

            String[] tempResources = new String[]{context.getString(R.string.emailError), context.getString(R.string.emailAtSymbol)};

            if (!regexPatterns.matcher(emailEntry).find() && emailEntry.isEmpty()) { // If there is no @ symbol and the email field is empty

                AlertDialog.Builder emailError = new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.error)
                        .setMessage(tempResources[0]).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        });

                emailError.show();
                emailAddressField.setError(tempResources[1]);

                emailAddressField.setText("");
                return false;

            }

            else {
                emailAddressField.setError(null); // Set no error otherwise
                login(); // Log the user in.
                return true; // Return true.
            }
        }

        private boolean validatePassword() { // Routine that validates the password when logging in.

            String passwordEntry = passwordField.getText().toString().trim(); // Get the password entry
            String flushedString = "";

            if (passwordEntry.isEmpty()) { // If the password field is left empty

                AlertDialog.Builder passwordError = new AlertDialog.Builder(LoginActivity.this).setTitle("Password Error")
                        .setMessage("Password should not be left empty")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int which) {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        });

                passwordError.show();
                passwordField.setError("Password cannot be left empty");
                passwordField.setText(flushedString);

                return false;
            }

            else {
                passwordField.setError(null);
                login();

                showLoginDialogue();
                return true;
            }
        }

        private void showLoginDialogue() { // Routine that shows the login dialogue.
            final long sleepSeconds = 2400; // The number of seconds for the thread to sleep for.

            Context context = getApplicationContext();
            String[] tempResources = new String[]{context.getString(R.string.loggingIn), context.getString(R.string.wait)};
            final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

            dialog.setTitle(tempResources[0]);
            dialog.setMessage(tempResources[1]);

            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            new Thread(new Runnable() { // Create a new thread
                @Override
                public void run() { // Routine to run the threaad

                    try {
                        Thread.sleep(sleepSeconds);
                    }

                    catch (InterruptedException exc) {

                        Log.d(String.valueOf(R.string.error), exc.toString());
                    }

                    dialog.dismiss();
                }
            }).start();

            dialog.show(); // Show the progress bar
        }

        private void login() { // Logs the user in
            final String emailInput = emailAddressField.getText().toString(); // Get the e-mail input
            String passwordInput = passwordField.getText().toString(); // Get the password input

            auth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) { // If the task is successful
                        isLoggedIn = true;

                        Toast.makeText(LoginActivity.this, "You are logged in as " + emailInput, Toast.LENGTH_LONG).show();
                        transitionToHomepage(); // Take user to homepage
                        setVisibilityOfLogout();
                    }

                    else if (!task.isSuccessful()) { // If the task is not successful, i.e the credentials do not match
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_LONG).show(); // Show error message
                        isLoggedIn = false;
                    }
                }

                private void setVisibilityOfLogout() {
                }
            });
        }

        public void transitionToHomepage() { // Routine that takes user to home page
            try {
                Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }

            catch (ActivityNotFoundException act) {
                Log.d(String.valueOf(R.string.error), act.toString());
            }
        }
    }
