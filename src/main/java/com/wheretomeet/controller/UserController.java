package com.wheretomeet.controller;

import java.util.Optional;
import java.lang.Iterable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wheretomeet.model.AccountId;
import com.wheretomeet.model.User;
import com.wheretomeet.repository.UserRepository;

@RestController
public class UserController {
	final static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepo;

	@GetMapping("/user/id/{id}")
	public ResponseEntity<?> getUserDetails(@PathVariable("id") String accountId) {
		log.debug("id: {}", accountId);
		String[] s = accountId.split("#");
		if(s.length == 2) {
			log.debug("id: {} tag: {}", s[0], s[1]);
			Optional<User> user = userRepo.findById(new AccountId(s[0], s[1]));
			if(user.isPresent()) {
				return ResponseEntity.ok().body(user.get());
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/user/email/{email}")
	public ResponseEntity<?> getUserViaEmail(@PathVariable("email") String email) {
		Optional<User>  user = userRepo.findByEmail(email);
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

	@DeleteMapping("/user/id/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") String accountId) {
		String[] s = accountId.split("#");
		userRepo.deleteById(new AccountId(s[0], s[1]));
		return new ResponseEntity<String>("User deleted", HttpStatus.OK);
	}
}