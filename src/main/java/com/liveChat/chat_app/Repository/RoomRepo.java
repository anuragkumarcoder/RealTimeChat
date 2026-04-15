package com.liveChat.chat_app.Repository;

import com.liveChat.chat_app.Entities.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepo extends MongoRepository<Room,String> {
    Room findByRoomId(String roomId);
}
