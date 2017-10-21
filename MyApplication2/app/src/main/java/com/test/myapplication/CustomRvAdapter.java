package com.test.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NehaRege on 10/21/17.
 */
public class CustomRvAdapter extends RecyclerView.Adapter<CustomRvAdapter.SampleViewHolder> {

    private ArrayList<String> data;

    private static OnRecyclerViewItemClickListener onItemClickListener;


    public CustomRvAdapter(ArrayList<String> inComingData, OnRecyclerViewItemClickListener listener) {
        this.onItemClickListener = listener;

        if (inComingData != null) {
            this.data = inComingData;
        } else {
            this.data = new ArrayList<String>();
        }
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View listItemLayout = inflater.inflate(R.layout.rv_list_item, parent, false);

        SampleViewHolder viewHolder = new SampleViewHolder(listItemLayout);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(SampleViewHolder holder, int position) {

        String dataItem = data.get(position);

        TextView textView = holder.textView;
        ImageView imageView = holder.imageView;

        textView.setText(dataItem);
        imageView.setImageResource(R.mipmap.ic_launcher);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class SampleViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;

        public SampleViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.list_item_text_view);
            imageView = (ImageView) itemView.findViewById(R.id.list_item_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * Whenever the custom item layout is clicked, we pass the layout and position
                     * to whoever implemented the OnRecyclerViewItemClickListener ( i.e our Activity )
                     *
                     * Note: getLayoutPosition() returns the list item position in the RecyclerView
                     */
                    onItemClickListener.onItemClick(getLayoutPosition());
                }
            });
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }
}
