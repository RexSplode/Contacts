package com.zhaldak.myapplication


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zhaldak.myapplication.datadase.AppDatabase
import com.zhaldak.myapplication.datadase.ContactGroup
import kotlinx.coroutines.*

class ContactGroupsViewModel(app: Application) : AndroidViewModel(app) {
    var groups: MutableLiveData<List<ContactGroup>> = MutableLiveData()
    private var database: AppDatabase = AppDatabase.getDatabase(app)
    private var groupsDao = database.getGroupsDao()

    fun requestGroups() {
        GlobalScope.launch(Dispatchers.Main) {
            groups.value = withContext(Dispatchers.Default) {
                groupsDao.getAll()
            }
        }
    }


    fun saveGroup(group: ContactGroup) {
        GlobalScope.launch(Dispatchers.Default) {
            groupsDao.insert(group)
        }
        requestGroups()
    }
}
