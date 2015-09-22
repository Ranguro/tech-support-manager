package com.example.ranguro.technicalsupportmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ranguro.technicalsupportmanager.R;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectAsset;
import com.parse.ParseObject;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by Proyecto on 22/09/2015.
 */
public class AssetsAdapter extends RecyclerView.Adapter<AssetsAdapter.ViewHolder> {

    private List<ParseObjectAsset> assetList;


    private clickListener clickListener;

    public AssetsAdapter(List<ParseObjectAsset> assetList) {
        this.assetList = assetList;
    }

    public interface clickListener {

        void onTaskSelected(View view, ParseObject asset, int position);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item_asset, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setAsset(assetList.get(position));
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public final TextView assetAssetNumber;
        public final TextView assetCategory;
        public final TextView assetLocation;


        private ParseObjectAsset asset;
        private int position;


        public ViewHolder(View itemView) {
            super(itemView);

            assetAssetNumber = (TextView) itemView.findViewById(R.id.asset_assetnumber);
            assetCategory = (TextView) itemView.findViewById(R.id.asset_category);
            assetLocation = (TextView) itemView.findViewById(R.id.asset_location);
        }

        public void setAsset(ParseObjectAsset asset){
            this.asset = asset;
            assetAssetNumber.setText(asset.getAssetNumber());
            assetCategory.setText(asset.getCategory());
            assetLocation.setText(asset.getLocation());
        }

        @Override
        public void onClick(View view) {
            if (clickListener == null) return;

            clickListener.onTaskSelected(view, asset, position);

        }
        public void setPosition(int position) {
            this.position = position;
        }

    }


}
