package com.example.apps65test.data

import android.content.res.Resources
import androidx.room.*
import com.example.apps65test.R
import com.example.apps65test.utilities.EMPLOYEE_TABLE_NAME
import java.util.*


@Entity(tableName = EMPLOYEE_TABLE_NAME,
    indices = [Index(value = ["first_name", "last_name", "date_of_birth"], unique = true)]
)

data class Employee(

    @ColumnInfo(name = "first_name")
    val firstName: String?,

    @ColumnInfo(name = "last_name")
    val lastName: String?,

    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: String,

    @ColumnInfo(name = "employee_avatar")
    val employeeAvatar: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
//TODO сделать calcAge самостоятельной функцией?
     fun calcAge(): String {
         return if (dateOfBirth == "-") "Неизвестен"
         else {
             val splitDob = dateOfBirth.split(".").map { it.toInt() }
             val dob = Calendar.getInstance()
             val today = Calendar.getInstance()

             dob[splitDob[2], splitDob[1]] = splitDob[0]

             var age = today[Calendar.YEAR] - dob[Calendar.YEAR]

             if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
                 age--
             }
             //TODO Кидается експепшен "android.content.res.Resources$NotFoundException: Plural resource ID #0x7f0e0001 quantity=20 item=other"
             val postFix = Resources.getSystem().getQuantityString(R.plurals.plurals_age, age, age)
             "$age$postFix"
         }
     }

//TODO перенести на plurals. plurals в EmploeePOJO ?
    private fun generatePostfix(age: Int): String {
        val lastChar = age % 10
        return if (age in 11..14) "лет" else if (lastChar == 1) "год" else if (lastChar in 2..4) "года" else "лет"
    }
}
