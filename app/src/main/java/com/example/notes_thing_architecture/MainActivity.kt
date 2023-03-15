package com.example.notes_thing_architecture

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes_thing_architecture.Adapter.NoteAdapter
import com.example.notes_thing_architecture.Database.NoteDatabase
import com.example.notes_thing_architecture.Models.Note
import com.example.notes_thing_architecture.Models.NoteViewModel
import com.example.notes_thing_architecture.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),NoteAdapter.NoteClickListener,PopupMenu.OnMenuItemClickListener  {

    lateinit var binding: ActivityMainBinding
    lateinit var database: NoteDatabase
    lateinit var adapter : NoteAdapter
    lateinit var selectedNote:Note
    lateinit var viewModel: NoteViewModel

    val update_notes = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if (result.resultCode == Activity.RESULT_OK) {
            val note  = result.data?.getSerializableExtra("note") as? Note
            if(note != null){
                viewModel.update(note)
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        InitUI()

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application))
            .get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this){
                list->list?.let{
                    adapter.updateList(list)
            }
        }
            database = NoteDatabase.getDatabase(this)
    }

    private fun InitUI() {
    binding.recyclerview.setHasFixedSize(true)
    binding.recyclerview.layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
    adapter = NoteAdapter(this,this)
    binding.recyclerview.adapter = adapter

    val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if (result.resultCode == Activity.RESULT_OK) {
            val note  = result.data?.getSerializableExtra("note") as? Note
            if(note != null){
                viewModel.insertNote(note)
            }
        }

        }
        binding.fbAddNote.setOnClickListener{

            val intent = Intent(this,Add_note::class.java)
            getContent.launch(intent)

        }
        binding.searchview.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    adapter.filterSearch(newText)
                }
                return true
            }

        })
    }

    override fun onItemClicked(note: Note) {
        val intent  = Intent(this@MainActivity,Add_note::class.java)
        intent.putExtra("current_note",note)
        update_notes.launch(intent)


    }

    private fun populateDisplay(cardView: CardView){
        val popup = PopupMenu(this,cardView)
        popup.setOnMenuItemClickListener (this@MainActivity)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        selectedNote = note
        populateDisplay(cardView)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete_note){
            viewModel.deleteNote(selectedNote)
        return true
        }
        return false
    }

}

















