package com.vitorbetmann.restaurant.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// JPA
@Entity
@DiscriminatorValue("customer")
// Lombok
@NoArgsConstructor
@Getter
@Setter
public class Customer extends User {
}
