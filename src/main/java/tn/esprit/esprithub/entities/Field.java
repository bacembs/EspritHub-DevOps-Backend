package tn.esprit.esprithub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Field implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fieldId;
    String nameField;
    String descriptionField;
    String locationField;
    int capacityField;
    @Enumerated(EnumType.STRING)
    TypeF typeField;

    @OneToMany(mappedBy = "fields")
 //   @JsonIgnore
    Set<Reservation> reservations;

}
