package com.diarpy.webquizengine.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 12/11/2020
 */

@Entity
@Table(name = "users_roles")
public class UserRole implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "\\w{3,}@[a-z]+\\.[a-z]{2,3}")
    @Column(name = "username")
    private String email;

    @Column(name = "role")
    private String role;

    public UserRole() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
