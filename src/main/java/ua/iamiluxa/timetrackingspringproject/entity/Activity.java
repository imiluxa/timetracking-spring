package ua.iamiluxa.timetrackingspringproject.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @SequenceGenerator(name = "actIdSeq", sequenceName = "act_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actIdSeq")
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String goal;

    @Column(name = "duration")
    private Long duration;

    @Enumerated(value = EnumType.STRING)
    private ActivityStatus status;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "activities", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<User> users;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private Set<Request> Requests;
}
