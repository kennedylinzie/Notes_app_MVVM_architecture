package com.example.notes_thing_architecture.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes_thing_architecture.Models.Note

@Dao
interface noteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note:Note)
    @Delete
    suspend fun delete(note:Note)

//    @Query("SELECT * notes_table ORDER BY id ASC")
//    fun getAllNotes() : LiveData<List<Note>>

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>


    @Query("UPDATE notes_table SET title=:title ,note=:note WHERE id=:id")
    suspend fun update(id:Int?,title:String?,note:String?)





}