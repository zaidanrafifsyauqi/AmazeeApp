import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.amikomchat.model.User

class DBhelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "AmikomChatDB"
        private const val TABLE_USERS = "users"
        private const val KEY_ID = "id"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
        private const val KEY_USERNAME = "username"
        private const val KEY_IMAGE_PROFILE = "imageProfile"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_USERS($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$KEY_USERNAME TEXT, $KEY_EMAIL TEXT, $KEY_PASSWORD TEXT, $KEY_IMAGE_PROFILE TEXT)")
        db?.execSQL(createTableQuery)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE $TABLE_USERS ADD COLUMN $KEY_IMAGE_PROFILE TEXT")
        }
    }

    fun addUser(username: String, email: String, password: String): Long {
        val db = this.writableDatabase

        // Pengecekan apakah pengguna sudah terdaftar
        if (checkUser(email)) {
            return -1
        }

        val values = ContentValues()
        values.put(KEY_USERNAME, username) // Simpan username
        values.put(KEY_EMAIL, email)
        values.put(KEY_PASSWORD, password)
        val id = db.insert(TABLE_USERS, null, values)
        db.close()

        // Simpan status login ke SharedPreferences
        saveLoginStatus(true)

        return id
    }




    fun getUser(email: String): User? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS, arrayOf(KEY_ID, KEY_EMAIL, KEY_PASSWORD, KEY_USERNAME),
            "$KEY_EMAIL=?", arrayOf(email), null, null, null, null
        )
        return if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            val storedEmail = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
            val storedPassword = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))
            val storedUsername = cursor.getString(cursor.getColumnIndex(KEY_USERNAME))
            User(id, storedEmail, storedPassword, storedUsername)
        } else {
            null
        }
    }


    fun checkUser(email: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS, arrayOf(KEY_ID),
            "$KEY_EMAIL=?", arrayOf(email), null, null, null, null
        )
        return cursor != null && cursor.moveToFirst()
    }
    fun saveImageProfile(email: String, imageProfile: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_IMAGE_PROFILE, imageProfile)

        return db.update(TABLE_USERS, values, "$KEY_EMAIL=?", arrayOf(email))
    }

    fun getImageProfile(email: String): String? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS, arrayOf(KEY_IMAGE_PROFILE),
            "$KEY_EMAIL=?", arrayOf(email), null, null, null, null
        )
        return if (cursor != null && cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndex(KEY_IMAGE_PROFILE))
        } else {
            null
        }
    }

    private fun saveLoginStatus(status: Boolean) {
        val sharedPreferences = context.getSharedPreferences("login_status", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", status)
        editor.apply()
    }
}
