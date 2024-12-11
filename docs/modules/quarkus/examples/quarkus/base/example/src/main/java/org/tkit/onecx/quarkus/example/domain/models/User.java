package org.tkit.onecx.quarkus.example.domain.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Database entity
 */
@Entity
@Table(name = "T_USER")
public class User {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
