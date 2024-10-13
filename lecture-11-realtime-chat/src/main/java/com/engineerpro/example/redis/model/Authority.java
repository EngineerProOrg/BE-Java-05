package com.engineerpro.example.redis.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Data
@Entity
public class Authority implements GrantedAuthority {

    @Id
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    private UUID id;

    public String authority;
}
