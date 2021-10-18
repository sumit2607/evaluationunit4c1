package com.example.expensemanager

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName =   "transaction")
data class transactionDataHold (

    @PrimaryKey(autoGenerate = true) val id: Int,
    val label : String,
    val amount:Double,
    val  description: String){
}