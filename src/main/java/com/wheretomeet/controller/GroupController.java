package com.wheretomeet.controller;

import java.util.Optional;
import java.lang.Iterable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wheretomeet.model.User;
import com.wheretomeet.model.Group;
import com.wheretomeet.repository.GroupRepository;

@RestController
public class GroupController {

	@Autowired
	private GroupRepository groupRepo;

	@GetMapping("/groups/{id}")
	public ResponseEntity<?> getGroupDetails(@PathVariable("id") long id) {
		Optional<Group> group = groupRepo.findById(id);
		if(group.isPresent()) {
			return ResponseEntity.ok().body(group.get());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/groups")
	public Iterable<Group> getAllGroupDetails() {
		return groupRepo.findAll();
	}

	@PostMapping("/groups")
	public ResponseEntity<String> createGroup(Group g) {
		groupRepo.save(g);
		return new ResponseEntity<>("group created", HttpStatus.OK);
	}

	@DeleteMapping("/groups/{id}")
	public ResponseEntity<String> deleteGroup(@PathVariable("id") long id) {
		groupRepo.deleteById(id);
		return new ResponseEntity<String>("Group deleted", HttpStatus.OK);
	}

}