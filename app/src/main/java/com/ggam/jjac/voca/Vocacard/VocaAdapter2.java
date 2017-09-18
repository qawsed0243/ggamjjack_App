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
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.ggam.jjac.voca.DB.DBHelper;
import com.ggam.jjac.voca.R;

import java.util.ArrayList;

/**
 * Created by YoungJung on 2017-06-19.
 */

public class VocaAdapter2 extends RecyclerView.Adapter<VocaAdapter2.ViewHolder> implements SectionIndexer{
    private String mSections= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private ArrayList<Voca> Voca_card;
    public Context mcontext;
    Animation fadein;
    public static String dic_word;
    DBHelper dbHelper;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView, mean1, mean2, mean3, voca_gram;
        LinearLayout front, back;
        ImageButton goto_dic;
        ImageButton voca_btn;
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

    public VocaAdapter2(ArrayList<Voca> Voca_card){ this.Voca_card = Voca_card; }
    @Override
    public VocaAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        this.mcontext = parent.getContext();
        View v = LayoutInflater.from(mcontext)
                .inflate(R.layout.voca_card2, parent, false);
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
        dbHelper = new DBHelper(mcontext, "MYWORD.db", null, 1);
        dic_word= item.getWord();
        holder.goto_dic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dic_word = item.getWord();
                Intent i = new Intent(mcontext, DicActivity.class);
                i.putExtra("dic", dic_word);
                mcontext.startActivity(i);
            }
        });
        //내 단어장 삭제
        holder.voca_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.delete(item.getWord());
                Toast.makeText(mcontext,"재접속시 적용 됩니다.",Toast.LENGTH_SHORT).show();
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
