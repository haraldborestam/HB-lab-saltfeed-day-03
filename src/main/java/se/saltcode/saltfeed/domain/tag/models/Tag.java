package se.saltcode.saltfeed.domain.tag.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Tag(String name) {
        this.name = name;
    }
    public Tag() {}

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Tag)) {
            return false;
        }

        return ((Tag) o).getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
}
