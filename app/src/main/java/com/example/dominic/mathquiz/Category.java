package com.example.dominic.mathquiz;

import android.util.Log;

import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by dominic on 15/06/2015.
 */
public class Category {
    private int currentQuestion = 0;
    private int id = -1;
    Vector<Question> questions = new Vector();

    public static Category parseCategoryFromData(StringBuilder paramStringBuilder)
    {
        Category localCategory = new Category();
        int i = 0;
        int k;
        for (int j = 1;; j = 0)
        {
            if ((i >= paramStringBuilder.length()) || (j == 0))
            {
                localCategory.randomizeQuestions();
                return localCategory;
            }
            k = paramStringBuilder.indexOf(":", i);
            Log.v("CATEGORY_INDEX:",Integer.toString(i));
            Log.v("CATEGORY_NEXT:",Integer.toString(k));
            if (k != -1) {
                break;
            }
        }
        Question localQuestion = new Question(paramStringBuilder.substring(i, k).trim());
        int m = k + 1;
        int n = paramStringBuilder.indexOf("?", m);
        Log.v("CATEGORY_INDEX2:",Integer.toString(m));
        Log.v("CATEGORY_NEXT2:",Integer.toString(n));
        StringTokenizer localStringTokenizer1 = new StringTokenizer(paramStringBuilder.substring(m, n).trim(), "|");
        for (;;)
        {
            if (!localStringTokenizer1.hasMoreElements())
            {
                int i1 = n + 1;
                int i2 = paramStringBuilder.indexOf("?", i1);
                StringTokenizer localStringTokenizer2 = new StringTokenizer(paramStringBuilder.substring(i1, i2).trim(), "[]");
                localQuestion.setCorrect(new Integer(localStringTokenizer2.nextToken().trim()).intValue());
                if (localStringTokenizer2.hasMoreElements()) {
                    localQuestion.setExplanation(localStringTokenizer2.nextToken().trim());
                }
                i = i2 + 1;
                localQuestion.randomize();
                localCategory.addQuestion(localQuestion);
                break;
            }
            localQuestion.AddAnswer(localStringTokenizer1.nextToken().trim());
        }
    return localCategory;
    }

    public void addQuestion(Question paramQuestion)
    {
        this.questions.add(paramQuestion);
    }

    public int getID()
    {
        return this.id;
    }

    public Question getNextQuestion()
    {
        Question localQuestion = (Question)this.questions.elementAt(this.currentQuestion);
        if (this.currentQuestion < -1 + size()) {
            this.currentQuestion = (1 + this.currentQuestion);
        }
        return localQuestion;
    }

    public Question getRandomQuestion()
    {
        int i = new Random().nextInt(size());
        return (Question)this.questions.get(i);
    }

    public void randomizeQuestions()
    {
        this.currentQuestion = 0;
        int i = Math.min(size(), 5);
        for (int j = 0;; j++)
        {
            if (j >= i) {
                return;
            }
            int k = new Random().nextInt(size() - j);
            Collections.swap(this.questions, j, j + k);
        }
    }

    public void setID(int paramInt)
    {
        this.id = paramInt;
    }

    public int size()
    {
        return this.questions.size();
    }

    public String toString()
    {
        String str = "";
        Iterator localIterator = this.questions.iterator();
        for (;;)
        {
            if (!localIterator.hasNext()) {
                return str;
            }
            str = str + ((Question)localIterator.next()).toString() + "\n\n";
        }
    }
}
