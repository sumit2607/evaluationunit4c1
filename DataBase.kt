package com.example.expensemanager

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase

@Database(entities = arrayOf(transactionDataHold::class), version = 1)
abstract class DataBase: RoomDatabase() {
    abstract  fun transactionDataobject() : transactionDataobject
}