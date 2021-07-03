package com.thiagotormena.tasklist;

import java.util.List;

public interface InterfaceTaskDAO {
    public boolean save(Task task);
    public boolean update(Task task);
    public boolean delete(Task task);
    public List<Task> list();
}
