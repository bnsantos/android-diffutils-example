package com.bnsantos.diffutils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

public class ItemDiffCallback extends DiffUtil.Callback {
  private final List<Item> mOldItems;
  private final List<Item> mNewItems;

  public ItemDiffCallback(List<Item> oldItems, List<Item> newItems) {
    mOldItems = oldItems;
    mNewItems = newItems;
  }

  @Override
  public int getOldListSize() {
    return mOldItems != null ? mOldItems.size() : 0;
  }

  @Override
  public int getNewListSize() {
    return mNewItems != null ? mNewItems.size() : 0;
  }

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return mOldItems.get(oldItemPosition).getId().equals(mNewItems.get(newItemPosition).getId());
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    return mOldItems.get(oldItemPosition).equals(mNewItems.get(newItemPosition));
  }

  @Nullable
  @Override
  public Object getChangePayload(int oldItemPosition, int newItemPosition) {
    Item oldI = mOldItems.get(oldItemPosition);
    Item newI = mNewItems.get(newItemPosition);

    Bundle diff = new Bundle();

    if (!oldI.getName().equals(newI.getName())) {
      diff.putString(Item.NAME, newI.getName());
    }

    if (!oldI.getUrl().equals(newI.getUrl())) {
      diff.putString(Item.URL, newI.getUrl());
    }

    return diff;
  }
}
