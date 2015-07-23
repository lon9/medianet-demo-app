package com.example.rompei.medianet_demo_app.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.rompei.medianet_demo_app.R;
import com.example.rompei.medianet_demo_app.dummy.api.DummyApi;
import com.example.rompei.medianet_demo_app.dummy.models.DummyEntity;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;
import rx.Observer;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycler)RecyclerView mRecycler;
    @Bind(R.id.progress)
    ProgressBar mProgress;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.root)
    CoordinatorLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mProgress.setVisibility(View.GONE);


        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://api.openweathermap.org")
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("=NETWORK="))
                .build();

        adapter.create(DummyApi.class).get("weather", "Tokyo,jp")
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<DummyEntity>(){


            @Override
            public void onCompleted() {
                Log.d("MainActivity", "onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("MainActivity", "Error : " + e.toString());
            }

            @Override
            public void onNext(final DummyEntity dummyEntity) {
                Log.d("MainActivity", "onNext()");
                if(dummyEntity != null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecycler.setAdapter(new MyAdapter(MainActivity.this, dummyEntity.weather));
                            mRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            mProgress.setVisibility(View.GONE);
                        }
                    });

                }
            }
        });
    }

    @OnClick(R.id.fab)
    public void onFavClicked(){
        new MaterialDialog.Builder(this)
                .title(R.string.send_reply_title)
                .customView(R.layout.send_dialog_layout, true)
                .positiveText(R.string.send)
                .positiveColorRes(R.color.primary_color)
                .negativeText(R.string.cancel)
                .negativeColorRes(R.color.primary_dark)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        View view = dialog.getCustomView();
                        if (view != null) {
                            EditText name = ButterKnife.findById(view, R.id.reply_name);
                            EditText text = ButterKnife.findById(view, R.id.reply_text);
                            if (text.getText() != null && !text.getText().toString().equals("")) {
                                Log.d("Dialog", "name : " + name.getText() + " text : " + text.getText());
                            } else {
                                Snackbar.make(mRootView, getString(R.string.send_error), Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                    }
                })
                .show();
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private LayoutInflater mInflater;
        private List<DummyEntity.Weather> mItems;

        public MyAdapter(Context context, List<DummyEntity.Weather> items) {
            if(items != null)
                this.mItems = items;
            else
                this.mItems = new ArrayList<>();
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.card_view_item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DummyEntity.Weather weather = mItems.get(position);
            holder.name.setText(weather.main);
            holder.text.setText(weather.description);
        }

        @Override
        public int getItemCount() {
            return mItems != null && !mItems.isEmpty() ? mItems.size() : 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView text;

            public ViewHolder(View itemView) {
                super(itemView);
                name = ButterKnife.findById(itemView, R.id.name);
                text = ButterKnife.findById(itemView, R.id.text);
            }
        }

    }
}
