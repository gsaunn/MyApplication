package com.example.dominic.mathquiz;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import static java.util.Collections.*;

/**
 * Created by dominic on 15/06/2015.
 */
public class Question implements Parcelable  {
    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator()
    {
        public Question createFromParcel(Parcel paramAnonymousParcel)
        {
            return new Question(paramAnonymousParcel);
        }

        public Question[] newArray(int paramAnonymousInt)
        {
            return new Question[paramAnonymousInt];
        }
    };
    private Vector<String> answers;
    private int correct = -1;
    private String explanation = "";
    private String question;

    public Question(Parcel paramParcel)
    {
        this.question = paramParcel.readString();
        this.correct = paramParcel.readInt();
        this.explanation = paramParcel.readString();
        this.answers = ((Vector)paramParcel.readValue(null));
    }

    public Question(String paramString)
    {
        this.question = paramString;
        this.answers = new Vector();
    }

    public Question(String paramString, Vector<String> paramVector)
    {
        this.question = paramString;
        this.answers = paramVector;
    }

    public Question(String paramString, Vector<String> paramVector, int paramInt)
    {
        this.question = paramString;
        this.answers = paramVector;
        this.correct = paramInt;
    }

    public void AddAnswer(String paramString)
    {
        this.answers.add(paramString);
    }

    public int describeContents()
    {
        return 0;
    }

    public Iterator<String> getAllAnswers()
    {
        return this.answers.iterator();
    }

    public String getAnswer(int paramInt)
    {
        if (paramInt < this.answers.size()) {
            return (String)this.answers.get(paramInt);
        }
        return "There is no answer in this question with index: " + paramInt;
    }

    public String getCorrectAnswer()
    {
        if (this.correct != -1) {
            return (String)this.answers.get(this.correct);
        }
        return "Question not initialized correctly";
    }

    public int getCorrectAnswerId()
    {
        return this.correct;
    }

    public String getExplanation()
    {
        return this.explanation;
    }

    public String getQuestion()
    {
        return this.question;
    }

    public boolean hasExplanation()
    {
        return !this.explanation.equals("");
    }

    public boolean isCorrect(int paramInt)
    {
        return paramInt == this.correct;
    }

    public void randomize()
    {
        int i = this.answers.size();
        int j = 0;
        if (j >= i - 1) {
            return;
        }
        int k = new Random().nextInt(i - j);
        if (j == this.correct) {
            this.correct = (j + k);
        }
        for (;;)
        {
            Collections.swap(this.answers, j, j+k);
            j++;

            if (j + k == this.correct) {
                this.correct = j;
            }
        }
    }

    public void setCorrect(int paramInt)
    {
        this.correct = paramInt;
    }

    public void setExplanation(String paramString)
    {
        this.explanation = paramString;
    }

    public void setQuestion(String paramString)
    {
        this.question = paramString;
    }

    public String toString()
    {
        String str = this.question + " : \n";
        Iterator localIterator = this.answers.iterator();
        for (;;)
        {
            if (!localIterator.hasNext()) {
                return str + "? " + this.correct + " [ " + this.explanation + " ] ";
            }
            str = str + ((String)localIterator.next()).toString() + "\n";
        }
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeString(this.question);
        paramParcel.writeInt(this.correct);
        paramParcel.writeString(this.explanation);
        paramParcel.writeValue(this.answers);
    }
}
