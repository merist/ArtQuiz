package statha.meri.artquiz;

import android.content.SharedPreferences;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer mediaPlayer;
    String name;
    int finalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        name = settings.getString("name", "");
        // If user has entered the name then display a welcome message
        if (name != null) {
            Toast.makeText(this, getText(R.string.welcome) + " " + name, Toast.LENGTH_SHORT).show();
        }

        ImageButton btnPlay = (ImageButton) findViewById(R.id.buttonPlay);
        ImageButton btnStop = (ImageButton) findViewById(R.id.buttonStop);

        //Register button click listener for play and stop buttons
        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);

    }

    /*
     * Calculate the final result of the quiz and store the value in the finalResult
     * @param view
     */
    public void calculateResult(View view) {
        finalResult = 0;
        // Answer for question 1
        RadioButton radioButtonQ1A2 = findViewById(R.id.question1_answer2_rb);

        // Answer for question 2
        RadioButton radioButtonQ2A2 = findViewById(R.id.question2_answer2_rb);

        // Answers for question 3
        CheckBox checkboxQ3A1 = findViewById(R.id.question3_answer1_chb);
        CheckBox checkboxQ3A2 = findViewById(R.id.question3_answer2_chb);
        CheckBox checkboxQ3A3 = findViewById(R.id.question3_answer3_chb);
        CheckBox checkboxQ3A4 = findViewById(R.id.question3_answer4_chb);

        // Answer for question 4
        EditText editTextQ4 = findViewById(R.id.question4_answer_etx);
        String question4Text = editTextQ4.getText().toString();
        String userAnswerQ4 = getText(R.string.answerQuestion4).toString();

        // Answer for question 5
        RadioButton radioButtonQ5A1 = findViewById(R.id.question5_answer1_rb);

        // Answers for question 6
        CheckBox checkboxQ6A1 = findViewById(R.id.question6_answer1_chb);
        CheckBox checkboxQ6A2 = findViewById(R.id.question6_answer2_chb);
        CheckBox checkboxQ6A3 = findViewById(R.id.question6_answer3_chb);
        CheckBox checkboxQ6A4 = findViewById(R.id.question6_answer4_chb);

        // Answer for question 7
        RadioButton radioButtonQ7A4 = findViewById(R.id.question7_answer4_rb);

        // Answer for question 8
        RadioButton radioButtonQ8A3 = findViewById(R.id.question8_answer3_rb);

        // Answer for question 9
        EditText editTextQ9 = findViewById(R.id.question9_answer_etx);
        String question9Text = editTextQ9.getText().toString();
        String userAnswerQ9 = getText(R.string.answerQuestion9).toString();

        // Answer for question 10
        RadioButton radioButtonQ10A1 = findViewById(R.id.question10_answer1_rb);

        // Check answer for question 1
        if (radioButtonQ1A2.isChecked()) {
            finalResult++;
        }
        // Check answer for question 2
        if (radioButtonQ2A2.isChecked()) {
            finalResult++;
        }
        // Check answer for question 3
        if (checkboxQ3A2.isChecked() && checkboxQ3A4.isChecked() && !checkboxQ3A1.isChecked() && checkboxQ3A4.isChecked()) {
            finalResult++;
        }
        // Check answer for question 4 and ignore case from user input
        if (question4Text.equalsIgnoreCase(userAnswerQ4)) {
            finalResult++;
        }
        // Check answer for question 5
        if (radioButtonQ5A1.isChecked()) {
            finalResult++;
        }
        // Check answers for question 6
        if (checkboxQ6A2.isChecked() && checkboxQ6A3.isChecked() && !checkboxQ6A1.isChecked() && !checkboxQ6A4.isChecked()) {
            finalResult++;
        }
        // Check answers for question 7
        if (radioButtonQ7A4.isChecked()) {
            finalResult++;
        }
        // Check answer for question 8
        if (radioButtonQ8A3.isChecked()) {
            finalResult++;
        }
        // Check answer for question 9 and ignore case from user input
        if (question9Text.equalsIgnoreCase(userAnswerQ9)) {
            finalResult++;
        }
        // Check answer for question 10
        if (radioButtonQ10A1.isChecked()) {
            finalResult++;
        }
        Toast.makeText(MainActivity.this, getText(R.string.yourResult) + " " + finalResult, Toast.LENGTH_SHORT).show();
    }

    // Register button click event, Call play method if button Play is clicked else call stop method.
    public void onClick(View view) {
        int buttonId = view.getId();

        if (buttonId == R.id.buttonPlay) {
            play();
        } else if (buttonId == R.id.buttonStop) {
            stop();
        }
    }

    // Play music
    private void play() {
        try {
            stop();
            mediaPlayer = MediaPlayer.create(this, R.raw.fur_elise);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    // Stop music
    private void stop() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}