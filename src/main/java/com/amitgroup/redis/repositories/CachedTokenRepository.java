package com.amitgroup.redis.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.amitgroup.redis.entities.CachedTokenDTO;
import java.util.List;

public interface CachedTokenRepository extends CrudRepository<CachedTokenDTO, String> {
    List<CachedTokenDTO> findByCurrentUserId(Long userId);
    //find by token
    @Query("SELECT t FROM CachedTokenDTO t WHERE t.token = ?1")
    CachedTokenDTO findByToken(String token);
}
