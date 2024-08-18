package com.ks.EventManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@Table(name = "user_conf")
@AllArgsConstructor
@NoArgsConstructor
public class UserConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private long user_id;

    @Column(name = "confirmation_token")
    private String confirmation_token;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "expired_at")
    private Date expired_at;
}
