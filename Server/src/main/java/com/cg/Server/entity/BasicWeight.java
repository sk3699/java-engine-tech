package com.cg.Server.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "BasicWeightTable")
@Data
@NamedQueries({
        @NamedQuery(name = "BasicWeight.getCumulativeChances", query = "select SUM(bw.chance) from BasicWeight bw"),
        @NamedQuery(name = "BasicWeight.getAllRecords", query = "select new com.cg.Server.wrapper.BasicWeightWrapper(bw.weightValue, bw.chance) from BasicWeight bw")
})
public class BasicWeight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weightValue")
    private double weightValue;

    @Column(name = "chance")
    private double chance;
}
