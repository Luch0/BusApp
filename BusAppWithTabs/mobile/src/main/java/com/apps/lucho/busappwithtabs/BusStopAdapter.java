package com.apps.lucho.busappwithtabs;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.MyCustomViewHolder> {

    private static final String TAG = BusStopAdapter.class.getSimpleName();

    private ArrayList<RowData> rowsDataSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyCustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView firstBus;
        public TextView otherBuses;
        public ImageView busPosIV;
        public TextView tvBusPosAway;

        public MyCustomViewHolder(View v) {
            super(v);
            firstBus = (TextView) v.findViewById(R.id.firstItem);
            otherBuses = (TextView) v.findViewById(R.id.otherItems);
            busPosIV = (ImageView) v.findViewById(R.id.imageViewBus);
            tvBusPosAway = (TextView) v.findViewById(R.id.tvBusPos);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //int position = getAdapterPosition(); // gets item position
            if (getAdapterPosition() != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                //Toast.makeText(view.getContext(), firstBus.getText().toString(), Toast.LENGTH_SHORT).show();

                if(!otherBuses.getText().equals("")) {
                    if (otherBuses.getVisibility() == View.GONE)
                        otherBuses.setVisibility(View.VISIBLE);
                    else otherBuses.setVisibility(View.GONE);
                }

            }

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BusStopAdapter(ArrayList<RowData> rdSet) {
        rowsDataSet = rdSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_stop_item_layout, null);
        // create ViewHolder
        MyCustomViewHolder vh = new MyCustomViewHolder(itemLayoutView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyCustomViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        RowData currentRow = rowsDataSet.get(position);
        holder.firstBus.setText(currentRow.getStopName());
        holder.firstBus.setTypeface(Typeface.DEFAULT);
        if(currentRow.isOrCloseToStop) {
            holder.firstBus.setTypeface(Typeface.DEFAULT_BOLD);
            holder.busPosIV.setVisibility(View.VISIBLE);
            //holder.tvBusPosAway.setText(currentRow.getBusPosition() + " " + position);
            holder.tvBusPosAway.setText(currentRow.getBusPosition());
            holder.otherBuses.setText(currentRow.getOtherBus());
        }
//        else{
//            holder.busPosIV.setVisibility(View.INVISIBLE);
//            holder.tvBusPosAway.setText("");
//            holder.otherBuses.setText("");
//        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return rowsDataSet.size();
    }

    //override helps fix de data in place error!!!
    @Override
    public int getItemViewType(int position) {
        return position;
    }

}