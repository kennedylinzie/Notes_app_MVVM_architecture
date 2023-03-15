package com.example.notes_thing_architecture

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.example.notes_thing_architecture.Models.Note
import com.example.notes_thing_architecture.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.Date

class Add_note : AppCompatActivity() {

    private lateinit var binding : ActivityAddNoteBinding

    private lateinit var note : Note
    private lateinit var old_note : Note
    var isUpdate = false

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            old_note = intent.getSerializableExtra("current_note") as Note

            binding.etAddTitle.setText(HtmlCompat.fromHtml(old_note.title.toString(),HtmlCompat.FROM_HTML_MODE_LEGACY))

            binding.etAddNote.setText(HtmlCompat.fromHtml(old_note.note.toString(),HtmlCompat.FROM_HTML_MODE_LEGACY))
            isUpdate =true

        }catch (e : java.lang.Exception){
            e.printStackTrace()
        }
        binding.imgCheck.setOnClickListener {
            val title = binding.etAddTitle.text.toString()
            val note_desc = binding.etAddNote.text.toString()

            if (!title.isEmpty() || !note_desc.isEmpty()){
                val formatter = SimpleDateFormat("EEE,d MMM yyy HH:mm:a")

                if(isUpdate ){
                    note = Note(
                        old_note.id,title,note_desc,formatter.format(Date())
                    )
                }else{
                    note = Note(
                        null,title,note_desc,formatter.format(Date())
                    )
                }
                val intent = Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }else{
                Toast.makeText(this@Add_note,"Please enter some data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
        binding.imgBackArrow.setOnClickListener {
            this.finish()
        }

    }
}