package com.educative.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private @NotNull String name;
    private @NotNull String surname;
    private @NotNull String telephoneNumber;
    private @NotNull String email;
    private @NotNull String address;

    @JsonIgnore
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<Purchase> purchases;

}
