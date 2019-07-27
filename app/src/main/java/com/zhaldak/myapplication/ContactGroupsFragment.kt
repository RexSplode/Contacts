package com.zhaldak.myapplication

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhaldak.myapplication.datadase.ContactGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.contact_groups_fragment.*
import androidx.appcompat.app.AlertDialog
import com.zhaldak.myapplication.datadase.ContactGroupWithContacts
import kotlinx.android.synthetic.main.add_group_dialog.view.*


class ContactGroupsFragment : Fragment() {

    companion object {
        fun newInstance() = ContactGroupsFragment()
    }

    private lateinit var viewModel: ContactGroupsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.contact_groups_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ContactGroupsViewModel::class.java)
        viewModel.requestGroups()

        recycler.layoutManager = LinearLayoutManager(context)
        viewModel.groups.observe(this, Observer<List<ContactGroupWithContacts>> {
            val mainActivity = activity as MainActivity
            val addContactViewModel = ViewModelProviders.of(mainActivity).get(AddContactViewModel::class.java)
            recycler.adapter = ContactGroupsAdapter(it, mainActivity, addContactViewModel)
        })

        activity?.fab?.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                createAndShowGroupDialog()
            }
        }



    }

    private fun createAndShowGroupDialog() {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(R.string.add_contact_group)


        val dialogView = layoutInflater
            .inflate(R.layout.add_group_dialog, null)

        builder.setView(dialogView)
        builder.setPositiveButton(R.string.ok) { dialogInterface, _ ->
            val color = dialogView.colorPickerView.selectedColor
            val title = dialogView.editTitle.text.toString()
            val description = dialogView.editDescription.text.toString()

            val group = ContactGroup(name = title, description = description, color = color)
            viewModel.saveGroup(group)
            dialogInterface.dismiss()


        }
        builder.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

}
