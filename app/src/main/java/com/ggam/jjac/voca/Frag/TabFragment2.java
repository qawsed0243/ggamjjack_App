package com.ggam.jjac.voca.Frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.ggam.jjac.voca.R;
import com.ggam.jjac.voca.Vocacard.Su_high_Activity;
import com.ggam.jjac.voca.Vocacard.Su_low_Activity;
import com.ggam.jjac.voca.Vocacard.Su_mid_Activity;
import com.ggam.jjac.voca.Vocacard.Toe_high_Activity;
import com.ggam.jjac.voca.Vocacard.Toe_mid_Activity;
import com.ggam.jjac.voca.Vocacard.Toe_low_Activity;

import static com.ggam.jjac.voca.Frag.FragMain.kinsightSession;


public class TabFragment2 extends Fragment {
    // 깜짝 단어장 (공부하기)

    ImageButton toe_low, toe_mid, toe_high, su_low, su_mid, su_high;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_fragment2,container,false);
        toe_low = (ImageButton) view.findViewById(R.id.wordselect4);
        toe_mid = (ImageButton) view.findViewById(R.id.wordselect5);
        toe_high = (ImageButton) view.findViewById(R.id.wordselect6);
        su_low = (ImageButton) view.findViewById(R.id.wordselect1);
        su_mid = (ImageButton) view.findViewById(R.id.wordselect2);
        su_high = (ImageButton) view.findViewById(R.id.wordselect3);

        toe_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kinsightSession.addEvent(String.format("토익_초급"));
                Intent i = new Intent(getActivity(), Toe_low_Activity.class);
                startActivity(i);
            }
        });

        toe_mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kinsightSession.addEvent(String.format("토익_중급"));
                Intent intent = new Intent(getActivity(),Toe_mid_Activity.class);
                startActivity(intent);
            }
        });

        toe_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kinsightSession.addEvent(String.format("토익_고급"));
                Intent i = new Intent(getActivity(), Toe_high_Activity.class);
                startActivity(i);
            }
        });
        su_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kinsightSession.addEvent(String.format("수능_초급"));
                Intent i = new Intent(getActivity(), Su_low_Activity.class);
                startActivity(i);
            }
        });
        su_mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kinsightSession.addEvent(String.format("수능_중급"));
                Intent i = new Intent(getActivity(), Su_mid_Activity.class);
                startActivity(i);
            }
        });
        su_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kinsightSession.addEvent(String.format("수능_고급"));
                Intent i = new Intent(getActivity(), Su_high_Activity.class);
                startActivity(i);
            }
        });

        return view;
    }
}
