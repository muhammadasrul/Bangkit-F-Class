package com.muhammadfurqan.bangkitfclass.sqlite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.muhammadfurqan.bangkitfclass.sqlite.db.BookDatabaseManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class BookViewModel(application: Application): AndroidViewModel(application) {

    private val db = BookDatabaseManager(application)

    private var _bookList = MutableLiveData<List<BookModel>>()
    val bookList: LiveData<List<BookModel>> = _bookList

    fun getAllBook() {
        runBlocking(Dispatchers.IO) {
            _bookList.postValue(db.getData())
        }
    }

    fun addBook(title: String) {
        runBlocking(Dispatchers.IO) {
            db.saveData(title)
            _bookList.postValue(db.getData())
        }
    }

    fun editBook(id: Int, title: String) {
        runBlocking(Dispatchers.IO) {
            db.editData(id, title)
            _bookList.postValue(db.getData())
        }
    }

    fun deleteBook(id: Int) {
        runBlocking(Dispatchers.IO) {
            db.deleteData(id)
            _bookList.postValue(db.getData())
        }
    }

}