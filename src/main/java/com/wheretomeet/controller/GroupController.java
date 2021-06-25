package com.wheretomeet.controller;

import java.util.Optional;
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

import com.wheretomeet.model.DistanceDuration;
import com.wheretomeet.model.Group;
import com.wheretomeet.model.User;
import com.wheretomeet.model.Venue;
import com.wheretomeet.model.Timeframe;
import com.wheretomeet.repository.GroupRepository;
import com.wheretomeet.repository.UserRepository;

@RestController
public class GroupController {

	final static Logger log = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	private UserRepository userRepo;

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
	
	@GetMapping("/group/{id}/timeframes")
	public ResponseEntity<?> getTimeframes(@PathVariable("id") String id) {
		Group group = groupRepo.findById(id).orElse(null);
		if(group != null) {
			return ResponseEntity.ok().body(group.getTimeframes());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/groups/create/{uid}")
	public ResponseEntity<?> createGroup(@PathVariable("uid") String userId, @RequestBody Group group) {
		User owner = userRepo.findById(userId).orElse(null); 
		if(owner != null && group != null) {
			try {
				group.initGroupVenues();
				group.setGroupOwner(owner);
				group.addGroupMember(owner);
			}
			catch(NullPointerException e) {
				log.info(e.getMessage());
			}
			groupRepo.save(group);
			return new ResponseEntity<Group>(group, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/group/{groupId}/add/{userId}")
	public ResponseEntity<?> addGroupMember(@PathVariable("groupId") String groupId, @PathVariable("userId") String userId) {
		User newMember = userRepo.findById(userId).orElse(null);
		Group group = groupRepo.findById(groupId).orElse(null);
		if(newMember != null && group != null) {
			boolean added = group.addGroupMember(newMember);
			if(!added) {
				return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
			}
			groupRepo.save(group);
			return new ResponseEntity<Group>(group, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/group/{groupId}/remove/{userId}")
	public ResponseEntity<?> removeGroupMember(@PathVariable("groupId") String groupId, @PathVariable("userId") String userId) {
		User newMember = userRepo.findById(userId).orElse(null);
		Group group = groupRepo.findById(groupId).orElse(null);
		if(newMember != null && group != null) {
			try {
				boolean removed = group.removeGroupMember(newMember);
				if(!removed) {
					return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND); 
				}
			}
			catch(NullPointerException e) {
				log.info(e.getMessage());
			}
			groupRepo.save(group);
			return new ResponseEntity<Group>(group, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}


	@DeleteMapping("/group/id/{id}")
	public ResponseEntity<String> deleteGroup(@PathVariable("id") String id) {
		groupRepo.deleteById(id);
		return new ResponseEntity<String>("Group deleted", HttpStatus.OK);
	}

	@GetMapping("/group/{id}/locations")
	public ResponseEntity<?> getGroupVenues(@PathVariable("id") String id) {
		Group g = groupRepo.findById(id).orElse(null);
		if(g != null) {
			return ResponseEntity.ok().body(g.getGroupVenues());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/group/{id}/add/location") 
	public ResponseEntity<?> addVenueToGroup(@PathVariable("id") String id, @RequestBody Venue venue) {
		Group g = groupRepo.findById(id).orElse(null);
		if(g != null && g.getGroupVenues() != null) {
			venue.initUserDistanceDuration();
			g.addGroupVenue(venue.getVenueId(), venue);
			groupRepo.save(g);
			return new ResponseEntity<String>("Venue location added successfully!", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/group/{id}/remove/location") 
	public ResponseEntity<?> removeVenueFromGroup(@PathVariable("id") String id, @RequestBody Venue venue) {
		Group g = groupRepo.findById(id).orElse(null);
		if(g != null) {
			g.removeGroupVenue(venue.getVenueId());
			return new ResponseEntity<String>("Venue location removed successfully!",HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/group/{id}/add/timeframe") 
	public ResponseEntity<?> addTimeframeToGroup(@PathVariable("id") String id, @RequestBody Timeframe timeframe) {
		Group g = groupRepo.findById(id).orElse(null);
		if(g != null) {
			g.addTimeframe(timeframe);
			groupRepo.save(g);
			return new ResponseEntity<String>("Timeframe added successfully",HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/group/{id}/remove/timeframe") 
	public ResponseEntity<?> removeTimeframeToGroup(@PathVariable("id") String id, @RequestBody Timeframe timeframe) {
		Group g = groupRepo.findById(id).orElse(null);
		if(g != null) {
			g.removeTimeframe(timeframe);
			groupRepo.save(g);
			return new ResponseEntity<String>("Timeframe added successfully",HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("group/{id}/distanceDuration/{userId}")
	public ResponseEntity<?> addUserDistanceDurationToVenue(@PathVariable("id") String groupId, @RequestBody DistanceDuration distanceDuration) {

		Group g = groupRepo.findById(groupId).orElse(null);

		if(g != null) {
			String venueId = distanceDuration.getPlaceId();
			Venue venue = g.getGroupVenues().get(venueId);
			if(venue != null) {
				venue.storeUserDistanceDurationToVenue(distanceDuration.getUserId(), distanceDuration);
				return ResponseEntity.ok().body("added user's distance to place");
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}