package com.example.dominic.mathquiz;

/**
 * Created by dominic on 14/06/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HighScore extends Activity implements View.OnClickListener {

    private static DataBase db;
    boolean checkedGlobalDone = false;
    Runnable drawGlobalScoreTask = new Runnable()
    {
        public void run()
        {
            Log.d("MY THREAD", "DRAWING THE GLOBAL _HIGHSCORE_ AT " + System.currentTimeMillis());
            HighScore.this.drawGlobalHighScore(HighScore.this.info);
        }
    };
    Handler guiThread;
    ArrayList<GlobalScoreInfo> info;
    String space = "  ";

    public static boolean addNewGlobalScore(Context paramContext, int paramInt1, int paramInt2, String paramString)
    {
        String str1 = paramContext.getResources().getConfiguration().locale.getDisplayCountry().replace(' ', '+');
        String str2 = paramString.replace(' ', '+');
        Log.d("GLOBALHIGHSCORE", " country = " + str1);
        try
        {
            String str3 = executeHttpGet("http://pondar.dk/php/updateglobalhighscores.php?update=1&category=" + paramInt1 + "&score=" + paramInt2 + "&name=" + str2 + "&location=" + str1).trim();
            Log.d("GLOBALHIGHSCORE", " result from addnewglobal = " + str3);
            boolean bool1 = str3.equals("true");
            boolean bool2 = false;
            if (bool1) {
                bool2 = true;
            }
            return bool2;
        }
        catch (Exception localException)
        {
            Log.d("GLOBALHIGHSCORE", " exception = " + localException.getMessage());
        }
        return false;
    }

    public static boolean addNewScore(Context paramContext, int paramInt1, int paramInt2)
    {
        db = new DataBase(paramContext);
        SQLiteDatabase localSQLiteDatabase = db.getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT score FROM scores where category = " + paramInt1, null);
        localCursor.moveToNext();
        if (paramInt2 > localCursor.getInt(0))
        {
            localSQLiteDatabase.execSQL("UPDATE scores SET score = " + paramInt2 + " WHERE category = " + paramInt1);
            localCursor.close();
            localSQLiteDatabase.close();
            return true;
        }
        localCursor.close();
        localSQLiteDatabase.close();
        return false;
    }

    public static boolean checkForGlobalScore(int paramInt1, int paramInt2)
    {
        try
        {
            String str = executeHttpGet("http://pondar.dk/php/updateglobalhighscores.php?update=0&category=" + paramInt1 + "&score=" + paramInt2);
            Log.d("GLOBALHIGHSCORE", str);
            if (str.trim().equals("true"))
            {
                Log.d("GLOBALHIGHSCORE", "result er true");
                return true;
            }
            Log.d("GLOBALHIGHSCORE", "result er false");
            return false;
        }
        catch (Exception localException)
        {
            Log.d("GLOBALHIGHSCORE", "Exception");
        }
        return false;
    }

    private void drawGlobalHighScore(ArrayList<GlobalScoreInfo> paramArrayList)
    {
        if (paramArrayList == null)
        {
            ((TextView)findViewById(R.id.highscore_view)).append("\n\n" + TextConstants.get(R.string.nointernet) + "\n\n");
            return;
        }
        TableLayout localTableLayout = (TableLayout)findViewById(R.id.highscoretable_global);
        Iterator localIterator = paramArrayList.iterator();
        TableRow localTableRow1 = new TableRow(this);
        TextView localTextView1 = new TextView(this);
        localTextView1.setText(TextConstants.get(R.string.category));
        localTableRow1.addView(localTextView1);
        TextView localTextView2 = new TextView(this);
        localTextView2.setText(this.space + TextConstants.get(R.string.score));
        localTableRow1.addView(localTextView2);
        TextView localTextView3 = new TextView(this);
        localTextView3.setText(this.space + TextConstants.get(R.string.name));
        localTableRow1.addView(localTextView3);
        TextView localTextView4 = new TextView(this);
        localTextView4.setText(this.space + TextConstants.get(R.string.location));
        localTableRow1.addView(localTextView4);
        localTableLayout.addView(localTableRow1);
        if (!localIterator.hasNext())
        {
            TextView localTextView5 = new TextView(this);
            localTextView5.setText("\n");
            localTableLayout.addView(localTextView5);
            return;
        }
        TableRow localTableRow2 = new TableRow(this);
        GlobalScoreInfo localGlobalScoreInfo = (GlobalScoreInfo)localIterator.next();
        Log.d("GLOBAL HIGSCORE", " info = " + localGlobalScoreInfo.toString());
        TextView localTextView6 = new TextView(this);
        localTextView6.setText(Tools.ToName(localGlobalScoreInfo.getCategory(), getResources()));
        localTableRow2.addView(localTextView6);
        TextView localTextView7 = new TextView(this);
        localTextView7.setText(this.space + localGlobalScoreInfo.getScore());
        localTableRow2.addView(localTextView7);
        TextView localTextView8 = new TextView(this);
        if (localGlobalScoreInfo.getName().length() <= 10) {
            localTextView8.setText(this.space + localGlobalScoreInfo.getName());
        }
        for (;;)
        {
            localTableRow2.addView(localTextView8);
            TextView localTextView9 = new TextView(this);
            localTextView9.setText(this.space + localGlobalScoreInfo.getLocation());
            localTableRow2.addView(localTextView9);
            localTableLayout.addView(localTableRow2);
            localTextView8.setText(this.space + localGlobalScoreInfo.getName().substring(0, 10));
        }
    }

    /* Error */
    public static String executeHttpGet(String paramString)
            throws Exception
    {
        // Byte code:
        //   0: new 290	org/apache/http/impl/client/DefaultHttpClient
        //   3: dup
        //   4: invokespecial 291	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
        //   7: astore_1
        //   8: new 293	org/apache/http/client/methods/HttpGet
        //   11: dup
        //   12: invokespecial 294	org/apache/http/client/methods/HttpGet:<init>	()V
        //   15: astore_2
        //   16: aload_2
        //   17: new 296	java/net/URI
        //   20: dup
        //   21: aload_0
        //   22: invokespecial 297	java/net/URI:<init>	(Ljava/lang/String;)V
        //   25: invokevirtual 301	org/apache/http/client/methods/HttpGet:setURI	(Ljava/net/URI;)V
        //   28: new 303	java/io/BufferedReader
        //   31: dup
        //   32: new 305	java/io/InputStreamReader
        //   35: dup
        //   36: aload_1
        //   37: aload_2
        //   38: invokeinterface 311 2 0
        //   43: invokeinterface 317 1 0
        //   48: invokeinterface 323 1 0
        //   53: invokespecial 326	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
        //   56: invokespecial 329	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
        //   59: astore 6
        //   61: new 331	java/lang/StringBuffer
        //   64: dup
        //   65: ldc_w 333
        //   68: invokespecial 334	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
        //   71: astore 7
        //   73: ldc_w 336
        //   76: invokestatic 341	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
        //   79: astore 8
        //   81: aload 6
        //   83: invokevirtual 344	java/io/BufferedReader:readLine	()Ljava/lang/String;
        //   86: astore 9
        //   88: aload 9
        //   90: ifnonnull +28 -> 118
        //   93: aload 6
        //   95: invokevirtual 345	java/io/BufferedReader:close	()V
        //   98: aload 7
        //   100: invokevirtual 346	java/lang/StringBuffer:toString	()Ljava/lang/String;
        //   103: astore 10
        //   105: aload 6
        //   107: ifnull +8 -> 115
        //   110: aload 6
        //   112: invokevirtual 345	java/io/BufferedReader:close	()V
        //   115: aload 10
        //   117: areturn
        //   118: aload 7
        //   120: new 92	java/lang/StringBuilder
        //   123: dup
        //   124: aload 9
        //   126: invokestatic 237	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
        //   129: invokespecial 97	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   132: aload 8
        //   134: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   137: invokevirtual 104	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   140: invokevirtual 349	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   143: pop
        //   144: goto -63 -> 81
        //   147: astore_3
        //   148: aload 6
        //   150: astore 4
        //   152: aload 4
        //   154: ifnull +8 -> 162
        //   157: aload 4
        //   159: invokevirtual 345	java/io/BufferedReader:close	()V
        //   162: aload_3
        //   163: athrow
        //   164: astore 11
        //   166: aload 11
        //   168: invokevirtual 352	java/io/IOException:printStackTrace	()V
        //   171: aload 10
        //   173: areturn
        //   174: astore 5
        //   176: aload 5
        //   178: invokevirtual 352	java/io/IOException:printStackTrace	()V
        //   181: goto -19 -> 162
        //   184: astore_3
        //   185: aconst_null
        //   186: astore 4
        //   188: goto -36 -> 152
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	191	0	paramString	String
        //   7	30	1	localDefaultHttpClient	DefaultHttpClient
        //   15	23	2	localHttpGet	org.apache.http.client.methods.HttpGet
        //   147	16	3	localObject1	Object
        //   184	1	3	localObject2	Object
        //   150	37	4	localBufferedReader1	java.io.BufferedReader
        //   174	3	5	localIOException1	java.io.IOException
        //   59	90	6	localBufferedReader2	java.io.BufferedReader
        //   71	48	7	localStringBuffer	java.lang.StringBuffer
        //   79	54	8	str1	String
        //   86	39	9	str2	String
        //   103	69	10	str3	String
        //   164	3	11	localIOException2	java.io.IOException
        // Exception table:
        //   from	to	target	type
        //   61	81	147	finally
        //   81	88	147	finally
        //   93	105	147	finally
        //   118	144	147	finally
        //   110	115	164	java/io/IOException
        //   157	162	174	java/io/IOException
        //   0	61	184	finally
        return paramString;
    }

    private ArrayList<GlobalScoreInfo> getGlobalHighscore()
    {
        long l = System.currentTimeMillis();
        ArrayList localArrayList = getHighScoreFromServer();
        Log.d("PROFILING gethighscorefromserver", String.valueOf(System.currentTimeMillis() - l));
        return localArrayList;
    }

    private ArrayList<GlobalScoreInfo> getHighScoreFromServer()
    {
        try
        {
            long l = System.currentTimeMillis();
            String str = executeHttpGet("http://pondar.dk/php/getglobalhighscores.php");
            Log.d("PROFILING executeHTTPGET", String.valueOf(System.currentTimeMillis() - l));
            ArrayList localArrayList = parseFromGlobalServer(str);
            return localArrayList;
        }
        catch (Exception localException)
        {
            Log.d("GLOBALHIGHSCORE EXC", localException.getMessage());
        }
        return null;
    }

    private ArrayList<GlobalScoreInfo> parseFromGlobalServer(String paramString)
    {
        ArrayList localArrayList = new ArrayList();
        if (paramString == null) {
            localArrayList = null;
        }
        for (;;)
        {

            Log.d("GLOBALHIGHSCORE_INPUT", paramString);
            StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString.trim(), ";");
            while (localStringTokenizer1.hasMoreTokens())
            {
                StringTokenizer localStringTokenizer2 = new StringTokenizer(localStringTokenizer1.nextToken(), ",");
                localArrayList.add(new GlobalScoreInfo(new Integer(localStringTokenizer2.nextToken()).intValue(),
                        new Integer(localStringTokenizer2.nextToken()).intValue(), localStringTokenizer2.nextToken(),
                        localStringTokenizer2.nextToken()));
            }
            return localArrayList;
        }
    }

    /* Error */
    private void writeLocalHighScoresToUI()
    {
        // Byte code:
        //   0: new 145	org/pondar/mathquizfree/DataBase
        //   3: dup
        //   4: aload_0
        //   5: invokespecial 148	org/pondar/mathquizfree/DataBase:<init>	(Landroid/content/Context;)V
        //   8: putstatic 150	org/pondar/mathquizfree/HighScore:db	Lorg/pondar/mathquizfree/DataBase;
        //   11: aload_0
        //   12: invokevirtual 408	org/pondar/mathquizfree/HighScore:getLocalHighScores	()Landroid/database/Cursor;
        //   15: astore_2
        //   16: aload_0
        //   17: aload_2
        //   18: invokevirtual 412	org/pondar/mathquizfree/HighScore:startManagingCursor	(Landroid/database/Cursor;)V
        //   21: aload_0
        //   22: ldc_w 413
        //   25: invokevirtual 198	org/pondar/mathquizfree/HighScore:findViewById	(I)Landroid/view/View;
        //   28: checkcast 215	android/widget/TableLayout
        //   31: astore_3
        //   32: aload_3
        //   33: invokevirtual 416	android/widget/TableLayout:removeAllViews	()V
        //   36: aload_2
        //   37: invokeinterface 168 1 0
        //   42: istore 4
        //   44: iload 4
        //   46: ifne +10 -> 56
        //   49: getstatic 150	org/pondar/mathquizfree/HighScore:db	Lorg/pondar/mathquizfree/DataBase;
        //   52: invokevirtual 417	org/pondar/mathquizfree/DataBase:close	()V
        //   55: return
        //   56: new 223	android/widget/TableRow
        //   59: dup
        //   60: aload_0
        //   61: invokespecial 224	android/widget/TableRow:<init>	(Landroid/content/Context;)V
        //   64: astore 5
        //   66: aload_2
        //   67: iconst_0
        //   68: invokeinterface 172 2 0
        //   73: istore 6
        //   75: aload_2
        //   76: iconst_1
        //   77: invokeinterface 172 2 0
        //   82: istore 7
        //   84: new 200	android/widget/TextView
        //   87: dup
        //   88: aload_0
        //   89: invokespecial 225	android/widget/TextView:<init>	(Landroid/content/Context;)V
        //   92: astore 8
        //   94: aload 8
        //   96: iload 6
        //   98: aload_0
        //   99: invokevirtual 264	org/pondar/mathquizfree/HighScore:getResources	()Landroid/content/res/Resources;
        //   102: invokestatic 270	org/pondar/mathquizfree/CommonTools:categoryToName	(ILandroid/content/res/Resources;)Ljava/lang/String;
        //   105: invokevirtual 229	android/widget/TextView:setText	(Ljava/lang/CharSequence;)V
        //   108: aload 5
        //   110: aload 8
        //   112: invokevirtual 233	android/widget/TableRow:addView	(Landroid/view/View;)V
        //   115: new 200	android/widget/TextView
        //   118: dup
        //   119: aload_0
        //   120: invokespecial 225	android/widget/TextView:<init>	(Landroid/content/Context;)V
        //   123: astore 9
        //   125: aload 9
        //   127: new 92	java/lang/StringBuilder
        //   130: dup
        //   131: aload_0
        //   132: getfield 29	org/pondar/mathquizfree/HighScore:space	Ljava/lang/String;
        //   135: invokestatic 237	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
        //   138: invokespecial 97	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   141: iload 7
        //   143: invokevirtual 115	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   146: ldc_w 419
        //   149: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   152: ldc_w 420
        //   155: invokestatic 209	org/pondar/mathquizfree/TextConstants:get	(I)Ljava/lang/String;
        //   158: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   161: ldc_w 419
        //   164: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   167: iconst_5
        //   168: invokevirtual 115	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   171: invokevirtual 104	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   174: invokevirtual 229	android/widget/TextView:setText	(Ljava/lang/CharSequence;)V
        //   177: aload 5
        //   179: aload 9
        //   181: invokevirtual 233	android/widget/TableRow:addView	(Landroid/view/View;)V
        //   184: aload_3
        //   185: aload 5
        //   187: invokevirtual 241	android/widget/TableLayout:addView	(Landroid/view/View;)V
        //   190: goto -154 -> 36
        //   193: astore_1
        //   194: getstatic 150	org/pondar/mathquizfree/HighScore:db	Lorg/pondar/mathquizfree/DataBase;
        //   197: invokevirtual 417	org/pondar/mathquizfree/DataBase:close	()V
        //   200: aload_1
        //   201: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	202	0	this	HighScore
        //   193	8	1	localObject	Object
        //   15	61	2	localCursor	Cursor
        //   31	154	3	localTableLayout	TableLayout
        //   42	3	4	bool	boolean
        //   64	122	5	localTableRow	TableRow
        //   73	24	6	i	int
        //   82	60	7	j	int
        //   92	19	8	localTextView1	TextView
        //   123	57	9	localTextView2	TextView
        // Exception table:
        //   from	to	target	type
        //   11	36	193	finally
        //   36	44	193	finally
        //   56	190	193	finally
    }

    public Cursor getLocalHighScores()
    {
        return db.getReadableDatabase().rawQuery("SELECT category,score FROM scores ORDER BY category", null);
    }

    public void initThreading()
    {
        this.guiThread = new Handler();
        Runnable local2 = new Runnable()
        {
            public void run()
            {
                Log.d("MY THREAD", "CHECKING THE GLOBAL _HIGHSCORE AT " + System.currentTimeMillis());
                HighScore.this.info = HighScore.this.getGlobalHighscore();
                Log.d("MY THREAD", "DONE CHECKING THE GLOBAL _HIGHSCORE AT " + System.currentTimeMillis());
                HighScore.this.checkedGlobalDone = true;
                HighScore.this.guiThread.post(HighScore.this.drawGlobalScoreTask);
            }
        };
        Executors.newSingleThreadExecutor().execute(local2);
        Toast.makeText(getApplicationContext(), TextConstants.get(R.string.highscore_loading),Toast.LENGTH_SHORT).show();
    }

    public void onClick(View paramView)
    {
        switch (paramView.getId())
        {
            default:
                return;
            case R.id.highScoreReset:
                new OkCancelDialog(this, "Reset Highscores", TextConstants.get(R.string.resetHighscoreConfirmation))
                {
                    public void clickOk()
                    {
                        super.clickOk();
                        long l1 = System.currentTimeMillis();
                        HighScore.this.resetLocalHighScores();
                        long l2 = System.currentTimeMillis();
                        HighScore.this.writeLocalHighScoresToUI();
                        long l3 = System.currentTimeMillis();
                        Log.d("PROFILING reset",String.valueOf( l2 - l1));
                        Log.d("PROFILING writeToUI",String.valueOf(l3 - l2));
                        Toast.makeText(HighScore.this.getApplicationContext(), TextConstants.get(R.string.highscorereset_done), Toast.LENGTH_LONG).show();
                    }
                }.show();

        }
        finish();
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.highscore_new);
        ((TextView)findViewById(R.id.highscore_view)).append(" ( max 5 points )");
        ((Button)findViewById(R.id.highScoreReset)).setOnClickListener(this);
        ((Button)findViewById(R.id.highScoreBack)).setOnClickListener(this);
        if (paramBundle != null) {
            this.checkedGlobalDone = paramBundle.getBoolean("checkedGlobalDone");
        }
        if (!this.checkedGlobalDone) {
            initThreading();
        }
        for (;;)
        {
            writeLocalHighScoresToUI();
            Log.d("THREADING", "ONCREATE IS DONE at " + System.currentTimeMillis());
            this.info = paramBundle.getParcelableArrayList("info");
            drawGlobalHighScore(this.info);
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
    }

    protected void onSaveInstanceState(Bundle paramBundle)
    {
        super.onSaveInstanceState(paramBundle);
        paramBundle.putBoolean("checkedGlobalDone", this.checkedGlobalDone);
        paramBundle.putParcelableArrayList("info", this.info);
    }

    public void resetLocalHighScores()
    {
        SQLiteDatabase localSQLiteDatabase = db.getReadableDatabase();
        localSQLiteDatabase.execSQL("UPDATE scores SET score=0 WHERE category >-1");
        localSQLiteDatabase.close();
    }

    public void sendPostData(String paramString1, String paramString2)
    {
        DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
        HttpPost localHttpPost = new HttpPost(paramString1);
        try
        {
            ArrayList localArrayList = new ArrayList(1);
            localArrayList.add(new BasicNameValuePair("your_form_name", paramString2));
            localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList));
            localDefaultHttpClient.execute(localHttpPost).getStatusLine().getStatusCode();
            return;
        }
        catch (Exception localException) {}
    }
}
