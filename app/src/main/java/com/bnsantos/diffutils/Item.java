package com.bnsantos.diffutils;

public class Item {
  public static final String NAME = "name";
  public static final String URL = "url";
  private final String mId;
  private final String mName;
  private final String mUrl;

  public Item(String id, String name, String url) {
    mId = id;
    mName = name;
    mUrl = url;
  }

  public String getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public String getUrl() {
    return mUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Item item = (Item) o;

    if (mId != null ? !mId.equals(item.mId) : item.mId != null) return false;
    if (mName != null ? !mName.equals(item.mName) : item.mName != null) return false;
    return mUrl != null ? mUrl.equals(item.mUrl) : item.mUrl == null;

  }

  @Override
  public int hashCode() {
    int result = mId != null ? mId.hashCode() : 0;
    result = 31 * result + (mName != null ? mName.hashCode() : 0);
    result = 31 * result + (mUrl != null ? mUrl.hashCode() : 0);
    return result;
  }
}
