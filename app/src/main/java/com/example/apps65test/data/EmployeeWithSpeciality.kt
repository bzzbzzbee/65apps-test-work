package com.example.apps65test.data

import androidx.room.*
import com.example.apps65test.utilities.EMPLOYEE_SPECIALITY_CROSSREF_TABLE_NAME


@Entity(
    tableName = EMPLOYEE_SPECIALITY_CROSSREF_TABLE_NAME,
    primaryKeys = ["employee_id", "speciality_id"],
    foreignKeys = [
        ForeignKey(
        entity = Employee::class,
        parentColumns = ["id"],
        childColumns = ["employee_id"],
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
    ),
        ForeignKey(
        entity = Speciality::class,
        parentColumns = ["id"],
        childColumns = ["speciality_id"],
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
    )],
    indices = [Index("employee_id"), Index("speciality_id")]
)

data class EmployeeSpecialityCrossRef(
    @ColumnInfo(name = "employee_id")
    val employeeId: Int,

    @ColumnInfo(name = "speciality_id")
    val specialityId: Int
)

data class EmployeeWithSpeciality (
    @Embedded
    val employee: Employee,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = Speciality::class,
        associateBy = Junction(
            EmployeeSpecialityCrossRef::class,
            parentColumn = "employee_id",
            entityColumn = "speciality_id"
        )
    )
    var specialities: List<Speciality> = listOf()
)

data class SpecialityWithEmployee(
    @Embedded
    val speciality: Speciality,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = Employee::class,
        associateBy = Junction(
            EmployeeSpecialityCrossRef::class,
            parentColumn = "speciality_id",
            entityColumn = "employee_id"
        )
    )
    var employees: List<Employee> = listOf()
)