package org.hillel.persistence.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientEntityTest {



    @Test
    void setEmail() {
        UserEntity client = new UserEntity("Bob", "Bobber", "bob@email.com");
        assertTrue(client.isValid());
        assertThrows(IllegalArgumentException.class, () -> new UserEntity("Bob", "Bobber", " "));
        assertThrows(IllegalArgumentException.class, () -> new UserEntity(" ", "Bobber", " "));
        assertThrows(IllegalArgumentException.class, () -> new UserEntity("", "Bobber", " "));
        assertThrows(IllegalArgumentException.class, () -> new UserEntity("Bob", "Bobber", "bob"));
        assertThrows(IllegalArgumentException.class, () -> new UserEntity("Bob", "Bobber", "@email.com"));
        assertThrows(IllegalArgumentException.class, () -> new UserEntity("Bob", "Bobber", "@#$%^&(-@email.com"));
        assertThrows(IllegalArgumentException.class, () -> new UserEntity("Bob", "Bobber", "bob@email.com "));
        assertThrows(IllegalArgumentException.class, () -> new UserEntity("Bob", "Bobber", " bob@email.com"));
    }
}