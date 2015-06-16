package com.example.dominic.mathquiz;

import java.util.Vector;

/**
 * Created by dominic on 15/06/2015.
 */
public class QuizManager {

    Vector<Category> categories = new Vector();
    Category curCategory;
    int questionsCorrect = 0;
    int questionsTried = 0;

    public void chooseCategory(int paramInt)
    {
        this.curCategory = ((Category)this.categories.get(paramInt));
    }

     public void countQuestion(boolean paramBoolean) {}
}
