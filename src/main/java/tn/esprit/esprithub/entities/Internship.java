package tn.esprit.esprithub.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Internship implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long internshipId;
    String titleInternship;
    String companyInternship;
    String durationInternship;
    String descriptionInternship;
    String locationInternship;
    String skillsRequiredInternship;
    String responsibilitiesInternship;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime deadlineInternship;
    String toEmail;
    String subject;
    String body;





    @OneToMany(mappedBy = "internship", cascade = CascadeType.ALL)
    private List<Filee> files = new ArrayList<>();

    @ManyToOne // Relation Many-to-One avec User
    User user;


    public void setFiles() {
        this.files = files;

    }



}
