package com.rafaelvincensi.backend.connection

import com.rafaelvincensi.backend.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "connections")
class Connection(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    val requester: User,

    @ManyToOne
    @JoinColumn(name = "addressee_id", nullable = false)
    val addressee: User,

    @Enumerated(EnumType.STRING)
    @Column(name = "status_request", nullable = false)
    var statusRequest: StatusRequest,

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime,

    var respondedAt: LocalDateTime? = null
)