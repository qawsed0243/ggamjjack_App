package com.ggam.jjac.voca.Vocacard;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ggam.jjac.voca.BaseActivity;
import com.ggam.jjac.voca.R;

import java.util.ArrayList;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

/**
 * Created by YoungJung on 2017-06-19.
 */

public class Su_mid_Activity extends BaseActivity {
    private IndexFastScrollRecyclerView mIndexView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CoordinatorLayout cor_card;
    static public ArrayList<Voca> Voca_card2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("수능 중급");

        cor_card = (CoordinatorLayout) findViewById(R.id.cor_card);

        cor_card.setBackgroundResource(R.drawable.middle_back);

        mIndexView = (IndexFastScrollRecyclerView) findViewById(R.id.index_view);
        mIndexView.setAdapter(new VocaAdapter(Voca_card2));
        mLayoutManager = new LinearLayoutManager(this);
        mIndexView.setLayoutManager(mLayoutManager);
        mIndexView.setHasFixedSize(true);
        mIndexView.setIndexBarVisibility(true);
        mIndexView.setIndexBarTextColor("#000000");
        mIndexView.setIndexBarColor("#ffffff");
    }
}
