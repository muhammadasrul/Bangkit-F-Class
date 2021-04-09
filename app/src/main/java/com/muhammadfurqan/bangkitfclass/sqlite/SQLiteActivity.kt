package com.muhammadfurqan.bangkitfclass.sqlite

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muhammadfurqan.bangkitfclass.R
import com.muhammadfurqan.bangkitfclass.sqlite.db.BookDatabaseManager
import kotlinx.coroutines.launch

/**
 *
 * Contact : 081375496583
 *
 * Step :
 * 1. Fork our Repository (https://github.com/fueerqan/Bangkit-F-Class)
 *
 * CHALLENGE :
 * 1. Recycler View to show all of the data, previously we only show them in toast
 * 2. Add Function to edit the books data for each item in your Recycler View Items
 * 3. Add Function to delete the books data for each item in your Recycler View Items
 * 4. Notify Data Changes for you Recycler View
 *
 * Reward : Rp20.000 Go-Pay / OVO
 * Limit : No Limit Person
 * Dateline : 23.00
 *
 * Submit to https://forms.gle/CytSQSyQDJeivpkd7
 *
 */

class SQLiteActivity : AppCompatActivity() {

    private lateinit var etBookName: AppCompatEditText
    private lateinit var btnAdd: AppCompatButton
    private lateinit var btnRead: AppCompatButton
    private lateinit var rvBookList: RecyclerView

    private val viewModel: BookViewModel by lazy {
        BookViewModel(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)

        etBookName = findViewById(R.id.et_book_name)
        btnAdd = findViewById(R.id.btn_add)
        btnRead = findViewById(R.id.btn_read)
        rvBookList = findViewById(R.id.rv_book_list)

        btnAdd.setOnClickListener {
            onAdd()
        }

        onRead()
    }

    private fun onAdd() {
        val bookName = etBookName.text.toString()
        if (bookName.isNotEmpty()) {
            viewModel.addBook(bookName)
            etBookName.setText("")
        } else {
            Toast.makeText(this, "Please fill in the book name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onRead() {
        viewModel.getAllBook()
        viewModel.bookList.observe(this, {
            val adapter = BookAdapter(it)
            rvBookList.layoutManager = LinearLayoutManager(this)
            rvBookList.adapter = adapter
            adapter.setOnItemClickCallback(object: OnItemClickCallback {
                override fun onItemClicked(book: BookModel) {
                    showBookDialog(book)
                }

            })
        })
    }

    private fun showBookDialog(book: BookModel) {

        val view: View = LayoutInflater.from(this).inflate(R.layout.book_dialog, null)

        val edtBookTitle: AppCompatEditText = view.findViewById(R.id.edt_book_title)

        edtBookTitle.setText(book.name)

        AlertDialog.Builder(this)
            .setTitle("Edit/Delete")
            .setView(view)
            .setPositiveButton("Edit") { _, _ ->
                val title = edtBookTitle.text.toString()
                viewModel.editBook(book.id, title) }
            .setNegativeButton("Delete") { _, _ ->
                AlertDialog.Builder(this)
                    .setTitle("Delete ${book.name}?")
                    .setPositiveButton("Ok") { _, _ ->
                        viewModel.deleteBook(book.id)
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.cancel()
                    }
                    .show() }
            .show()
    }

}