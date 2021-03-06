package htl_leonding.fiplyteam.fiply.menu;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.music.FPlaylist;
import htl_leonding.fiplyteam.fiply.statistic.FStatistic;
import htl_leonding.fiplyteam.fiply.trainingsplan.FPlanManagement;
import htl_leonding.fiplyteam.fiply.trainingssession.FTrainingsSettings;
import htl_leonding.fiplyteam.fiply.uebungskatalog.FUebungskatalog;
import htl_leonding.fiplyteam.fiply.user.FUsermanagement;

public class FMain extends Fragment {
    Button startUeb;
    Button startTra;
    Button startUse;
    Button startPlaylists;
    Button startTrainplan;
    Button startStatistic;
    Button startSettings;

    /**
     * Lädt das fragment_main in das FrameLayout der MainActivity
     *
     * @param inflater           default
     * @param container          default
     * @param savedInstanceState default
     * @return View die ins FrameLayout geladen wird
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    /**
     * Wird aufgerufen nachdem die View aufgebaut ist und dient dem setzen der OnClickListener
     *
     * @param view               default
     * @param savedInstanceState default
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startUeb = (Button) getActivity().findViewById(R.id.btStartUe);
        startTra = (Button) getActivity().findViewById(R.id.btStartTr);
        startUse = (Button) getActivity().findViewById(R.id.btStartEU);
        startPlaylists = (Button) getActivity().findViewById(R.id.btStartPlaylist);
        startTrainplan = (Button) getActivity().findViewById(R.id.btStartTrainView);
        startStatistic = (Button) getActivity().findViewById(R.id.btStartStatistic);
        startSettings = (Button) getActivity().findViewById(R.id.btStartSettings);

        /**
         * Bei Drücken des Uebungskatalog Buttons wird eine FragmentTransaction durchgeführt,
         * in der das Fragment des Uebungskatalog in das FrameLayout der MainActivity geladen wird
         */
        startUeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FUebungskatalog fUebungskatalog = new FUebungskatalog();
                displayView(fUebungskatalog);
            }
        });

        /**
         * Bei Drücken des Trainingssession Buttons wird eine FragmentTransaction durchgeführt,
         * in der das Fragment der Trainingssession in das FrameLayout der MainActivity geladen wird
         */
        startTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FTrainingsSettings fTrainingsSettings = new FTrainingsSettings();
                displayView(fTrainingsSettings);
            }
        });

        /**
         * Bei Drücken des Usererstellung Buttons wird eine FragmentTransaction durchgeführt,
         * in der das Fragment der Usererstellung in das FrameLayout der MainActivity geladen wird
         */
        startUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FUsermanagement fUsererstellung = new FUsermanagement();
                displayView(fUsererstellung);
            }
        });

        /**
         * Bei Drücken des Usererstellung Buttons wird eine FragmentTransaction durchgeführt,
         * in der das Fragment der Playlistverwaltung in das FrameLayout der MainActivity geladen wird
         */
        startPlaylists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FPlaylist fPlaylist = new FPlaylist();
                displayView(fPlaylist);
            }
        });

        /**
         * Bei Drücken des Trainingsplanview Buttons wird eine FragmentTransaction durchgeführt,
         * in der das Fragment der Trainingsplanview in das FrameLayout der MainActivity geladen wird
         */
        startTrainplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FPlanManagement fTrainManage = new FPlanManagement();
                displayView(fTrainManage);
            }
        });

        /**
         * Bei Drücken des Statisitc Buttons wird eine FragmentTransaction durchgeführt,
         * in der das Fragment der Statisitc in das FrameLayout der MainActivity geladen wird
         */
        startStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FStatistic fStatistic = new FStatistic();
                displayView(fStatistic);

            }
        });

        /**
         * Bei Drücken des Settings Buttons wird eine FragmentTransaction durchgeführt,
         * in der das Fragment der Settings in das FrameLayout der MainActivity geladen wird
         */
        startSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fraPlace, new FAppSettings())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    /**
     * Ersetzt das FrameLayout der MainActivity mit dem übergebenem Fragment
     *
     * @param fragment Fragment das in das FrameLayout geladen werden soll
     */
    private void displayView(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fügt dieses Fragment zum Backstack hinzu, somit kann man bei drücken des BackButtons darauf zurückspringen
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fraPlace, fragment);
        fragmentTransaction.commit();
    }
}
