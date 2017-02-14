package com.bnsantos.diffutils;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
  private List<Item> mItems;
  private final int mAvatarSize;

  public ItemAdapter(List<Item> items, int avatarSize) {
    mItems = items;
    mAvatarSize = avatarSize;
  }

  @Override
  public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adatper_item, parent, false);
    return new ItemHolder(view);
  }

  @Override
  public void onBindViewHolder(ItemHolder holder, int position) {
    Item item = mItems.get(position);
    holder.mName.setText(item.getName());

    loadImage(holder.mAvatar, item.getUrl());
  }

  @Override
  public void onBindViewHolder(ItemHolder holder, int position, List<Object> payloads) {
    if (!payloads.isEmpty()) {
      Bundle b = (Bundle) payloads.get(0);
      for (String key : b.keySet()) {
        if(key.equals(Item.NAME)){
          holder.mName.setText(b.getString(Item.NAME));
        }else if(key.equals(Item.URL)){
          loadImage(holder.mAvatar, b.getString(Item.URL));
        }
      }
    }else {
      onBindViewHolder(holder, position);
    }
  }

  private void loadImage(SimpleDraweeView view, String url) {
    if (url != null) {
      DraweeController controller = Fresco.newDraweeControllerBuilder()
          .setImageRequest(ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
              .setRotationOptions(RotationOptions.autoRotate())
              .setResizeOptions(new ResizeOptions(mAvatarSize, mAvatarSize)).build())
          .setOldController(view.getController())
          .build();
      view.setController(controller);
    }
  }

  @Override
  public int getItemCount() {
    return mItems.size();
  }

  public void swap(List<Item> data) {
    final ItemDiffCallback cb = new ItemDiffCallback(mItems, data);
    final DiffUtil.DiffResult result = DiffUtil.calculateDiff(cb);
    result.dispatchUpdatesTo(this);

    mItems.clear();
    mItems.addAll(data);
  }

  public List<Item> getItems() {
    return mItems;
  }

  public void setItems(List<Item> items) {
    mItems = items;
  }

  class ItemHolder extends RecyclerView.ViewHolder{
    final SimpleDraweeView mAvatar;
    final TextView mName;
    public ItemHolder(View itemView) {
      super(itemView);
      mAvatar = (SimpleDraweeView) itemView.findViewById(R.id.avatar);
      mName = (TextView) itemView.findViewById(R.id.name);
    }
  }
}
