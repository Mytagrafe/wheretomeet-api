package com.wheretomeet.controller;

import java.util.Optional;
import java.lang.Iterable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.wheretomeet.model.Group;
import com.wheretomeet.repository.GroupRepository;

@RestController
public class GroupController {

	final static Logger log = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	private GroupRepository groupRepo;

	@GetMapping("/group/id/{id}")
	public ResponseEntity<?> getGroupDetails(@PathVariable("id") String id) {
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
	public ResponseEntity<String> createGroup(@RequestBody Group group) {
		groupRepo.save(group);
		return new ResponseEntity<>("Group created", HttpStatus.OK);
	}

	@DeleteMapping("/group/id/{id}")
	public ResponseEntity<String> deleteGroup(@PathVariable("id") String id) {
		groupRepo.deleteById(id);
		return new ResponseEntity<String>("Group deleted", HttpStatus.OK);
	}
}