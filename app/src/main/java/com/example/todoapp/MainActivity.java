package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding=ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );

        noteViewModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get( NoteViewModel.class );

        binding.floatingActionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,DataInsertActivity.class);
                intent.putExtra( "type","addMode" );
                startActivityForResult( intent,1 );
            }
        } );

        binding.No.setLayoutManager( new LinearLayoutManager( this ) );
        binding.No.setHasFixedSize( true );

        NoteAdapter noteAdapter=new NoteAdapter();
        binding.No.setAdapter( noteAdapter );

        noteViewModel.getAllNotes().observe( this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.submitList( notes );
            }
        } );

        new ItemTouchHelper( new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction==ItemTouchHelper.RIGHT){
                    noteViewModel.delete( noteAdapter.getNote( viewHolder.getAdapterPosition() ) );
                    Toast.makeText( MainActivity.this, "Deleting", Toast.LENGTH_SHORT ).show();
                }
                else{
                    Intent intent=new Intent(MainActivity.this,DataInsertActivity.class);
                    intent.putExtra( "type","update" );
                    intent.putExtra( "title",noteAdapter.getNote( viewHolder.getAdapterPosition() ).getTitle() );
                    intent.putExtra( "disp",noteAdapter.getNote( viewHolder.getAdapterPosition() ).getDisp() );
                    intent.putExtra( "id",noteAdapter.getNote( viewHolder.getAdapterPosition() ).getId() );
                    startActivityForResult( intent,2 );

                }

            }
        } ).attachToRecyclerView( binding.No );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode==1){
            String title=data.getStringExtra( "title" );
            String disp=data.getStringExtra( "disp" );

            Note note=new Note( title,disp );
            noteViewModel.insert( note );
            Toast.makeText( this, "Note Added", Toast.LENGTH_SHORT ).show();
        }
        else if(requestCode==2){
            String title=data.getStringExtra( "title" );
            String disp=data.getStringExtra( "disp" );
            Note note=new Note( title,disp );
            note.setId(data.getIntExtra( "id",0  )  );
            noteViewModel.update( note );
            Toast.makeText( this, "Note Updated", Toast.LENGTH_SHORT ).show();
        }
    }
}