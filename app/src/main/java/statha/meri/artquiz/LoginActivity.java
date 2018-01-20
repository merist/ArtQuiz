package statha.meri.artquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by user on 1/10/2018.
 */

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    String  nameText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LoginActivity loginActivity = LoginActivity.this;
                EditText editText = (EditText)findViewById(R.id.name_edit_text);
                nameText = editText.getText().toString();

                if(!TextUtils.isEmpty(nameText)) {
                    SharedPreferences.Editor settings  = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                    settings.putString("name", nameText);
                    settings.commit();
                    Intent mainActivityIntent = new Intent(loginActivity, MainActivity.class);
                    loginActivity.startActivity(mainActivityIntent);
                    finish();
                }
                else
                {
                    Toast.makeText(loginActivity,getText(R.string.enter_your_name), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}