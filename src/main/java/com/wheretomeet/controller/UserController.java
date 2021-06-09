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
import com.wheretomeet.model.GroupsList;
import com.wheretomeet.model.User;
import com.wheretomeet.repository.FriendsListRepository;
import com.wheretomeet.repository.GroupsListRepository;
import com.wheretomeet.repository.UserRepository;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
public class UserController {

	final static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private FriendsListRepository friendsRepo;

	@Autowired
    private GroupsListRepository groupsListRepo;

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
		// initialize and store user's data lists
		friendsRepo.save(new FriendsList(generatedId));
		groupsListRepo.save(new GroupsList(generatedId));
		return new ResponseEntity<String>("User " + generatedId + " created", HttpStatus.OK);
	}

	@DeleteMapping("/user/id/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") String accountId) {
		userRepo.deleteById(accountId);
		friendsRepo.deleteById(accountId);
		groupsListRepo.deleteById(accountId);
		return new ResponseEntity<String>("User " + accountId + " deleted", HttpStatus.OK);
	}
}