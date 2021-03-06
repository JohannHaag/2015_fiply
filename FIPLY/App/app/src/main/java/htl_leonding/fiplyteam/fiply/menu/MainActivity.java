package htl_leonding.fiplyteam.fiply.menu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;
import htl_leonding.fiplyteam.fiply.music.FPlaylist;
import htl_leonding.fiplyteam.fiply.trainingsplan.FPlanManagement;
import htl_leonding.fiplyteam.fiply.trainingssession.FTrainingsSettings;
import htl_leonding.fiplyteam.fiply.uebungskatalog.FUebungskatalog;
import htl_leonding.fiplyteam.fiply.user.FUsermanagement;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    String mActivityTitle;
    String[] navArray = new String[6];
    KeyValueRepository kvr;
    public InterstitialAd mInterstitialAd;

    /**
     * Wird beim Ersten Aufruf der MainActivity aufgerufen und dient dem setzen aller Viewelemente
     *
     * @param savedInstanceState default
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView) findViewById(R.id.navlist);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        Resources res = getResources();
        navArray = res.getStringArray(R.array.navigationArray);

        KeyValueRepository.setContext(this);
        kvr = KeyValueRepository.getInstance();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        requestNewInterstitial();

        /**
         * Bei Drücken eines Elements des NavigationDrawers wird eine FragmentTransaction durchgeführt,
         * in der das Fragment des gedrückten Elements in das FrameLayout der MainActivity geladen wird
         */
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position);
            }
        });

        //Übergibt dem NavigationDrawer die Elemente die er enthalten soll
        addDrawerItems();
        //Konfiguriert den navigationDrawer
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Lädt das FMain Fragment in das FrameLayout der MainActivity
        FMain fMain = new FMain();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fraPlace, fMain).commit();

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        restartNotifications();
    }

    /**
     * Wird nachdem erstellen der View aufgerufen und stellt sicher
     * dass der NavigationDrawer und die Activity synchron sind
     *
     * @param savedInstanceState default
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    /**
     * Teilt der DrawerNavigation mit wenn sich die Configuration ändert
     *
     * @param newConfig default
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Übergibt dem NavigationDrawer die Elemente die er enthalten soll
     */
    private void addDrawerItems() {
        mAdapter = new ArrayAdapter<>(this, R.layout.navigation_list, R.id.navlist_content, navArray);
        mDrawerList.setAdapter(mAdapter);
    }

    /**
     * Konfiguriert den NavigationDrawer und behandelt das Verhalten beim öffnen und schließen
     */
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /**
             * Behandelt das Verhalten beim Öffnen des NavigationDrawers
             *
             * @param drawerView default
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu();
            }

            /**
             * Behandelt das Verhalten beim Schließen des NavigationDrawers
             * @param view default
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    /**
     * Teilt dem NavigationDrawer mit wenn ein Item gefrückt wird
     *
     * @param item Fragment das ins FrameLayout der MainActivity geladen werden soll
     * @return Fragment das ins FrameLayout der MainActivity geladen werden soll
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * Lädt das Fragment, dass sich an der jeweiligen Position in der Liste befindet, in das FrameLayout der MainActivity
     *
     * @param position Position in der Liste des NavigationDrawers
     */
    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FMain();
                break;
            case 1:
                fragment = new FTrainingsSettings();
                break;
            case 2:
                fragment = new FUsermanagement();
                break;
            case 3:
                fragment = new FPlanManagement();
                break;
            case 4:
                fragment = new FUebungskatalog();
                break;
            case 5:
                fragment = new FPlaylist();
                break;
            case 6:
                fragment = new FAppSettings();
                break;
            default:
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fügt dieses Fragment zum Backstack hinzu, somit kann man bei drücken des BackButtons darauf zurückspringen
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fraPlace, fragment);
        fragmentTransaction.commit();

        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    /**
     * Eine neue Interstitial-Ad mit passenden Eigenschaften wird angefordert (aber noch nicht angezeigt)
     */
    public void requestNewInterstitial() {
        int gender;
        try {
            gender = kvr.getGender();
        } catch (SQLException e) {
            gender = AdRequest.GENDER_UNKNOWN;
        }
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("BAD0FD4427FB4415FF62FD7B3D3B655D")
                .setGender(gender)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    /**
     * Dieser Listener überprüft ob sich die Einstellungen bezüglich Notifications geändert haben
     * @param sharedPreferences die jeweilige Preference
     * @param key der Name der geänderten Preference
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("notificationInterval")) {
            restartNotifications();
            Toast.makeText(this, "Änderungen der Zeitspanne zwischen den Erinnerungen gespeichert!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Die Notifications werden gestartet bzw. neu gestarted.
     * Diese erfolgen in dem in den Preferences gesetzten Intervall.
     * Quelle: http://it-ride.blogspot.co.at/2010/10/android-implementing-notification.html
     */
    private void restartNotifications() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int minutes = Integer.valueOf(prefs.getString("notificationInterval", "0"));

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, NotificationService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        am.cancel(pi);

        // by my own convention, minutes <= 0 means notifications are disabled
        if (minutes > 0) {
            am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + minutes * 60 * 1000,
                    minutes * 60 * 1000, pi);
        }
    }
}
