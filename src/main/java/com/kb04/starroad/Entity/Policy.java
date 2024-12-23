package com.kb04.starroad.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "policy_seq", sequenceName = "policy_seq", allocationSize = 1)
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "policy_seq")
    private int no;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, length = 2000)
    private String explain;

    @Column(nullable = false, length = 50)
    private String tag;

    @Column(nullable = false, length = 5000)
    private String link;

    @Column(nullable = false)
    private Date endDate;
}
