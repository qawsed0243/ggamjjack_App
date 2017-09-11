package com.ggam.jjac.voca.Frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ggam.jjac.voca.R;

public class TabFragment5 extends Fragment {
    //private main.index_scroll.widget.IndexableRecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    //DBHelper dbHelper;
    private Button deleteBtn;
    private Button okBtn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_fragment5, container, false);
        /*
        dbHelper = new DBHelper(getContext(), "MYWORD.db", null, 1);

        mRecyclerView = (main.index_scroll.widget.IndexableRecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new Adapter(dbHelper.getListWORD()));
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        deleteBtn = (Button) view.findViewById(R.id.deleteBtn);
        okBtn = (Button) view.findViewById(R.id.cancelBtn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),MyVocaEditActivity.class);
                startActivity(i);
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main2Activity.class);
                startActivity(intent);
            }
        });
        */
        return view;
    }
}
