package com.nitest.web.api.v1.controller;

import com.nitest.model.TodoModel;
import com.nitest.web.api.v1.mapper.TodoResponseMapper;
import com.nitest.web.api.v1.transport.ResponseTransport;
import com.nitest.web.api.v1.transport.TodoTransport;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiVerb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.TodoService;

import javax.validation.Valid;
import java.util.List;

@Api(name = "Todo", description = "Todo List API")
@RestController
@RequestMapping(value = "/api/nitest/v1/todos", produces = MediaType.APPLICATION_JSON_VALUE)
public class TodoController {

    @Autowired
    private TodoService todoService;

    @ApiMethod(
            path = "/v1/todos", verb = ApiVerb.POST,
            description = "Add a new todo into the list",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@ApiBodyObject @RequestBody @Valid TodoTransport todoTransport) {

        TodoModel todoModel = new TodoModel();
        todoModel.setSubject(todoTransport.getSubject());
        todoModel.setDetail(todoTransport.getDetail());
        todoModel.setStatus(todoTransport.getStatus());

        todoService.save(todoModel);
        return new ResponseEntity<>(todoModel, HttpStatus.CREATED);
    }

    @ApiMethod(
            path = "/v1/todos/{id}", verb = ApiVerb.PUT,
            description = "Edit existing todo list",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@ApiBodyObject @RequestBody @Valid TodoTransport todoTransport,
                                 @PathVariable @ApiPathParam(name = "id") Integer id) {

        TodoModel findTodo = todoService.getTodo(id);
        if(findTodo == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        findTodo.setSubject(todoTransport.getSubject());
        findTodo.setDetail(todoTransport.getDetail());
        findTodo.setStatus(todoTransport.getStatus());

        todoService.save(findTodo);
        return new ResponseEntity<>(findTodo, HttpStatus.ACCEPTED);
    }

    @ApiMethod(
            path = "/v1/todos/{id}", verb = ApiVerb.GET,
            description = "Edit existing todo list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable @ApiPathParam(name = "id") Integer id) {

        TodoModel findTodo = todoService.getTodo(id);
        if(findTodo == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(findTodo, HttpStatus.OK);
    }

    @ApiMethod(
            path = "/v1/todos", verb = ApiVerb.GET,
            description = "Edit existing todo list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity listAll() {
        List<TodoModel> listAll = todoService.listAll();
        List<TodoTransport> transports = new TodoResponseMapper().map(listAll);
        ResponseTransport<TodoTransport> responseTransport = new ResponseTransport<>(transports.size(), transports);

        return new ResponseEntity<>(responseTransport, HttpStatus.OK);
    }


    @ApiMethod(
            path = "/v1/todos/{id}", verb = ApiVerb.DELETE,
            description = "Edit existing todo list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable @ApiPathParam(name = "id") Integer id) {
        todoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
