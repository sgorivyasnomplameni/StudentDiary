import android.app.Application
import androidx.room.Room

class AApp : Application() {

    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()

        // Инициализация базы данных
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "student_diary_database" // название базы данных
        ).fallbackToDestructiveMigration() // если версия базы меняется, она будет пересоздаваться
            .build()
    }
}
