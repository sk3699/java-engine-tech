package com.cg.Server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "WinPatternTable")
@Data
@NamedQueries({
        //@NamedQuery(name = "WinPattern.getAllRecords", query = "select w.pattern, w.value, w.expectedChance from WinPattern w"),
        @NamedQuery(name = "WinPattern.getValueByPattern", query = "select w.value from WinPattern w where w.pattern = :patern and w.occurrences = :occurrences")
})
public class WinPattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pattern")
    private String pattern;
    @Column(name = "valuee")
    private double value;
    @Column(name = "expectedChance")
    private double expectedChance;
    @Column(name = "occurrences")
    private int occurrences;
}
