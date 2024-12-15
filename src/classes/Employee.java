package classes;

import annotations.Column;
import annotations.Table;

import java.util.UUID;

@Table(name = "users")
public class Employee {

    // region Поля

    @Column(name = "id", primaryKey = true)
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    // endregion

    // region Геттеры-Сеттеры

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // endregion

    // region Конструкторы

    public Employee(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // endregion
}
