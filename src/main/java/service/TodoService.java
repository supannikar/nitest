package service;

import com.nitest.dao.TodoDao;
import com.nitest.model.TodoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("todoService")
public class TodoService {

    @Autowired
    private TodoDao todoDao;

    public void save(TodoModel task){
        todoDao.save(task);
    }
    public TodoModel getTodo(Integer id){
        return todoDao.findById(id);
    }

    public List<TodoModel> listAll(){
        return todoDao.listAll();
    }

    public void delete(Integer id){
        todoDao.delete(id);
    }
}
