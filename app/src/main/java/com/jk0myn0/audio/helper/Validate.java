package com.jk0myn0.audio.helper;

import android.content.Context;
import android.widget.EditText;

import com.jk0myn0.audio.R;

/**
 * Created by Root on 29/12/2015.
 */
public class Validate {

    static Context ctx;
    public Validate(Context ctx) {
        this.ctx = ctx;
    }

    public static boolean isValid(EditText inputUserName, EditText inputPassword) {
        boolean valid = true;
        String userName = inputUserName.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (password.isEmpty() || password.length() < 6 || password.length() > 15) {
            String stringError = ctx.getString(R.string.val_password);
            inputPassword.setError(stringError);
            valid = false;
        } else {
            // Necessary to remove any previoius visual error set here
            inputPassword.setError(null);
        }

        if (userName.isEmpty() || userName.length() < 4 || userName.length() > 15) {
            String stringError = ctx.getString(R.string.val_username);
            inputUserName.setError(stringError);
            valid = false;
        } else {
            inputUserName.setError(null);
        }

        return valid;
    }

    public static boolean isValid(EditText inputUserName, EditText inputPassword, EditText inputEmail) {
        boolean valid = true;
        String userName = inputUserName.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();

        if (password.isEmpty() || password.length() < 6 || password.length() > 15) {
            String stringError = ctx.getString(R.string.val_password);
            inputPassword.setError(stringError);
            valid = false;
        } else {
            // Necessary to remove any previoius visual error set here
            inputPassword.setError(null);
        }

        if (userName.isEmpty() || userName.length() < 4 || userName.length() > 15) {
            String stringError = ctx.getString(R.string.val_username);
            inputUserName.setError(stringError);
            valid = false;
        } else {
            inputUserName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("enter a valid email address");
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        return valid;
    }


}
