package com.example.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Todo")

public class Todo {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;

	    @Column(name = "title")
	    private String title;
	    
	    @Column(name = "status")
	    private String status;
	    
	    @Column(name = "description")
	    private String description;

		public Todo() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Todo(long id, String title, String status, String description) {
			super();
			this.id = id;
			this.title = title;
			this.status = status;
			this.description = description;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	
}
