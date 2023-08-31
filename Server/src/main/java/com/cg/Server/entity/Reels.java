package com.cg.Server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Reels")
@Data
@NamedQueries({
        //@NamedQuery(name = "Reels.getAllRecords", query = "select new com.cg.Server.wrapper.ReelsWrapper(r.pos1, r.pos2, r.pos3) from Reels r")
        @NamedQuery(name = "Reels.getAllRecords", query = "select r.pos1, r.pos2, r.pos3 from Reels r")
})
public class Reels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pos1")
    private String pos1;

    @Column(name = "pos2")
    private String pos2;

    @Column(name = "pos3")
    private String pos3;
}
