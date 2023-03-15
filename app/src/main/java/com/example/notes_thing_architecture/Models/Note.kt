package com.example.notes_thing_architecture.Models
import android.os.Parcelable
import androidx.room.ColumnInfo
import  androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true) val id:Int?,
    @ColumnInfo(name = "title") val title:String?,
    @ColumnInfo(name = "note") val note:String?,
    @ColumnInfo(name = "date") val date:String?

):java.io.Serializable
