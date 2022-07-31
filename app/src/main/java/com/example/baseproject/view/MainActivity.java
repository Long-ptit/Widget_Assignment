package com.example.baseproject.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.baseproject.R;
import com.example.baseproject.databinding.ActivityMainBinding;
import com.example.baseproject.room.Note;
import com.example.baseproject.room.NoteRepository;
import com.example.baseproject.util.Util;
import com.example.baseproject.view.NoteAdapter;
import com.example.baseproject.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.CallBack {

    private MainViewModel mMainViewModel;
    private ActivityMainBinding mBinding;
    private NoteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferences(MODE_PRIVATE);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mMainViewModel.getListNoteLiveData().observe(this, notes -> mAdapter.setListData(notes));
        setUpRcv();
        mBinding.btnAdd.setOnClickListener(v -> {
            mMainViewModel.addData(new Note(mBinding.edtContent.getText().toString(), false));
            mBinding.edtContent.setText("");
        });
        Util.updateData(getApplicationContext());
    }

    private void setUpRcv() {
        mBinding.rcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new NoteAdapter(this, this);
        mBinding.rcv.setAdapter(mAdapter);
    }

    @Override
    public void onClickNote(Note note) {
        note.setDone(note.isDone());
        mMainViewModel.updateWork(note);
    }

    @Override
    protected void onDestroy() {
        mBinding = null;
        super.onDestroy();
    }
}