package com.bnsantos.diffutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends AppCompatActivity {
  private ItemAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Fresco.initialize(this);

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    mAdapter = new ItemAdapter(getResources().getStringArray(R.array.names), getResources().getDimensionPixelSize(R.dimen.avatar_size));
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(mAdapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.sort, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.random:
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
