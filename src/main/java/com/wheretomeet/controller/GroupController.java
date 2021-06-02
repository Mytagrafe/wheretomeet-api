package com.wheretomeet.controller;

import java.util.Optional;
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
import com.wheretomeet.model.Venue;
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

	@GetMapping("/group/{id}/locations")
	public ResponseEntity<?> getGroupVenues(@PathVariable("id") String id, @RequestBody Venue venue) {
		Group g = groupRepo.findById(id).orElse(null);
		if(g != null) {
			return ResponseEntity.ok().body(g.getGroupVenues());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/group/{id}/add/location") 
	public ResponseEntity<?> addVenueToGroup(@PathVariable("id") String id, @RequestBody Venue venue) {
		Group g = groupRepo.findById(id).orElse(null);
		if(g != null) {
			g.addGroupVenue(venue);
			return new ResponseEntity<String>("Venue location added successfully!",HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/group/{id}/remove/location") 
	public ResponseEntity<?> removeVenueToGroup(@PathVariable("id") String id, @RequestBody Venue venue) {
		Group g = groupRepo.findById(id).orElse(null);
		if(g != null) {
			g.removeGroupVenue(venue);
			return new ResponseEntity<String>("Venue location removed successfully!",HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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