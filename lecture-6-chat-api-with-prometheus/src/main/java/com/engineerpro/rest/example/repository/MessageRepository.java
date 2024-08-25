package com.engineerpro.rest.example.repository;

import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.engineerpro.rest.example.model.Message;

import io.micrometer.core.annotation.Timed;

@Repository
@Timed(histogram = true)
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(value = "select * from message where channel_id = :channelId order by id desc LIMIT :limit", nativeQuery = true)
    List<Message> listLatestMessages(@Param("channelId") String channelId, @Param(value = "limit") int limit);

    @Query(value = "select * from message where id < :id and channel_id = :channelId order by id desc LIMIT :limit", nativeQuery = true)
    List<Message> listMessagesBeforeId(@Param("id") long id, @Param("channelId") String channelId,
            @Param(value = "limit") int limit);

    @Query(value = "select * from message where id > :id and channel_id = :channelId order by id asc LIMIT :limit", nativeQuery = true)
    List<Message> listMessagesAfterId(@Param("id") long id, @Param("channelId") String channelId,
            @Param(value = "limit") int limit);
}
