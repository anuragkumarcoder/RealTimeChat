package com.liveChat.chat_app.Controller;

import com.liveChat.chat_app.Entities.Message;
import com.liveChat.chat_app.Entities.Room;
import com.liveChat.chat_app.Repository.RoomRepo;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomRepo roomRepo;
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId){
        if(roomRepo.findByRoomId(roomId)!=null){
            return ResponseEntity.badRequest().body("Room already exist");
        }
        Room room=new Room();
        room.setRoomId(roomId);
        roomRepo.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);


    }
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId){
        Room room=roomRepo.findByRoomId(roomId);
        if(room==null){
            return ResponseEntity.badRequest().body("room not found");
        }
        return ResponseEntity.ok(room);
    }
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessage(@PathVariable String roomId,@RequestParam(
                                                                value = "page",
                                                                defaultValue = "0",
                                                                required = false
                                                        ) int page,

                                                    @RequestParam(
                                                            value = "size",
                                                            defaultValue = "20",
                                                            required = false
                                                    ) int size){
        Room room=roomRepo.findByRoomId(roomId);
        if(room==null){
            return ResponseEntity.badRequest().build();
        }
        List<Message> messages=room.getMessages();
        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);
        List<Message> paginatedMessages = messages.subList(start, end);
        return ResponseEntity.ok(paginatedMessages);
    }
}
