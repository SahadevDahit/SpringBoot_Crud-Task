package com.example.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.Todo;

public interface repository extends JpaRepository<Todo, Integer>{

}
