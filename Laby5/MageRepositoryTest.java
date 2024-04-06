package org.example;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MageRepositoryTest {

    @Test
    void find() {
        MageRepository repository = new MageRepository();

        Mage mage = new Mage("Borys", 123);
        repository.save(mage);
        Optional<Mage> exists = repository.find(mage.getName());
        Optional<Mage> invalid = repository.find("paraparaprar");

        assert exists.isPresent();
        assert invalid.isEmpty();

        assertEquals(mage.getName(), exists.get().getName());
        assertEquals(mage.getLevel(), exists.get().getLevel());
    }

    @Test
    void delete() {
        MageRepository repository = new MageRepository();

        Mage mage = new Mage("Grzesio", 333);
        repository.save(mage);

        assertThrows(IllegalArgumentException.class, () -> repository.delete("BUMCYKCYK"));
        assertDoesNotThrow(() -> repository.delete(mage.getName()));
    }

    @Test
    void save() {
        MageRepository repository = new MageRepository();

        Mage mage = new Mage("Grzesio", 333);
        assertDoesNotThrow(()->repository.save(mage));
        assertThrows(IllegalArgumentException.class, ()->repository.save(mage));
    }
}