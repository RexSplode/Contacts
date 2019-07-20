package com.zhaldak.myapplication

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.zhaldak.myapplication.datadase.ContactGroupWithContacts
import kotlinx.android.synthetic.main.contact_group_card.view.*

class ContactGroupsAdapter(private val items: List<ContactGroupWithContacts>,
                           val contect: Context,
                           private val addContactViewModel: AddContactViewModel) :
    RecyclerView.Adapter<GroupsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        return GroupsViewHolder(LayoutInflater.from(contect)
            .inflate(R.layout.contact_group_card, parent, false))
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        val groupWithContacts = items[position]
        val group = groupWithContacts.group!!

        holder.titleTxt?.text = group.name
        holder.descriptionTxt?.text = group.description
        val hexColor = "#${java.lang.Integer.toHexString(group.color)}"
        holder.card.setCardBackgroundColor(Color.parseColor(hexColor))

        holder.countTxt.text = "${groupWithContacts.contacts.size}"

        holder.buttonAdd.setOnClickListener {
            val navController = it.findNavController()
            addContactViewModel.groupId = group.id!!
            navController.navigate(R.id.addContactFragment)
        }

    }


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

}

class GroupsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val titleTxt = view.titleTxt
    val descriptionTxt = view.descriptionTxt
    val card = view.card
    val buttonAdd = view.imageAdd
    val countTxt = view.countTxt
}