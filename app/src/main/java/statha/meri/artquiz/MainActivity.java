package statha.meri.artquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
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
    int finalResult = 0;
    String subject;
    String body ;
    String[] addresses = {"meristatha@gmail.com"};


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
        RadioButton radioButtonQ1A2 = findViewById(R.id.question1answer2);

        // Answer for question 2
        RadioButton radioButtonQ2A2 = findViewById(R.id.question2answer2);

        // Answers for question 3
        CheckBox radioButtonQ3A2 = findViewById(R.id.question3answer2);
        CheckBox radioButtonQ3A4 = findViewById(R.id.question3answer4);

        // Answer for question 4
        EditText editTextQ4 = findViewById(R.id.question4_answer);
        String question4Text = editTextQ4.getText().toString();
        String userAnswerQ4 = getText(R.string.answerQuestion4).toString();
        // Convert the user's input to lower case
        editTextQ4.setAllCaps(false);

        // Answer for question 5
        RadioButton radioButtonQ5A1 = findViewById(R.id.question5answer1);

        // Answers for question 6
        CheckBox radioButtonQ6A2 = findViewById(R.id.question6answer2);
        CheckBox radioButtonQ6A3 = findViewById(R.id.question6answer3);

        // Answer for question 7
        RadioButton radioButtonQ7A4 = findViewById(R.id.question7answer4);

        // Answer for question 8
        RadioButton radioButtonQ8A3 = findViewById(R.id.question8answer3);

        // Answer for question 9
        EditText editTextQ9 = findViewById(R.id.question9answer);
        String question9Text = editTextQ9.getText().toString();
        String userAnswerQ9 = getText(R.string.answerQuestion9).toString();
        // Convert the user's input to lower case
        editTextQ9.setAllCaps(false);

        // Answer for question 10
        RadioButton radioButtonQ10A1 = findViewById(R.id.question10answer1);

        // Check answer for question 1
        if (radioButtonQ1A2.isChecked()) {
            finalResult++;
        }
        // Check answer for question 2
        if (radioButtonQ2A2.isChecked()) {
            finalResult++;
        }
        // Check answer for question 3
        if (radioButtonQ3A2.isChecked() && radioButtonQ3A4.isChecked()) {
            finalResult++;
        }
        // Check answer for question 4
        if (question4Text.equals(userAnswerQ4)) {
            finalResult++;
        }
        // Check answer for question 5
        if (radioButtonQ5A1.isChecked()) {
            finalResult++;
        }
        // Check answers for question 6
        if (radioButtonQ6A2.isChecked() && radioButtonQ6A3.isChecked()) {
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
        // Check answer for question 9
        if (question9Text.equals(userAnswerQ9)) {
            finalResult++;
        }
        // Check answer for question 10
        if (radioButtonQ10A1.isChecked()) {
            finalResult++;
        }
        displayDialog(finalResult);
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
    public void play() {
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
    public void stop() {
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
    /**
     * Set all the fields for the email(the email address, the subject and the body)
     *
     * @param addresses - the email addresses to send the email
     * @param subject - the subject of the email
     * @param body - the content of the email
     */
    public void composeEmail(String[] addresses, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * Display final result in a dialog. Add Cancel, Try again and Send result buttons in the dialog
     * @param finalResult - is the final score of the user
     */
    public void displayDialog(int finalResult) {
        int scorePercent = finalResult * 10;
        body = getText(R.string.hello) + " " + name + "!";
        body += "\n" + getText(R.string.thank_you_message) ;
        body += "\n" + getText(R.string.yourResult) + " " + scorePercent+"%.";
        subject = getText(R.string.result).toString();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getText(R.string.result));
        alertDialogBuilder.setMessage(name + ", " + getText(R.string.yourResult) + " " + scorePercent +"%");
        alertDialogBuilder.setIcon(R.drawable.tick);
        // Add cancel button to return in the main activity
        alertDialogBuilder.setNegativeButton(getText(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // Add try again button to reset all the answers and begin again the quiz
        alertDialogBuilder.setPositiveButton(getText(R.string.tryAgain), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getApplicationContext().startActivity(mainActivityIntent);
                finish();
            }
        });
        // Add send result button to send the final result by email
        alertDialogBuilder.setNeutralButton(getText(R.string.sendResult), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                composeEmail(addresses,subject,body);
            }
        });
        // Display the dialog
        alertDialogBuilder.show();
    }
}