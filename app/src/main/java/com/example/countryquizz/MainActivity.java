package com.example.countryquizz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.countryquizz.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private  int currentQuestionIndex;
    private  Snackbar mySnackBar;
    private View mainActivity;

    // Create questions for quiz
    private  Quiz[] quizBank = new  Quiz[] {
            new Quiz(R.string.my,R.drawable.perodua,true),
            new Quiz(R.string.germany,R.drawable.ford,false),
            new Quiz(R.string.china,R.drawable.geely,true),
            new Quiz(R.string.sweden,R.drawable.kia,false),
            new Quiz(R.string.france,R.drawable.daimler,false),
            new Quiz(R.string.sk,R.drawable.peugeot,false),
            new Quiz(R.string.france,R.drawable.renault,true),
            new Quiz(R.string.usa,R.drawable.toyota1,false),
            new Quiz(R.string.japan,R.drawable.volkswagen_1,false),
            new Quiz(R.string.sweden,R.drawable.volvo,true)
    };

    // Initialise binder to UI elements and set its action
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // Set visibility of button when view loads up
        configureButton(Boolean.TRUE,View.INVISIBLE, View.INVISIBLE);
        binding.questionImage.setImageResource(quizBank[currentQuestionIndex].getImageName());
        Log.d("MAIN", "setImageResource :"+ quizBank[currentQuestionIndex].getImageName());
        binding.countryName.setText(quizBank[currentQuestionIndex].getQuestions());
        binding.nextButton.setOnClickListener(v-> {
            currentQuestionIndex = (currentQuestionIndex + 1);
            refreshQuestions();
        });
        binding.backButton.setOnClickListener(v-> {
            if (currentQuestionIndex > 0){
                configureButton(Boolean.FALSE,View.VISIBLE,View.VISIBLE);
                currentQuestionIndex = (currentQuestionIndex - 1) % quizBank.length;
                refreshQuestions();

                Log.d("MAIN", "Question index prev :"+currentQuestionIndex);
            } else if (currentQuestionIndex == quizBank.length){

                currentQuestionIndex = 0;
                refreshQuestions();
            }
        });
        binding.falseButton.setOnClickListener(v-> validateAnswers(false));
        binding.trueButton.setOnClickListener(v-> validateAnswers(true));
    }

    // Validate the answers & set the bg color for snackbar
    @SuppressLint("ResourceAsColor")
    private  void validateAnswers(boolean userAnswers){
        configureButton(Boolean.FALSE,View.VISIBLE, View.VISIBLE);
        boolean answerIsCorrect = quizBank[currentQuestionIndex].isAnswerTrue();
        int snackBarMessage;
        int snackBarColor;
        if (answerIsCorrect == userAnswers){
            snackBarMessage = R.string.answer_correct;
            snackBarColor = R.color.green;

        } else {
            snackBarMessage = R.string.answer_incorrect;
            snackBarColor = R.color.red;
        }

        mainActivity = binding.activityMain;
        // Create a snackbar and display the message
        mySnackBar= Snackbar.make(mainActivity, snackBarMessage, Snackbar.LENGTH_SHORT);
        mySnackBar.setBackgroundTint(ContextCompat.getColor(this, snackBarColor));
        mySnackBar.show();
    }

    // Refresh questions when user select next button
    private void refreshQuestions() {
        configureButton(Boolean.TRUE,View.VISIBLE,View.VISIBLE);
        binding.nextButton.setVisibility(View.INVISIBLE);
        binding.backButton.setVisibility(View.VISIBLE);
        if (currentQuestionIndex == quizBank.length) {
            currentQuestionIndex = 0;

        };

        // Set quiz text
        binding.countryName.setText(quizBank[currentQuestionIndex].getQuestions());
        // Set quiz image
        binding.questionImage.setImageResource(quizBank[currentQuestionIndex].getImageName());
    }

    private void configureButton(Boolean answerBtn,int backBtn,int nextBtn) {
        binding.trueButton.setEnabled(answerBtn);
        binding.falseButton.setEnabled(answerBtn);
        binding.backButton.setVisibility(backBtn);
        binding.nextButton.setVisibility(nextBtn);
    }
}