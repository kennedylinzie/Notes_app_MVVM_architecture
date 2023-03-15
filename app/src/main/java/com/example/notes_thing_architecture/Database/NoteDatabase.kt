package com.example.notes_thing_architecture.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes_thing_architecture.Models.Note
import com.example.notes_thing_architecture.Utilities.DATABASE_NAME

//this class is responsible for creating a database for the app
@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): noteDao

    companion object{

        @Volatile
        private var INSTANCE : NoteDatabase? = null

        fun getDatabase(context:Context) : NoteDatabase{
            return INSTANCE?: synchronized(this){
                val instance  = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }



    }


}