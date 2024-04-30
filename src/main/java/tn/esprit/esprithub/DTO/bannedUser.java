package tn.esprit.esprithub.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.entities.Transaction;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class bannedUser {
    String username;
Long  idtransaction;
    Feedback feedback;
}
