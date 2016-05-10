package com.nitest.web.api.v1.mapper;

import com.nitest.model.TodoModel;
import com.nitest.web.api.v1.transport.TodoTransport;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class TodoResponseMapper {
    public TodoTransport map(TodoModel todoModel){
        TodoTransport todoTransport = new TodoTransport();
        todoTransport.setId(todoModel.getId());
        todoTransport.setSubject(todoModel.getSubject());
        todoTransport.setDetail(todoModel.getDetail());
        todoTransport.setStatus(todoModel.getStatus());
        return todoTransport;
    }

    public List<TodoTransport> map(List<TodoModel> todoModels) {
        return todoModels.stream().map(this::map).collect(Collectors.toList());
    }
}
