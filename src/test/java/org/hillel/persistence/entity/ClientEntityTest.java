package org.hillel.persistence.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientEntityTest {



    @Test
    void setEmail() {
        ClientEntity client = new ClientEntity("Bob", "Bobber", "bob@email.com");
        assertTrue(client.isValid());
        assertThrows(IllegalArgumentException.class, () -> new ClientEntity("Bob", "Bobber", " "));
        assertThrows(IllegalArgumentException.class, () -> new ClientEntity(" ", "Bobber", " "));
        assertThrows(IllegalArgumentException.class, () -> new ClientEntity("", "Bobber", " "));
        assertThrows(IllegalArgumentException.class, () -> new ClientEntity("Bob", "Bobber", "bob"));
        assertThrows(IllegalArgumentException.class, () -> new ClientEntity("Bob", "Bobber", "@email.com"));
        assertThrows(IllegalArgumentException.class, () -> new ClientEntity("Bob", "Bobber", "@#$%^&(-@email.com"));
        assertThrows(IllegalArgumentException.class, () -> new ClientEntity("Bob", "Bobber", "bob@email.com "));
        assertThrows(IllegalArgumentException.class, () -> new ClientEntity("Bob", "Bobber", " bob@email.com"));
    }
}