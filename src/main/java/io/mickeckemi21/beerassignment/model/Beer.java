package io.mickeckemi21.beerassignment.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Basic
    @Column(nullable = false, unique = true)
    protected String name;

    @Basic
    @Column(nullable = false, unique = true, length = 1024)
    protected String description;

    @Basic
    @Column(nullable = false, unique = true)
    protected String internalId;

    @ElementCollection
    protected Collection<Double> temperatures = new ArrayList<>();

}
