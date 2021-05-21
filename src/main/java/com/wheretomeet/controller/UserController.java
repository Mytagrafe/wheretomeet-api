package com.wheretomeet.controller;

import java.util.Optional;
import java.lang.Iterable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wheretomeet.model.User;
import com.wheretomeet.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUserDetails(@PathVariable("id") long id) {
		Optional<User> user = userRepo.findById(id);
		if(user.isPresent()) {
			return ResponseEntity.ok().body(user.get());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/users")
	public Iterable<User> getAllUserDetails() {
		return userRepo.findAll();
	}

	@PostMapping("/users")
	public ResponseEntity<String> createUser(@RequestBody User user) {
		userRepo.save(user);
		return new ResponseEntity<String>("User created", HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
		userRepo.deleteById(id);
		return new ResponseEntity<String>("User deleted", HttpStatus.OK);
	}
}