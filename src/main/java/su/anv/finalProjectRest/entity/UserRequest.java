package su.anv.finalProjectRest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_requests")
public class UserRequest {
    @Id
    @Column(name = "user_requests_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID  id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "request_id")
    private Request request;
}