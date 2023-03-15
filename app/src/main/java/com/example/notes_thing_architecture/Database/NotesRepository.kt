package com.example.notes_thing_architecture.Database

import androidx.lifecycle.LiveData
import com.example.notes_thing_architecture.Models.Note

class NotesRepository(private val noteDao: noteDao) {
//this class acts as an intermediary to the note dao class as in DIRECT ACCESS OPERATION
    val allNotes : LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    suspend fun delete(note: Note){
        noteDao.delete(note)
    }

    suspend fun update(note: Note){
        noteDao.update(note.id,note.title,note.note)
    }


}