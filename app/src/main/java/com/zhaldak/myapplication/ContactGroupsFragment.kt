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
import kotlinx.android.synthetic.main.contact_groups_fragment.*


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
        viewModel.insertTestInformation()

        recycler.layoutManager = LinearLayoutManager(context)
        viewModel.groups.observe(this, Observer<List<ContactGroup>> {
            recycler.adapter = ContactGroupsAdapter(it, context!!)
        })



    }

}
