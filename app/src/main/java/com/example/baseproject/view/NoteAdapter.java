package com.example.baseproject.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseproject.R;
import com.example.baseproject.databinding.ItemNoteBinding;
import com.example.baseproject.room.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> mListData;
    private final Context mContext;
    private CallBack mCallBack;
    public void setListData(List<Note> mListData) {
        this.mListData = mListData;
        notifyDataSetChanged();
    }


    public NoteAdapter(Context context,  CallBack callBack) {
        this.mContext = context;
        this.mCallBack = callBack;
        mListData = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemNoteBinding itemView =
                ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = mListData.get(position);
        holder.itemView.setOnClickListener(v -> mCallBack.onClickNote(note));
        holder.mBinding.tvContent.setText(note.getContent());
        if (note.isDone()) {
            holder.mBinding.tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        } else {
            holder.mBinding.tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.light_blue_200));
        }

    }

    @Override
    public int getItemCount() {
        return this.mListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemNoteBinding mBinding;

        public ViewHolder(@NonNull ItemNoteBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public interface CallBack {
        void onClickNote(Note note);
    }

}
