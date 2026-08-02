package com.liveChat.chat_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatAppApplication {

	public static void main(String[] args) {
		System.setProperty("spring.data.mongodb.uri", "mongodb+srv://anuragkumar78702685001_db_user:bitsA1YoqQIuf3Y5@cluster0.zp5aemb.mongodb.net/chatApp_db?retryWrites=true&w=majority&appName=Cluster0");

		SpringApplication.run(ChatAppApplication.class, args);
	}
}