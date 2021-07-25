package com.wheretomeet.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.wheretomeet.entity.Group;
import com.wheretomeet.model.DistanceDuration;
import com.wheretomeet.model.Venue;
import com.wheretomeet.model.Timeframe;
import com.wheretomeet.service.GroupService;

@RestController
public class GroupController {

	final static Logger log = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	private GroupService groupService;

	@GetMapping("/group/id/{id}")
	public ResponseEntity<?> getGroupDetails(@PathVariable("id") String id) {
		return new ResponseEntity<>(groupService.getGroupById(id), HttpStatus.OK);
	}
	
	@GetMapping("/group/{id}/timeframes")
	public ResponseEntity<?> getTimeframes(@PathVariable("id") String id) {
		return new ResponseEntity<>(groupService.getGroupTimeframes(id), HttpStatus.OK);
	}

	@PostMapping("/groups/create/{uid}")
	public ResponseEntity<?> createGroup(@PathVariable("uid") String userId, @RequestBody Group group) {
		String groupName = group.getGroupName();
		String pass = group.getGroupPassword();
		return new ResponseEntity<>(groupService.createGroup(userId, groupName, pass), HttpStatus.OK);
	}

	@PutMapping("/group/{groupId}/add/{userId}")
	public ResponseEntity<?> addGroupMember(@PathVariable("groupId") String groupId, @PathVariable("userId") String userId) {
		return new ResponseEntity<>(groupService.addUserToGroup(groupId, userId), HttpStatus.OK);
	}

	@PutMapping("/group/{groupId}/remove/{userId}")
	public ResponseEntity<?> removeGroupMember(@PathVariable("groupId") String groupId, @PathVariable("userId") String userId) {
		return new ResponseEntity<>(groupService.removeUserFromGroup(groupId, userId), HttpStatus.OK);
	}

	@DeleteMapping("/group/id/{id}")
	public ResponseEntity<?> deleteGroup(@PathVariable("id") String id) {
		groupService.deleteGroup(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/group/{id}/locations")
	public ResponseEntity<?> getGroupVenues(@PathVariable("id") String id) {
		return new ResponseEntity<>(groupService.getGroupVenues(id), HttpStatus.OK);
	}

	@PutMapping("/group/{id}/add/location") 
	public ResponseEntity<?> addVenueToGroup(@PathVariable("id") String id, @RequestBody Venue venue) {
		return new ResponseEntity<>(groupService.addVenueToGroup(id, venue), HttpStatus.OK);
	}

	@PutMapping("/group/{id}/remove/location") 
	public ResponseEntity<?> removeVenueFromGroup(@PathVariable("id") String id, @PathVariable("venueId") String venueId) {
		return new ResponseEntity<>(groupService.removeVenueFromGroup(id, venueId), HttpStatus.OK);
	}

	@PutMapping("/group/{id}/add/timeframe") 
	public ResponseEntity<?> addTimeframeToGroup(@PathVariable("id") String id, @RequestBody Timeframe timeframe) {
		return new ResponseEntity<>(groupService.addTimeframeToGroup(id, timeframe), HttpStatus.OK);
	}

	@PutMapping("/group/{id}/remove/timeframe") 
	public ResponseEntity<?> removeTimeframeToGroup(@PathVariable("id") String id, @RequestBody Timeframe timeframe) {
		return new ResponseEntity<>(groupService.removeTimeframeFromGroup(id, timeframe), HttpStatus.OK);
	}

	@PutMapping("group/{id}/distanceDuration/{userId}")
	public ResponseEntity<?> addUserDistanceDurationToVenue(@PathVariable("id") String groupId, @RequestBody DistanceDuration distanceDuration) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}