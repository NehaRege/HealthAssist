package com.test.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.myapplication.models.appointments.Appointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by NehaRege on 10/21/17.
 */
public class CustomRvAdapter extends RecyclerView.Adapter<CustomRvAdapter.SampleViewHolder> {
    private static final String TAG = "CustomRvAdapter";

    private ArrayList<Appointment> data;

    private static OnRecyclerViewItemClickListener onItemClickListener;


    public CustomRvAdapter(ArrayList<Appointment> inComingData,
                           OnRecyclerViewItemClickListener listener) {
        this.onItemClickListener = listener;

        if (inComingData != null) {
            this.data = inComingData;
            Log.d(TAG, "CustomRvAdapter: data null ");
        } else {
            this.data = new ArrayList<>();
            Log.d(TAG, "CustomRvAdapter: data = " + data.get(0).getPurpose());
        }
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View listItemLayout = inflater.inflate(R.layout.rv_list_item, parent, false);

        return new SampleViewHolder(listItemLayout);
    }

    @Override
    public void onBindViewHolder(SampleViewHolder holder, int position) {

        Appointment dataItem = data.get(position);

        TextView textViewPurpose = holder.textViewPurpose;
        TextView textViewDate = holder.textViewDate;
        TextView textViewClickMe = holder.textViewClickMe;
        TextView textViewLocation = holder.textViewLocation;
        View viewLine = holder.viewLine;
        ImageView imageView = holder.imageView;

        imageView.setImageResource(R.drawable.ic_menu_calendar);
        textViewPurpose.setText(dataItem.getPurpose());
//        textViewLocation.setText(dataItem.getLocation());
        textViewLocation.setText("");

        String googleEventLink = dataItem.getGoogleEventLink();
        if (googleEventLink != null) {
            textViewClickMe.setText(Html.fromHtml("<a href=" + dataItem.getGoogleEventLink() + "> CLICK ME "));
            textViewClickMe.setMovementMethod(LinkMovementMethod.getInstance());

        } else {
            textViewClickMe.setText("");
        }

        textViewDate.setText("");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class SampleViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textViewPurpose;
        public TextView textViewDate;
        public TextView textViewLocation;
        public TextView textViewClickMe;
        public View viewLine;

        public SampleViewHolder(View itemView) {
            super(itemView);

            textViewPurpose = (TextView) itemView.findViewById(R.id.list_item_text_purpose);
            textViewDate = (TextView) itemView.findViewById(R.id.list_item_text_date);
            textViewClickMe = (TextView) itemView.findViewById(R.id.list_item_text_click_me);
            textViewLocation = (TextView) itemView.findViewById(R.id.list_item_text_location_rv);
            viewLine = itemView.findViewById(R.id.list_item_line);
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
