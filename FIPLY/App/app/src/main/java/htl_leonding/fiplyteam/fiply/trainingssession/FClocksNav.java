package htl_leonding.fiplyteam.fiply.trainingssession;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import htl_leonding.fiplyteam.fiply.R;

public class FClocksNav extends Fragment {

    Button btnNavWatch, btnNavTimer;

    /**
     * Hier wird das fragment_clocksnav fragment angezeigt
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_clocksnav, container, false);
    }

    /**
     * Hier werden alle ViewElemente und Listener gesetzt
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnNavWatch = (Button) getActivity().findViewById(R.id.btnNavWatch);
        btnNavTimer = (Button) getActivity().findViewById(R.id.btnNavTimer);

        btnNavWatch.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkPrimary));
        btnNavTimer.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkSecondary));

        btnNavWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFragment.displayTSClock(new FWatch(), getFragmentManager());
                btnNavWatch.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkPrimary));
                btnNavTimer.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkSecondary));
            }
        });
        btnNavTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFragment.displayTSClock(new FCountdown(), getFragmentManager());
                btnNavWatch.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkSecondary));
                btnNavTimer.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkPrimary));
            }
        });
    }
}
