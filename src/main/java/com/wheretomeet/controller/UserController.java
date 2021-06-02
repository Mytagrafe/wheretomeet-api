package com.wheretomeet.controller;

import java.util.Optional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wheretomeet.model.FriendsList;
import com.wheretomeet.model.User;
import com.wheretomeet.repository.FriendsListRepository;
import com.wheretomeet.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private FriendsListRepository friendsRepo;

	@GetMapping("/user/id/{id}")
	public ResponseEntity<?> getUserDetails(@PathVariable("id") String accountId) {
		Optional<User> user = userRepo.findById(accountId);
		if(user.isPresent()) {
			return ResponseEntity.ok().body(user.get());
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

	@PostMapping("/users")
	public ResponseEntity<String> createUser(@RequestBody User user) {
		userRepo.save(user);
		String generatedId = user.getUserId();
		friendsRepo.save(new FriendsList(generatedId));
		return new ResponseEntity<String>("User " + generatedId + " created", HttpStatus.OK);
	}

	@DeleteMapping("/user/id/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") String accountId) {
		userRepo.deleteById(accountId);
		return new ResponseEntity<String>("User " + accountId + " deleted", HttpStatus.OK);
	}

	@GetMapping("/user/{id}/groups")
	public ResponseEntity<?> getAllUsersGroups(@PathVariable("id") String userId) {
		User user = userRepo.findById(userId).orElse(null);
		if(user != null) {
			return ResponseEntity.ok().body(user.getGroups());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}