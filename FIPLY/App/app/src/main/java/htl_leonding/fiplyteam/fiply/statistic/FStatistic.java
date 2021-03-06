package htl_leonding.fiplyteam.fiply.statistic;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.math.RoundingMode;
import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;
import htl_leonding.fiplyteam.fiply.data.StatisticRepository;

/**
 * Created by Gerald on 11/02/2016.
 */
public class FStatistic extends Fragment {
    //repositories
    KeyValueRepository kvr;
    StatisticRepository str;

    //layout elements
    TextView tvStatisticGreeting;
    GraphView gvMood;
    GraphView gvLift;

    //series of datapoints
    LineGraphSeries<WeightLifted> weightLiftedSeries;


    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }

    /**
     * Initialisiert die Layout Elemente und setzt die entsprechenden Werte.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        gvMood = (GraphView) getActivity().findViewById(R.id.graphMood);
        gvLift = (GraphView) getActivity().findViewById(R.id.graphLift);
        tvStatisticGreeting = (TextView) getActivity().findViewById(R.id.statisticGreeting);
        kvr = KeyValueRepository.getInstance();
        str = StatisticRepository.getInstance();

        try {
            tvStatisticGreeting.setText("Hallo " + kvr.getKeyValue("userName").getString(1) + "! Hier hast du einen Überblick über deine Fortschritte und deine physische Entwicklung.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        fillSeries();


        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Füllt die Graphen mit den jeweiligen DatenPunkten
     */
    public void fillSeries() {

        Log.wtf("Statistic", "Fill Series");
        //gvMood
        gvMood.addSeries(str.getSeriesForMoodTime());
        gvMood.getViewport().setYAxisBoundsManual(true);
        gvMood.getViewport().setMaxY(5);
        gvMood.getViewport().setMinY(1);
        gvMood.getGridLabelRenderer().setNumHorizontalLabels(5);
        gvMood.getGridLabelRenderer().setNumVerticalLabels(5);

        gvMood.getGridLabelRenderer().setLabelFormatter(new LabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX)
                    return "" + (int)((value*2)-1) + ". Tag";
                return "" + (int) value;
            }

            @Override
            public void setViewport(Viewport viewport) {

            }
        });



        //gvLift
        gvLift.addSeries(str.getSeriesForLiftedWeight());
        gvLift.getViewport().setYAxisBoundsManual(true);
        gvLift.getViewport().setMaxY(3000);
        gvLift.getViewport().setMinY(0);
        gvMood.getGridLabelRenderer().setNumHorizontalLabels(5);
        gvMood.getGridLabelRenderer().setNumVerticalLabels(5);
        gvLift.getGridLabelRenderer().setLabelFormatter(new LabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX)
                    return ""+(int)((value*2)-1)+". Tag";
                return ""+(int)value+" kg";
            }

            @Override
            public void setViewport(Viewport viewport) {

            }
        });
    }
}
