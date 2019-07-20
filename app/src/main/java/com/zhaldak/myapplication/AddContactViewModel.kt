package com.zhaldak.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.zhaldak.myapplication.datadase.AppDatabase
import com.zhaldak.myapplication.datadase.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddContactViewModel(app: Application) : AndroidViewModel(app), CoroutineScope {
    private var database: AppDatabase = AppDatabase.getDatabase(app)
    var groupId: Int = 0

    private var contactsDao = database.getContactsDao()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private  val job: Job = Job()


    fun saveContact(contact: Contact) {
        launch(Dispatchers.IO){
            contactsDao.insert(contact)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
