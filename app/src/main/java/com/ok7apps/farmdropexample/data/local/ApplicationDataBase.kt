package com.ok7apps.farmdropexample.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.ok7apps.farmdropexample.data.local.model.ProducerEntity

@Database(
    entities = [ProducerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDataBase : RoomDatabase() {

    companion object {

        private const val DATABASE = "database.db"

        fun newInstance(context: Context): ApplicationDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                ApplicationDataBase::class.java,
                DATABASE
            ).build()
        }
    }

    abstract fun dao(): LocalDao
}