package org.toyota.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "Users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username",
                "email"
        })
})
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Cannot be empty.")
    @NotNull(message = "Cannot be null.")
    @Size(max = 40)
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Cannot be empty.")
    @NotNull(message = "Cannot be null.")
    @Size(max = 40)
    @Column(name = "username")
    private String username;

    @NaturalId(mutable = true)
    @NotBlank(message = "Cannot be empty.")
    @NotNull(message = "Cannot be null.")
    @Size(max = 40)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Cannot be empty.")
    @NotNull(message = "Cannot be null.")
    @Column(name = "password")
    private String password;

    @Column(name = "active")
    @NotNull(message = "Cannot be null.")
    private Boolean active;

    private Set<ERoles> roles = new HashSet<>();

    @OneToMany
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    public Set<ERoles> getRoles()
    {
        return roles;
    }


}
