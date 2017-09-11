package com.ggam.jjac.voca.Vocacard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ggam.jjac.voca.DB.DBHelper;
import com.ggam.jjac.voca.R;
import com.ggam.jjac.voca.Vocacard.VocaAdapter2;

import java.util.ArrayList;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

/**
 * Created by WBHS on 2017-08-17.
 */

public class myvoca extends AppCompatActivity {

    private IndexFastScrollRecyclerView mIndexView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CoordinatorLayout cor_card;
    static public ArrayList<Voca> mymy = new ArrayList<>();
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        dbHelper = new DBHelper(myvoca.this, "MYWORD.db", null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myvoca);
        setTitle("내 단어장");


        cor_card = (CoordinatorLayout) findViewById(R.id.cor_card);
        mIndexView = (IndexFastScrollRecyclerView) findViewById(R.id.index_view);

        mIndexView.setAdapter(new VocaAdapter2(dbHelper.getListWORD()));
        mLayoutManager = new LinearLayoutManager(this);
        mIndexView.setLayoutManager(mLayoutManager);
        mIndexView.setHasFixedSize(true);
        mIndexView.setIndexBarVisibility(true);
        mIndexView.setIndexBarTextColor("#000000");
        mIndexView.setIndexBarColor("#ffffff");

    }
}
