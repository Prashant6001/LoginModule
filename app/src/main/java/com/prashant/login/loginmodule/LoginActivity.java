package com.prashant.login.loginmodule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {

    @BindView(R.id.btnLogin)
    Button login;
    @BindView(R.id.txtUsername)
    EditText txtUserName;
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.version_number)
    TextView version_number;
    Context context;
    boolean checkUnFlag = false, checkPwdFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

//        I have used butterKnife for viewx
        ButterKnife.bind(this);

        try {
            String version = getPackageManager().getPackageInfo(
                    getPackageName(), 0).versionName;
            version_number.setText("Ver " + version);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Version not found", e.getLocalizedMessage());
        }
        login.setEnabled(false);
        login.setBackgroundColor(getResources().getColor(R.color.colorGrey));

        txtUserName.addTextChangedListener(generalTextWatcher);
        txtPassword.addTextChangedListener(generalTextWatcher);
    }


    @OnClick(R.id.btnLogin)
    public void onLoginClick() {

        Intent homeIntent = new Intent(context, HomeScreenActivity.class);
        homeIntent.putExtra(Constants.USER_NAME, txtUserName.getText().toString());
        startActivity(homeIntent);
        finish();

    }


    private TextWatcher generalTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (txtUserName.getText().hashCode() == s.hashCode() && count != 0) {
                if (isAlphaNumeric(s) && !s.toString().contains(" ") && s.toString().length() > 5) {
                    checkUnFlag = true;

                } else if (s.charAt(s.length() - 1) == ' ') {
                    txtUserName.setError("space not allowed");
                    checkUnFlag = false;
                } else if (!isAlphaNumeric(Character.toString(s.charAt(s.length() - 1)))) {
                    txtUserName.setError(s.charAt(s.length() - 1) + " is not allowed in username.");
                    checkUnFlag = false;
                } else {
                    checkUnFlag = false;
                }

            } else if (txtPassword.getText().hashCode() == s.hashCode() && count != 0) {
                if (isAlphaNumeric(s) && !s.toString().contains(" ") && s.toString().length() > 5) {
                    checkPwdFlag = true;

                } else if (s.charAt(s.length() - 1) == ' ') {
                    txtPassword.setError("space is not allowed in password.");
                    checkPwdFlag = false;
                } else if (!isAlphaNumeric(Character.toString(s.charAt(s.length() - 1)))) {
                    txtPassword.setError(s.charAt(s.length() - 1) + " is not allowed in password.");
                    checkPwdFlag = false;
                } else {
                    checkPwdFlag = false;
                }
            }

            /** Check if both flags are true then only enable the login button and change the color **/

            if ((checkUnFlag == true) && (checkPwdFlag == true)) {
                login.setEnabled(true);
                login.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            } else {
                login.setEnabled(false);
                login.setBackgroundColor(getResources().getColor(R.color.colorGrey));
            }
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

    };

    public boolean isAlphaNumeric(CharSequence s) {
        String pattern = "^[a-zA-Z0-9]*$";
        if (s.toString().matches(pattern)) {
            return true;
        }
        return false;
    }

}
