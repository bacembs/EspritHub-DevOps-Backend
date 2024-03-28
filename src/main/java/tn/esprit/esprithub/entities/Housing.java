package tn.esprit.esprithub.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Housing implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long housingID;
    @Enumerated(EnumType.STRING)
    TypeH typeHousing;
    String descriptionHousing;
    String locationHousing;
    Boolean availabilityHousing;
    String imgHousing;
    Float priceHousing;

    @OneToOne(cascade= CascadeType.PERSIST)
    Transaction transaction;
}
