package com.muhammadfurqan.bangkitfclass.sqlite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muhammadfurqan.bangkitfclass.R

class BookAdapter(private var bookList: List<BookModel>): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class BookViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var tvBookTitle: TextView = itemView.findViewById(R.id.edt_book_title)
        fun bind(book: BookModel) {
            tvBookTitle.text = book.name
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(book)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int = bookList.size
}

interface OnItemClickCallback {
    fun onItemClicked(book: BookModel)
}
