package com.example.walkietalkie.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.walkietalkie.data.local.dao.DeviceDao
import com.example.walkietalkie.data.local.dao.MessageDao
import com.example.walkietalkie.data.local.dao.UserDao
import com.example.walkietalkie.data.local.dao.VibrationDao
import com.example.walkietalkie.data.local.entity.DeviceEntity
import com.example.walkietalkie.data.local.entity.MessageEntity
import com.example.walkietalkie.data.local.entity.UserEntity
import com.example.walkietalkie.data.local.entity.VibrationEventEntity
import com.example.walkietalkie.domain.model.ConnectionQuality
import com.example.walkietalkie.domain.model.MessageStatus
import com.example.walkietalkie.domain.model.TransportType
import com.example.walkietalkie.domain.model.VibrationPattern

class Converters {
    @TypeConverter fun fromStatus(v: MessageStatus): String = v.name
    @TypeConverter fun toStatus(v: String): MessageStatus = MessageStatus.valueOf(v)

    @TypeConverter fun fromTransport(v: TransportType): String = v.name
    @TypeConverter fun toTransport(v: String): TransportType = TransportType.valueOf(v)

    @TypeConverter fun fromQuality(v: ConnectionQuality): String = v.name
    @TypeConverter fun toQuality(v: String): ConnectionQuality = ConnectionQuality.valueOf(v)

    @TypeConverter fun fromPattern(v: VibrationPattern): String = v.name
    @TypeConverter fun toPattern(v: String): VibrationPattern = VibrationPattern.valueOf(v)
}

@Database(
    entities = [UserEntity::class, DeviceEntity::class, MessageEntity::class, VibrationEventEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun deviceDao(): DeviceDao
    abstract fun messageDao(): MessageDao
    abstract fun vibrationDao(): VibrationDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "walkietalkie.db"
                ).build().also { INSTANCE = it }
            }
    }
}
