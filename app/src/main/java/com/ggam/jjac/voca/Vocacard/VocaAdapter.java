package com.ggam.jjac.voca.Vocacard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.ggam.jjac.voca.DB.DBHelper;
import com.ggam.jjac.voca.R;

import java.util.ArrayList;
/**
 * Created by YoungJung on 2017-06-19.
 */

public class VocaAdapter extends RecyclerView.Adapter<VocaAdapter.ViewHolder> implements SectionIndexer{
    private String mSections= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private ArrayList<Voca> Voca_card;
    public Context mcontext;
    Animation fadein;
    public static String dic_word;
    DBHelper dbHelper;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView, mean1, mean2, mean3, voca_gram;
        LinearLayout front, back;
        ImageButton goto_dic, voca_btn;

        ViewHolder(View view)
        {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.word);
            front = (LinearLayout) view.findViewById(R.id.front);
            mean1 = (TextView) view.findViewById(R.id.mean1);
            mean2 = (TextView) view.findViewById(R.id.mean2);
            mean3 = (TextView) view.findViewById(R.id.mean3);
            back = (LinearLayout) view.findViewById(R.id.back);
            goto_dic = (ImageButton) view.findViewById(R.id.goto_dic);
            voca_btn = (ImageButton) view.findViewById(R.id.myvoca_btn);
            voca_gram = (TextView) view.findViewById(R.id.voca_gram);
        }
    }

    public VocaAdapter(ArrayList<Voca> Voca_card){ this.Voca_card = Voca_card; }
    @Override
    public VocaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        this.mcontext = parent.getContext();
        View v = LayoutInflater.from(mcontext)
                .inflate(R.layout.voca_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        final ViewHolder vh = new ViewHolder(v);

        vh.front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.back.setVisibility(View.VISIBLE);
                vh.front.setVisibility(View.INVISIBLE);
                fadein = AnimationUtils.loadAnimation(mcontext, R.anim.fadein);
                vh.back.startAnimation(fadein);
            }
        });
        vh.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.back.setVisibility(View.INVISIBLE);
                vh.front.setVisibility(View.VISIBLE);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Voca item = Voca_card.get(position);
        holder.mTextView.setText(Voca_card.get(position).getWord());
        holder.mean1.setText(Voca_card.get(position).getMean1());
        holder.mean2.setText(Voca_card.get(position).getMean2());
        holder.mean3.setText(Voca_card.get(position).getMean3());
        holder.voca_gram.setText(Voca_card.get(position).getGrammar());
        holder.front.setVisibility(View.VISIBLE);
        holder.back.setVisibility(View.INVISIBLE);
        dic_word= item.getWord();
        dbHelper = new DBHelper(mcontext, "MYWORD.db", null, 1);
        holder.goto_dic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dic_word = item.getWord();
                Intent i = new Intent(mcontext, DicActivity.class);
                i.putExtra("dic", dic_word);
                mcontext.startActivity(i);
            }
        });
        //내 단어장 추가
        holder.voca_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbHelper.getListWORD().size() < item.index){
                    if(dbHelper.select(item.getWord())==0) {
                        dbHelper.insert(item.index, item.getWord(), item.getMean1(), item.getMean2(), item.getMean3(), item.getGrammar());
                        Toast.makeText(mcontext, "내 단어장에 추가되었습니다.", Toast.LENGTH_LONG).show();
                    }else if(dbHelper.select(item.getWord())==100){
                        Toast.makeText(mcontext, "이미 내 단어장에 존재합니다.", Toast.LENGTH_LONG).show();
                    }
                }else if(dbHelper.getListWORD().size() ==item.index){
                }else{
                    if(dbHelper.getListWORD().get(item.index).getIndex() == item.getIndex() ){
                        dbHelper.delete(item.getWord());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Voca_card.size();
    }

    @Override
    public int getPositionForSection(int section)
    {
        char selected = mSections.charAt(section);

        selected = Character.toLowerCase(selected);
        Log.d("selected -> : ",  " " + selected);
        int size = getItemCount();

        for(int i=0; i<size;i++)
        {
            //   Log.d("i -> : ",  " " + Voca_card.get(i).getWord());
            if(Voca_card.get(i).getWord().charAt(0) == selected)
                return i;
        }
        return 0;
    }
    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }
}
