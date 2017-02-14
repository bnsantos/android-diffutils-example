package com.bnsantos.diffutils;

import android.net.Uri;
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

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
  private String[] mItems;
  private final int mAvatarSize;

  public ItemAdapter(String[] items, int avatarSize) {
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
    String item = mItems[position];
    holder.mName.setText(item);

    String url = getImageUrl(item);
    if (url != null) {
      DraweeController controller = Fresco.newDraweeControllerBuilder()
          .setImageRequest(ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
              .setRotationOptions(RotationOptions.autoRotate())
              .setResizeOptions(new ResizeOptions(mAvatarSize, mAvatarSize)).build())
          .setOldController(holder.mAvatar.getController())
          .build();
      holder.mAvatar.setController(controller);
    }
  }

  @Override
  public int getItemCount() {
    return mItems.length;
  }

  private String getImageUrl(String name){
    if (name != null && name.length() > 0) {
      String[] split = name.split(" ");
      StringBuilder initials = new StringBuilder();
      if (split.length > 0) {
        if(split.length>1){
          initials.append(split[0].substring(0, 1)).append(split[split.length - 1].substring(0, 1));
        }else {
          initials.append(split[0].substring(0, 1));
        }
      }else {
        initials.append(name.substring(0, 1));
      }
      String letter, bg;
      switch (name.charAt(0) % 15) {
        case 0:
          letter = "ffffff";
          bg = "333a45";
          break;
        case 1:
          letter = "ffffff";
          bg = "f15e0e";
          break;
        case 2:
          letter = "333a45";
          bg = "abc144";
          break;
        case 3:
          letter = "ffffff";
          bg = "267ca8";
          break;
        case 4:
          letter = "ffffff";
          bg = "e23e3e";
          break;
        case 5:
          letter = "333a45";
          bg = "d8d8d8";
          break;
        case 6:
          letter = "333a45";
          bg = "71c6d9";
          break;
        case 7:
          letter = "ffffff";
          bg = "5f626a";
          break;
        case 8:
          letter = "333a45";
          bg = "f8f9fa";
          break;
        case 9:
          letter = "ffffff";
          bg = "33313f";
          break;
        case 10:
          letter = "ffffff";
          bg = "13597c";
          break;
        case 11:
          letter = "333a45";
          bg = "ffc000";
          break;
        case 12:
          letter = "333a45";
          bg = "f5f5f5";
          break;
        case 13:
          letter = "333a45";
          bg = "dfe0e2";
          break;
        case 14:
          letter = "333a45";
          bg = "7f7f7f";
          break;
        default:
          letter = "ffffff";
          bg = "2a2a2a";
          break;
      }
      return "https://dummyimage.com/180x150/" + bg + "/" + letter + "&text=" + initials.toString();
    }
    return null;
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
