package com.zhaldak.myapplication


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zhaldak.myapplication.datadase.AppDatabase
import com.zhaldak.myapplication.datadase.ContactGroup
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ContactGroupsViewModel(app: Application) : AndroidViewModel(app), CoroutineScope {
    var groups: MutableLiveData<List<ContactGroup>> = MutableLiveData()
    private var database: AppDatabase = AppDatabase.getDatabase(app)

    private var groupsDao = database.getGroupsDao()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private  val job: Job = Job()


    fun requestGroups() {
        launch(Dispatchers.Main) {
            groups.value = withContext(Dispatchers.Default) {
                groupsDao.getAll()
            }
        }
    }


    fun saveGroup(group: ContactGroup) {
        launch(Dispatchers.Default) {
            groupsDao.insert(group)
        }
        requestGroups()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
