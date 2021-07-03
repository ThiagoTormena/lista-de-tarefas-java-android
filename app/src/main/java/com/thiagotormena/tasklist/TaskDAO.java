package com.thiagotormena.tasklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements InterfaceTaskDAO{
    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public TaskDAO(Context context) {
        DbHelper db = new DbHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    @Override
    public boolean save(Task task) {

        ContentValues cv = new ContentValues();
        cv.put("taskName", task.getTaskName());

        try {
            write.insert(DbHelper.TABLE_TASKS, null, cv);
            Log.i("INFO", "Task salva com sucesso");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao salvar task");
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Task task) {
        ContentValues cv = new ContentValues();
        cv.put("taskName", task.getTaskName());
        try {
            String [] args = {task.getId().toString()};
            write.update(DbHelper.TABLE_TASKS, cv, "id=?", args);
            Log.i("INFO", "Task alterada com sucesso.");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao alterar Task.");
            return false;
        }


        return true;
    }

    @Override
    public boolean delete(Task task) {
        try {
            String [] args = {task.getId().toString()};
            write.delete(DbHelper.TABLE_TASKS, "id=?", args);
            Log.i("INFO", "Task excluída com sucesso.");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao excluir Task.");
            return false;
        }

        return true;
    }

    @Override
    public List<Task> list() {
        List<Task> tasklist = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABLE_TASKS + " ;";
        Cursor c = read.rawQuery(sql, null);

        while(c.moveToNext()){
            Task task = new Task();
            Long id = c.getLong(c.getColumnIndex("id"));
            String taskName = c.getString(c.getColumnIndex("taskName"));
            task.setId(id);
            task.setTaskName(taskName);

            tasklist.add(task);
            Log.i("taskDAO", task.getTaskName());
        }

        return tasklist;
    }
}
