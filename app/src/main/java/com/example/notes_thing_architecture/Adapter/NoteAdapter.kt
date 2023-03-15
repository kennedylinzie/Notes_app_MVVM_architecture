package com.example.notes_thing_architecture.Adapter

import android.content.Context
import android.text.Html
import android.text.Html.fromHtml
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.notes_thing_architecture.Models.Note
import com.example.notes_thing_architecture.R
import kotlin.random.Random

class NoteAdapter(private val context:Context,val listener:NoteClickListener) :RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

   private val NotesList = ArrayList<Note>()
   private val fullList = ArrayList<Note>()
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
     return NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,parent,false))
   }

   override fun getItemCount(): Int = NotesList.size

   override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
      val currentNote = NotesList[position]
      holder.title.text = HtmlCompat.fromHtml(currentNote.title.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
      holder.title.isSelected =true
      holder.note.text = HtmlCompat.fromHtml(currentNote.note.toString(),HtmlCompat.FROM_HTML_MODE_LEGACY)
      holder.date.text = currentNote.date
      holder.date.isSelected =true

      holder.card_view.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(),null))
      // Apply the animation to the view holder's itemView
      YoYo.with(Techniques.BounceInUp)
         .duration(1000)
         .playOn(holder.itemView)

      holder.card_view.setOnClickListener {
         listener.onItemClicked(NotesList[holder.adapterPosition])
      }
      holder.card_view.setOnLongClickListener{
         listener.onLongItemClicked(NotesList[holder.adapterPosition], holder.card_view)
         true
      }
   }

   fun updateList(newlist:List<Note>){
      fullList.clear()
      fullList.addAll(newlist)

      NotesList.clear()
      NotesList.addAll(fullList)

      notifyDataSetChanged()

   }

   fun filterSearch(search:String){
      NotesList.clear()

      for (item in fullList) {
         if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
            item.note?.lowercase()?.contains(search.lowercase()) == true) {
            val highlightSearchTerm = "<font color='Red'>$search</font>"
            val highlightedTitle = item.title?.replace(search, highlightSearchTerm, true)
            val highlightedNote = item.note?.replace(search, highlightSearchTerm, true)
            val highlightedNoteItem = Note(
               item.id, highlightedTitle, highlightedNote, item.date
            )
            NotesList.add(highlightedNoteItem)
         }
      }

      notifyDataSetChanged()
   }

   fun randomColor():Int{
      val color = ArrayList<Int>()
      color.add(R.color.notecolor1)
      color.add(R.color.notecolor2)
      color.add(R.color.notecolor3)
      color.add(R.color.notecolor4)
      color.add(R.color.notecolor5)
      color.add(R.color.notecolor6)

      val seed = System.currentTimeMillis().toInt()
      val randomIndex = Random(seed).nextInt(color.size)
      return color[randomIndex]
   }

   inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

      val card_view = itemView.findViewById<CardView>(R.id.card_layout)
      val title = itemView.findViewById<TextView>(R.id.tv_title)
      val note = itemView.findViewById<TextView>(R.id.tv_note)
      val date = itemView.findViewById<TextView>(R.id.tv_date)


   }

   interface NoteClickListener {
      fun onItemClicked(note:Note)
      fun onLongItemClicked(note:Note,cardView: CardView)
   }


}