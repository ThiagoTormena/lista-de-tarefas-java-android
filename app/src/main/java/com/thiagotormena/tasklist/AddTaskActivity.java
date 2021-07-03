package com.thiagotormena.tasklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddTaskActivity extends AppCompatActivity {
    private TextInputEditText txtInput;
    private Task actualTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        txtInput = findViewById(R.id.txtInput);

        actualTask = (Task) getIntent().getSerializableExtra("selectedTask");

        if (actualTask != null){
            txtInput.setText( actualTask.getTaskName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.saveItem:
                TaskDAO taskDAO = new TaskDAO(getApplicationContext());

                if (actualTask != null) {
                    String taskName = txtInput.getText().toString();
                    if(!taskName.isEmpty()){
                        Task task = new Task();
                        task.setTaskName(taskName);
                        task.setId(actualTask.getId());

                        if (taskDAO.update(task)){
                            finish();
                            Toast.makeText(this, "Task alterada com sucesso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Erro ao alterar Task", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    String taskName = txtInput.getText().toString();
                    if(!taskName.isEmpty()){
                        Task task = new Task();
                        task.setTaskName(taskName);
                        if (taskDAO.save(task)){
                            finish();
                            Toast.makeText(this, "Task salva com sucesso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Erro ao salvar Task", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
};