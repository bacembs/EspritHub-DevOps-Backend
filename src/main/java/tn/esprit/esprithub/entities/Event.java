package tn.esprit.esprithub.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long eventId;
    String titleEvent;
    String descriptionEvent;
    LocalDateTime dateEvent;
    String locationEvent;
    String categoryEvent;
    String imgEvent;
    int capacityEvent;
    @Enumerated(EnumType.STRING)
    Estatus statusEvent;

    @OneToMany(mappedBy = "events")
    Set<Participants> participants;
}
