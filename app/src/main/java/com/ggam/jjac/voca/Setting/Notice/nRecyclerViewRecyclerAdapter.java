package com.ggam.jjac.voca.Setting.Notice;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggam.jjac.voca.R;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.List;

/**
 * Created by julie on 2017-07-17.
 */

public class nRecyclerViewRecyclerAdapter extends RecyclerView.Adapter<nRecyclerViewRecyclerAdapter.ViewHolder> {

    private final List<NoticeItem> data;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public nRecyclerViewRecyclerAdapter(final List<NoticeItem> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public nRecyclerViewRecyclerAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new nRecyclerViewRecyclerAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.nrecycle, parent, false));
    }

    @Override
    public void onBindViewHolder(final nRecyclerViewRecyclerAdapter.ViewHolder holder, final int position) {

        final NoticeItem item = data.get(position);
        holder.setIsRecyclable(false);
        holder.nnum.setText(item.noticeNum);
        holder.ntitle.setText(item.title);
        holder.ndate.setText(item.timestamp);
        holder.ncontent.setText(item.content);
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, item.colorId1));
        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setBackgroundColor(ContextCompat.getColor(context, item.colorId2));
        holder.expandableLayout.setInterpolator(item.interpolator);
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.buttonLayout, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.buttonLayout, 180f, 0f).start();
                expandState.put(position, false);
            }
        });

        holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);
            }
        });
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nnum, ntitle, ncontent, ndate;
        public RelativeLayout buttonLayout;
        /**
         * You must use the ExpandableLinearLayout in the recycler view.
         * The ExpandableRelativeLayout doesn't work.
         */
        public ExpandableLinearLayout expandableLayout;

        public ViewHolder(View v) {
            super(v);
            nnum = (TextView) v.findViewById(R.id.noticnum);
            ntitle = (TextView) v.findViewById(R.id.notietitle);
            ndate = (TextView) v.findViewById(R.id.notidate);
            ncontent = (TextView) v.findViewById(R.id.noticontent);
            buttonLayout = (RelativeLayout) v.findViewById(R.id.notibut);
            expandableLayout = (ExpandableLinearLayout) v.findViewById(R.id.notiexpandableLayout);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}