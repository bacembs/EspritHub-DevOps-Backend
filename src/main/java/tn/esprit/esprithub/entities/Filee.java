package tn.esprit.esprithub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Filee  {
    @Id
    @Column(name = "fileID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileID;

    @Column(name = "file_name")
    private String filename;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Column(name = "file")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "internshipId")
    @JsonIgnore
    private Internship internship;
}