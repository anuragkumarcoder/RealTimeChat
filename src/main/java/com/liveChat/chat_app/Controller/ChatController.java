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
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(@DestinationVariable String roomId, @RequestBody MessageRequest request) {
        Room room = roomRepo.findByRoomId(request.getRoomId());

        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());

        if (room != null) {
            // FIX: Double-check null safety for the message list
            if (room.getMessages() == null) {
                room.setMessages(new java.util.ArrayList<>());
            }
            room.getMessages().add(message);
            roomRepo.save(room);
            return message;
        }

        // FIX: Just return null or log the error instead of throwing a RuntimeException
        System.out.println("Room not found: " + roomId);
        return null;
    }
}