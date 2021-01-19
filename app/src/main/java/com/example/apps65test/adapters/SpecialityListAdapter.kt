package com.example.apps65test.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apps65test.R
import com.example.apps65test.data.Speciality

class SpecialityListAdapter :
    ListAdapter<Speciality, SpecialityListAdapter.SpecialityViewHolder>(SPECIALITY_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialityViewHolder {
        return SpecialityViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SpecialityViewHolder, position: Int) {

        val current = getItem(position)
        holder.bind( current.specialityName )
    }

    class SpecialityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val specialityItemView: TextView = itemView.findViewById(R.id.employeeCardTextView)

        fun bind(text: String?) {
            specialityItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): SpecialityViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_emp_card_item, parent, false)
                return SpecialityViewHolder(view)
            }
        }
    }

    companion object {
        private val SPECIALITY_COMPARATOR = object : DiffUtil.ItemCallback<Speciality>() {
            override fun areItemsTheSame(oldItem: Speciality, newItem: Speciality): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Speciality, newItem: Speciality): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}