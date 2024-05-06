package tn.esprit.esprithub.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection
    private List<String> images;


    Float priceHousing;

    @ManyToOne
    private User owner;
    @JsonManagedReference
    @JsonIgnore
    @ManyToMany
    private List<User> renters;
    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "housing" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visit> visitsHousing;

    @OneToOne(cascade= CascadeType.PERSIST)
@JsonIgnore
    Transaction transaction;
    @JsonIgnore
    @OneToMany(mappedBy = "housing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailabilityTimeSlot> availabilityTimeSlots = new ArrayList<>();



}
