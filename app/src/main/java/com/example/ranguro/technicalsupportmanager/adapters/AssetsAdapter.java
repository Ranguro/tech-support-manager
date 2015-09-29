package com.example.ranguro.technicalsupportmanager.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ranguro.technicalsupportmanager.R;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectAsset;
import com.example.ranguro.technicalsupportmanager.swipe_helper.ItemTouchHelperAdapter;
import com.example.ranguro.technicalsupportmanager.swipe_helper.ItemTouchHelperViewHolder;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Proyecto on 22/09/2015.
 */
public class AssetsAdapter extends RecyclerView.Adapter<AssetsAdapter.ViewHolder>  implements ItemTouchHelperAdapter{

    private List<ParseObjectAsset> assetList;
    private ClickListener clickListener;

    public AssetsAdapter(List<ParseObjectAsset> assetList) {
        this.assetList = assetList;
    }

    public AssetsAdapter(){

    }

    public void addAll(List<ParseObjectAsset> assetsList) {
        assetList = assetsList;
    }

    @Override
    public void onItemDismiss(int position) {
        assetList.remove(position);
        notifyItemRemoved(position);
    }


    public interface ClickListener {
        void onAssetSelected(View view, ParseObject asset, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.list_item_asset, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setAsset(assetList.get(position));
        holder.setPosition(position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {

        public final TextView assetAssetNumber;
        public final TextView assetCategory;
        public final TextView assetLocation;

        private ParseObjectAsset asset;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
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
            clickListener.onAssetSelected(view, asset, position);
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }


}
