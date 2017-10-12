package com.ggam.jjac.voca.Frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ggam.jjac.voca.Exam_Stoarge.StorageDBHelper;
import com.ggam.jjac.voca.R;

public class TabFragment4 extends Fragment {
    // 내단어장

    //private AdView mAdView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_fragment4, container, false);
//        Button x = (Button) view.findViewById(R.id.button3);
//        x.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                long now = System.currentTimeMillis();
//                StorageDBHelper ExdbManager = new StorageDBHelper(getContext().getApplicationContext(), "EXAM.db", null, 1);
//                ExdbManager.insert((int)now);
//            }
//        });
        /*
        Button x1 = (Button) view.findViewById(R.id.personaldb);
        Button x2 = (Button) view.findViewById(R.id.accessterm);

        x1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), accessTv.class);
                startActivity(i);
            }
        });
        x2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), personalTv.class);
                startActivity(i);
            }
        });
        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        mAdView = (AdView) view.findViewById(R.id.adView);
        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        */
        return view;
    }
}
