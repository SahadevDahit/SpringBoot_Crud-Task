package com.example.todo.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.entity.Todo;
import com.example.todo.repository.repository;

@RestController
public class Controller {
    @Autowired
    private repository todoRepo;

    @GetMapping("/")
    public String viewHome() {
        return "Thanks for visiting us";
    }

    // Retrieve all todos
    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos() {
        try {
            List<Todo> todos = todoRepo.findAll();
            return ResponseEntity.ok(todos);
        } catch (Exception e) {
            // Handle the exception and return an appropriate error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching todos.");
        }
    }

    // Get todo by Id
    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable long id) {
        try {
            Optional<Todo> todo = todoRepo.findById((int) id);
            if (todo.isPresent()) {
                return ResponseEntity.ok(todo.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo with ID " + id + " not found.");
            }
        } catch (Exception e) {
            // Handle the exception and return an appropriate error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the todo.");
        }
    }

    // To add a new todo
    @PostMapping("/todos")
    public ResponseEntity<?> saveTodo(@RequestBody Todo newTodo) {
        try {
            // Save employee to the database
            todoRepo.save(newTodo);
            return ResponseEntity.ok(newTodo);
        } catch (Exception e) {
            // Handle the exception and return an appropriate error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the todo.");
        }
    }

    // Update todo by Id using PUT request method
    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable long id, @RequestBody Todo updatedTodo) {
        try {
            Optional<Todo> todoOptional = todoRepo.findById((int) id);
            if (todoOptional.isPresent()) {
                Todo existingTodo = todoOptional.get();
                existingTodo.setTitle(updatedTodo.getTitle());
                existingTodo.setStatus(updatedTodo.getStatus());
                existingTodo.setDescription(updatedTodo.getDescription());
                todoRepo.save(existingTodo);
                return ResponseEntity.ok(existingTodo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo with ID " + id + " not found.");
            }
        } catch (Exception e) {
            // Handle the exception and return an appropriate error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the todo.");
        }
    }

    // Update todo by Id using PATCH request method
    @PatchMapping("/todos/{id}")
    public ResponseEntity<?> partialUpdateTodo(
        @PathVariable long id,
        @RequestBody Map<String, Object> partialTodo) {
        try {
            Optional<Todo> todoOptional = todoRepo.findById((int) id);
            if (todoOptional.isPresent()) {
                Todo existingTodo = todoOptional.get();

                // Update fields if they are provided in the request
                if (partialTodo.containsKey("title")) {
                    existingTodo.setTitle((String) partialTodo.get("title"));
                }
                if (partialTodo.containsKey("status")) {
                    existingTodo.setStatus((String) partialTodo.get("status"));
                }
                if (partialTodo.containsKey("description")) {
                    existingTodo.setDescription((String) partialTodo.get("description"));
                }

                todoRepo.save(existingTodo);
                return ResponseEntity.ok(existingTodo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo with ID " + id + " not found.");
            }
        } catch (Exception e) {
            // Handle the exception and return an appropriate error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the todo.");
        }
    }

    // Delete todo by Id
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable long id) {
        try {
            Optional<Todo> todoOptional = todoRepo.findById((int) id);
            if (todoOptional.isPresent()) {
                todoRepo.deleteById((int) id);
                return ResponseEntity.ok("Todo with ID " + id + " has been deleted.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo with ID " + id + " not found.");
            }
        } catch (Exception e) {
            // Handle the exception and return an appropriate error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the todo.");
        }
    }

    // Delete all todos
    @DeleteMapping("/todos")
    public ResponseEntity<?> deleteAllTodos() {
        try {
            // Delete all Todo records from the database
            todoRepo.deleteAll();
            return ResponseEntity.ok("All Todo records have been deleted.");
        } catch (Exception e) {
            // Handle the exception and return an appropriate error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting all todos.");
        }
    }
}
