package com.example.apps65test.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apps65test.R
import com.example.apps65test.data.Employee
import com.example.apps65test.utilities.CircleTransform
import com.squareup.picasso.Picasso

class EmployeesListAdapter(private val resApp: Resources) :
    ListAdapter<Employee, EmployeesListAdapter.EmployeeViewHolder>(EMPLOYEE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {

        val current = getItem(position)
        val age = current.calcAge()
        var ageString = "Неизвестен"
        if (age > 0)
            ageString = resApp.getQuantityString(R.plurals.plurals_age, age, age)


        holder.bind(
            "${current.firstName} ${current.lastName}\nВозраст: $ageString",
            current.employeeAvatar,
            current.id
        )
    }

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val employeeItemView: TextView = itemView.findViewById(R.id.employeeTextView)
        private val empAvatarView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(text: String?, avatarUrl: String?, id: Int?) {
            employeeItemView.text = text
            itemView.tag = id
            if (avatarUrl.isNullOrEmpty()) {
                empAvatarView.setImageResource(R.drawable.ic_baseline_person)
            } else {
                Picasso.get().load(avatarUrl).transform(CircleTransform()).into(empAvatarView)
            }
        }

        companion object {
            fun create(parent: ViewGroup): EmployeeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_employee_item, parent, false)
                return EmployeeViewHolder(view)
            }
        }
    }

    companion object {
        private val EMPLOYEE_COMPARATOR = object : DiffUtil.ItemCallback<Employee>() {
            override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
                return oldItem == newItem
            }
        }
    }

}