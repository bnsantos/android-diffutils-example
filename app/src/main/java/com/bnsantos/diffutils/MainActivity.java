package com.bnsantos.diffutils;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private ItemAdapter mAdapter;
  private RecyclerView mRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Fresco.initialize(this);

    mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    mAdapter = new ItemAdapter(getData(null), getResources().getDimensionPixelSize(R.dimen.avatar_size));
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.setAdapter(mAdapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_activity, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.random:
        random();
        return true;
      case R.id.lastName:
        addLastNameOddPos();
        return true;
      case R.id.reset:
        mAdapter.swap(getData(null));
        return true;
      case R.id.lastNameAsync:
        addAsync();
        return true;
      case R.id.update:
        addSingle();
        return true;
      case R.id.remove:
        remove();
        return true;
      case R.id.clear:
        clear();
        return true;
      case R.id.add:
        add();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void random(){
    List<Item> data = getData(null);
    Collections.shuffle(data);
    mAdapter.swap(data);
  }

  private void addLastNameOddPos(){
    List<Item> data = getData(" Last");
    mAdapter.swap(data);
  }

  private void addAsync(){
    new AddAsyncTask().execute();
  }

  private List<Item> getData(String last){
    String[] names = getResources().getStringArray(R.array.names);
    String[] ids = getResources().getStringArray(R.array.ids);

    List<Item> items = new ArrayList<>(names.length);
    String name;
    for (int i = 0; i < names.length; i++) {
      name = names[i] + ((last != null && i % 2 != 0) ? last : "");

      items.add(new Item(ids[i], name, getImageUrl(ids[i], name)));
    }
    return items;
  }

  private void addSingle(){
    List<Item> data = getData(null);
    int index = 4;
    Item item = data.get(index);
    data.remove(index);
    data.add(index, new Item(item.getId(), item.getName() + " mudou", item.getUrl()));
    mAdapter.swap(data);
  }

  private void remove(){
    List<Item> data = getData(null);
    if (data.size() > 4) {
      data.remove(4);
    }
    mAdapter.swap(data);
  }

  private void clear(){
    mAdapter.swap(new ArrayList<Item>());
  }

  private void add(){
    List<Item> data = getData(null);
    String id = Integer.toString(data.size());
    String name = "Added User" + System.currentTimeMillis();
    data.add(0, new Item(id, name, getImageUrl(id, name)));
    mAdapter.swap(data);
    mRecyclerView.scrollToPosition(0);
  }

  private String getImageUrl(String id, String name){
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
      switch (Integer.parseInt(id) % 15) {
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

  private class AddAsyncTask extends AsyncTask<Void, Void, DiffUtil.DiffResult>{

    private List<Item> mData;

    @Override
    protected DiffUtil.DiffResult doInBackground(Void... params) {
      mData = getData(" Last");

      final ItemDiffCallback cb = new ItemDiffCallback(mAdapter.getItems(), mData);
      final DiffUtil.DiffResult result = DiffUtil.calculateDiff(cb);
      return result;
    }

    @Override
    protected void onPostExecute(DiffUtil.DiffResult result) {
      result.dispatchUpdatesTo(mAdapter);
      mAdapter.setItems(mData);
    }
  }
}
