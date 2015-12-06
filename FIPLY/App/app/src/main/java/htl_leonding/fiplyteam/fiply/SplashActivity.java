package htl_leonding.fiplyteam.fiply;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SleepIntentTask sleepIntentTask = new SleepIntentTask();
        sleepIntentTask.execute("");
    }

    public class SleepIntentTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                sleep(3500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent openMain = new Intent("fiply.MAINACTIVITY");
            openMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(openMain);
            return "Success";
        }
    }

}
