package com.example.dominic.mathquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;

/**
 * Created by dominic on 15/06/2015.
 */
public class QuestionActivity extends Activity implements View.OnClickListener {
    private static String[] answerArray = new String[5];
    private static Category category = null;
    private static int correctAnswers;
    private static boolean[] correctArray;
    private static String[] questionArray;
    private static int questionNr = 1;
    private Question currentQuestion = null;

    static
    {
        correctAnswers = 0;
        correctArray = new boolean[5];
        questionArray = new String[5];
    }

    public static boolean correctQuestion(int paramInt)
    {
        return correctArray[paramInt];
    }

    public static String[] getAnswers()
    {
        return answerArray;
    }

    public static Category getCategory()
    {
        return category;
    }

    public static int getPoints()
    {
        return correctAnswers;
    }

    public static String[] getQuestions()
    {
        return questionArray;
    }

    public static void setCategory(Category paramCategory)
    {
        category = paramCategory;
        questionNr = 1;
        correctAnswers = 0;
    }

    public void finish()
    {
        super.finish();
    }

    public boolean lastQuestion()
    {
        return questionNr == 5;
    }

    public void makeQuestionUI(Question paramQuestion)
    {
        setContentView(R.layout.questionui);
        this.currentQuestion = paramQuestion;
        if (paramQuestion == null) {
            Log.v("QUESTIONACTIVITY: ", "category is null");
        }
        WebView localWebView = (WebView)findViewById(R.id.questionTextId);
        Log.v("QUESTIONACTIVITY: ", "TEST");
        if (localWebView == null) {
            Log.v("QUESTIONACTIVITY: ", "view QuestionTextID is null");
        }
        localWebView.loadDataWithBaseURL(null, Tools.formatMathHTML(paramQuestion.getQuestion()) + "?", "text/html", "utf-8", null);

        setTitle(getResources().getString(R.string.question_label) + " " + questionNr + " " + TextConstants.get(R.string.of) + " " + 5);
        Iterator localIterator = paramQuestion.getAllAnswers();
        Log.v("QUESTIONACTIVITY: nr of answers = ", localIterator.toString());
        int i = -1;
        for (;;)
        {
            if (!localIterator.hasNext())
            {
                if (i < 3)
                {
                    RadioButton localRadioButton2 = (RadioButton)findViewById(R.id.radio3);
                    localRadioButton2.setText("");
                    localRadioButton2.setVisibility(View.INVISIBLE);
                }
                if (i < 2)
                {
                    RadioButton localRadioButton1 = (RadioButton)findViewById(R.id.radio2);
                    localRadioButton1.setText("");
                    localRadioButton1.setVisibility(View.INVISIBLE);
                }
                findViewById(R.id.questionSubmit).setOnClickListener(this);
                findViewById(R.id.stopQuiz).setOnClickListener(this);
                return;
            }
            i++;
            Spannable localSpannable = Tools.formatMathSpannable((String)localIterator.next());
            switch (i)
            {
                default:
                    break;
                case 0:
                    ((RadioButton)findViewById(R.id.radio0)).setText(localSpannable, TextView.BufferType.SPANNABLE);
                    break;
                case 1:
                    ((RadioButton)findViewById(R.id.radio1)).setText(localSpannable, TextView.BufferType.SPANNABLE);
                    break;
                case 2:
                    ((RadioButton)findViewById(R.id.radio2)).setText(localSpannable, TextView.BufferType.SPANNABLE);
                    break;
                case 3:
                    ((RadioButton)findViewById(R.id.radio3)).setText(localSpannable, TextView.BufferType.SPANNABLE);
            }
        }
    }

    public void onBackPressed()
    {
        if (questionNr == 1) {
            super.onBackPressed();
        }
    }

    public void onClick(View paramView)
    {
        switch (paramView.getId())
        {
            default:
                return;
            case R.id.stopQuiz:
                new OkCancelDialog(this, "Stop Quiz", TextConstants.get(R.string.surestop))
                {
                    public void clickOk()
                    {
                        super.clickOk();
                        QuestionActivity.this.finish();
                        Intent localIntent = new Intent(QuestionActivity.this.getApplicationContext(), MainActivity.class);
                        QuestionActivity.this.startActivity(localIntent);
                    }
                }.show();

        }
        questionArray[(-1 + questionNr)] = (this.currentQuestion.getQuestion() + " ?");
        answerArray[(-1 + questionNr)] = this.currentQuestion.getCorrectAnswer();
        if (this.currentQuestion.hasExplanation())
        {
            String[] arrayOfString = answerArray;
            int m = -1 + questionNr;
            arrayOfString[m] = (arrayOfString[m] + "\n [ " + this.currentQuestion.getExplanation() + " ]");
        }
        questionNr = 1 + questionNr;
        int i = ((RadioGroup)findViewById(R.id.radioGroupQuestion)).getCheckedRadioButtonId();
        String str1 = this.currentQuestion.getCorrectAnswer();
        int j = this.currentQuestion.getCorrectAnswerId();
        int k = 0;
        switch (i)
        {
            default:
                if (k != 0)
                {
                    correctArray[(-2 + questionNr)] = true;
                    String str2 = TextConstants.get(2131099689);
                    correctAnswers = 1 + correctAnswers;
                    Toast.makeText(getApplicationContext(), str2, Toast.LENGTH_LONG).show();
                    if (questionNr <= 5)
                    {
                        Intent localIntent = new Intent(this, QuestionActivity.class);
                        finish();
                        startActivity(localIntent);
                    }
                }
                break;
        }
        while ((questionNr > 5) && (k != 0))
        {
            finish();
            startActivity(new Intent(getApplicationContext(), StatusActivity.class));

            if (j == 0) {}
            for (k = 1;; k = 0) {
                break;
            }
            if (j == 1) {}
            for (k = 1;; k = 0) {
                break;
            }
            if (j == 2) {}
            for (k = 1;; k = 0) {
                break;
            }
            if (j == 3) {}
            for (k = 1;; k = 0) {
                break;
            }
            correctArray[(-2 + questionNr)] = false;
            SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(TextConstants.get(2131099693) + " ");
            localSpannableStringBuilder.append(Tools.formatMathSpannable(str1));
            if (this.currentQuestion.hasExplanation())
            {
                localSpannableStringBuilder.append("\n( ");
                localSpannableStringBuilder.append(Tools.formatMathSpannable(this.currentQuestion.getExplanation()));
                localSpannableStringBuilder.append(" )");
            }
            new OkDialog(this, TextConstants.get(R.string.wronganswer), localSpannableStringBuilder)
            {
                public void clickOk()
                {
                    super.clickOk();
                    if (QuestionActivity.questionNr <= 5)
                    {
                        Intent localIntent1 = new Intent(QuestionActivity.this.getApplicationContext(), QuestionActivity.class);
                        QuestionActivity.this.finish();
                        QuestionActivity.this.startActivity(localIntent1);
                        return;
                    }
                    QuestionActivity.this.finish();
                    Intent localIntent2 = new Intent(QuestionActivity.this.getApplicationContext(), StatusActivity.class);
                    QuestionActivity.this.startActivity(localIntent2);
                }
            }.show();
        }
    }

    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        if (category == null) {
            Log.v("QUESTIONACTIVITY: ", "category is null");
        }
        if (paramBundle != null) {
            this.currentQuestion = ((Question)paramBundle.getParcelable("question"));
        }
        if (this.currentQuestion == null) {
            makeQuestionUI(category.getNextQuestion());
        }
        for (;;)
        {
            makeQuestionUI(this.currentQuestion);
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
    }

    protected void onSaveInstanceState(Bundle paramBundle)
    {
        super.onSaveInstanceState(paramBundle);
        paramBundle.putParcelable("question", this.currentQuestion);
    }

    public void onStart()
    {
        super.onStart();
    }

}
