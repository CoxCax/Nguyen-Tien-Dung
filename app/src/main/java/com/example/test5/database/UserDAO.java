package com.example.test5.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.example.test5.model.UserModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserDAO extends SQLiteOpenHelper {

    public static final String DB_NAME = "users_db";
    public static final int DB_VERSION = 3;
    public static final String TABLE_NAME = "users";
    public static final String ID_COL = "id";
    public static final String NAME_COL = "name";
    public static final String EMAIL_COL = "email";
    public static final String PASSWORD_COL = "password";
    public static final String CREATED_AT_COL = "created_at";
    public static final String UPDATED_AT_COL = "updated_at";


    public UserDAO(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " VARCHAR(60) NOT NULL, "
                + PASSWORD_COL + " VARCHAR(200) NOT NULL, "
                + EMAIL_COL  + " VARCHAR(60)  NOT NULL, "
                + CREATED_AT_COL + " DATETIME , "
                + UPDATED_AT_COL + " DATETIME )";
        sqLiteDatabase.execSQL(query);// tao bang//
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);// xoa bang neu co loi
        onCreate(sqLiteDatabase); //tao lai bang
    }
    @RequiresApi( api = Build.VERSION_CODES.O)
    public long addNewUser(String username, String password, String email) {

        // Using SimpleDateFormat for compatibility with older API levels
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        String dateNow = sdf.format(new Date()); // Get the current date and time

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, username);
        values.put(PASSWORD_COL, password);
        values.put(EMAIL_COL, email);
        values.put(CREATED_AT_COL, dateNow); // Set the formatted date for creation time

        long add = sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return add;
    }
    @SuppressLint("Range")
    public UserModel getInforuser(String email, String password) {
        UserModel user = new UserModel();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            // Tìm kiếm dựa trên email và mật khẩu
            String[] columns = {ID_COL, NAME_COL, EMAIL_COL, PASSWORD_COL};
            String condition = EMAIL_COL + " =? AND " + PASSWORD_COL + " =?";  // Điều kiện tìm kiếm
            String[] params = {email, password};  // Dữ liệu truyền vào
            Cursor cursor = db.query(TABLE_NAME, columns, condition, params, null, null, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                // Đặt thông tin vào model
                user.setId(cursor.getInt(cursor.getColumnIndex(ID_COL)));
                user.setName(cursor.getString(cursor.getColumnIndex(NAME_COL)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL_COL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD_COL)));
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    public boolean checkUsernameEmail(String data, int type){
        boolean checking = false;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = { ID_COL, NAME_COL, EMAIL_COL };
            String condition = (type == 1) ? (NAME_COL + " =? ") : (EMAIL_COL + " =? ");
            String[] params = { data };
            Cursor cursor = db.query(TABLE_NAME, columns, condition, params, null, null, null);
            if (cursor.getCount() > 0) {
                checking = true;
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return checking;
}
}
