package com.vitorbetmann.restaurant.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// JPA
@Entity
@DiscriminatorValue("owner")
// Lombok
@NoArgsConstructor
@Getter
@Setter
public class Owner extends User {
}
