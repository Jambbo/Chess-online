package com.kukuxer.chess.domain.request;

import com.kukuxer.chess.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requests")
@Builder
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}
