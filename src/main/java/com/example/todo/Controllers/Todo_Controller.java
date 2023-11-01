package com.example.todo.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.todo.entity.Todo;
import com.example.todo.repository.repository;

@Controller
public class Todo_Controller {
	@Autowired
	private repository todoRepo;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("todoForm", new Todo());
		List<Todo> todos = todoRepo.findAll();
		model.addAttribute("listTodos", todos);
		 return "index";
	}

	// to save record
	@PostMapping("/saveTodo")
	public String saveTodo(@ModelAttribute("todoForm") Todo todoForm) {
		// Convert the TodoForm to a Todo entity and save it to the repository
		Todo todo = new Todo();
		todo.setTitle(todoForm.getTitle());
		todo.setStatus(todoForm.getStatus());
		todo.setDescription(todoForm.getDescription());
		todoRepo.save(todo);
		return "redirect:/";
	}

	// to delete record from database
	@GetMapping("/deleteTodo/{id}")
	public String deleteTodo(@PathVariable(value = "id") long id) {
		// call delete employee method
		todoRepo.deleteById((int) id);
		return "redirect:/";
	}

	// for updatebyId
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
		Optional<Todo> optional = todoRepo.findById((int) id);
		Todo todo = null;
		if (optional.isPresent()) {
			todo = optional.get();
			model.addAttribute("todoForm", todo);
			return "update_todo";
		} else {
			throw new RuntimeException(" todo not found for id :: " + id);
		}
	}
}
