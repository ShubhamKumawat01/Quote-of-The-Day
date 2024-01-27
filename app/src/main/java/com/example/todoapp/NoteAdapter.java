package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.databinding.EachNotesBinding;

public class NoteAdapter extends ListAdapter<Note,NoteAdapter.Viewholder> {

    public NoteAdapter(){
        super(CALLBACK);

    }
    private static final DiffUtil.ItemCallback<Note> CALLBACK=new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals( newItem.getTitle() )
                    && oldItem.getDisp().equals( newItem.getDisp() );
        }
    };

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.each_notes,parent,false );
        return new Viewholder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Note note=getItem( position );
        holder.binding.titleNo.setText( note.getTitle() );
        holder.binding.dispNo.setText( note.getDisp() );
    }
    public Note getNote(int position){
        return getItem( position );
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        EachNotesBinding binding;
        public Viewholder(@NonNull View itemView) {
            super( itemView );
            binding=EachNotesBinding.bind( itemView );
        }
    }
}
