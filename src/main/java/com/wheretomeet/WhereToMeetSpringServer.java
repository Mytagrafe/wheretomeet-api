package com.wheretomeet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;



@SpringBootApplication
@Import(WhereToMeetMessengerConfig.class)
public class WhereToMeetSpringServer {

	public static void main(String[] args) {
		SpringApplication.run(WhereToMeetSpringServer.class, args);
	}

}
