package htl_leonding.fiplyteam.fiply.trainingssession;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.music.FMusic;

public class FTrainingssession extends Fragment {

    /**
     * Lädt das fragment_trainingssession in das FrameLayout der MainActivity
     * Diese Klasse lädt alle Kindfragmente und zeigt diese in sich an.
     *
     * @param inflater           default
     * @param container          default
     * @param savedInstanceState default
     * @return View die ins FrameLayout geladen wird
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FTrainingsinstructions fragmentUebung = new FTrainingsinstructions();
        fragmentUebung.setArguments(getArguments());

        displayFragment.displayTSInstruktion(fragmentUebung, getFragmentManager());
        displayFragment.displayTSClocksNav(new FClocksNav(), getFragmentManager());
        displayFragment.displayTSClock(new FWatch(), getFragmentManager());
        displayFragment.displayTSMusic(new FMusic(), getFragmentManager());

        return inflater.inflate(R.layout.fragment_trainingssession, container, false);
    }
}
