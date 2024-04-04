package app.bookstore.socialbookstore.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public enum Role {
	USER("User"),
    ADMIN("Admin");

	@Id
    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}