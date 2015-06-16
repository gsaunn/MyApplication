package com.example.dominic.mathquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;



public class MainActivity extends ActionBarActivity {
    private int category= -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }
    public void onClick(View view)
    {
        switch (view.getId())
        {
            default:
                return;
            case R.id.about_button:
                startActivity(new Intent(MainActivity.this, About.class));
                break;
            case R.id.highscore_button:
                startActivity(new Intent(MainActivity.this, HighScore.class));
                break;
            case R.id.exit_button:
                finish();
                break;
            case R.id.invite_button:
                startActivity(new Intent(MainActivity.this,InviteFriend.class));
                break;
            case R.id.help_button:
                startActivity(new Intent(MainActivity.this,Help.class));
                break;
            case R.id.new_button:
                startNewQuiz();
                break;

        }

    }

    public void onDestroy()
    {
        super.onDestroy();
    }

    protected void onStart()
    {
        super.onStart();
    }

    protected void onStop()
    {
       super.onStop();
    }

    public void startNewQuiz()
    {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle(R.string.category);
        localBuilder.setItems(R.array.categoryzer, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

                MainActivity.this.startQuiz(paramAnonymousInt);
            }
        });
         localBuilder.show();
    }

    public void startQuiz(int paramInt) {
        if ((paramInt == 0)||(paramInt == 1)) {
            Resources localResources = getResources();
            Category localCategory = Category.parseCategoryFromData(Tools.readCategory(Tools.ToResource(paramInt), localResources));
            localCategory.setID(paramInt);
            Log.d("CATEGORYSIZE: ", "SIZE = " + localCategory.size());
            QuestionActivity.setCategory(localCategory);
            Intent localIntent = new Intent(this, QuestionActivity.class);
            super.finish();
            startActivity(localIntent);
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
