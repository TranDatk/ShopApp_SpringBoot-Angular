package com.dat.shopapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEnitity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname" ,length = 100, nullable = false)
    private String fullName;

    @Column(name = "phone_number" ,length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "address" ,length = 200)
    private String address;

    @Column(name = "password" ,length = 200)
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "facebook_account_id")
    private int facebookAccountId;

    @Column(name = "google_account_id")
    private int googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
