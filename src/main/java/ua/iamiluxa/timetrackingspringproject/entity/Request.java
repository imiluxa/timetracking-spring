package ua.iamiluxa.timetrackingspringproject.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @SequenceGenerator(name = "reqIdSeq", sequenceName = "req_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reqIdSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Activity activity;

    @Column(name = "action")
    @Enumerated(value = EnumType.STRING)
    private RequestActions requestAction;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private RequestStatus status;

}
