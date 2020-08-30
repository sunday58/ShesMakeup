package com.sundaydavid989.shesmakeup.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupTypeConverter


@Database(
    exportSchema = false,
    entities = [MakeupItem::class],
    version = 1
)
@TypeConverters(MakeupTypeConverter::class)
abstract class MakeupDatabase : RoomDatabase() {
        abstract fun makeupItem(): MakeupItem

    companion object {
        @Volatile private var instance: MakeupDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            MakeupDatabase::class.java,
            "makeup.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}