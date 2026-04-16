package com.liveChat.chat_app.Controller;

import com.liveChat.chat_app.Entities.Message;
import com.liveChat.chat_app.Entities.Room;
import com.liveChat.chat_app.Payload.MessageRequest;
import com.liveChat.chat_app.Repository.RoomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatController {

    private final RoomRepo roomRepo;

    // When a message is sent to /app/sendMessage/{roomId}
    @MessageMapping("/sendMessage/{roomId}")
    // The response (the message) is sent to /topic/room/{roomId}
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request
    ) {
        // 1. Find the room from the database
        Room room = roomRepo.findByRoomId(request.getRoomId());

        // 2. Create the message entity from the request payload
        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());

        if (room != null) {
            room.getMessages().add(message);
            roomRepo.save(room);
        } else {

            throw new RuntimeException("Room not found!");
        }
         return message;
    }
}