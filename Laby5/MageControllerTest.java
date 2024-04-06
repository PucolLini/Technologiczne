package org.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class MageControllerTest {

    @Test
    void find() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        Mage mage = new Mage("Yennefer", 420);

        Mockito.when(repository.find(anyString())).thenAnswer(variable -> {
            String name = variable.getArgument(0);
            if(name.equals(mage.getName())){
                return Optional.of(mage);
            }else{
                return Optional.empty();
            }
        });

        MageController controller = new MageController(repository);

        assertEquals(mage.toString(),controller.find(mage.getName()));
        assertEquals("not found",controller.find("Lokomotywa"));

    }

    @Test
    void delete() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        Mage mage = new Mage("Ciri", 29);

        Mockito.doAnswer(variable ->{
            String name = variable.getArgument(0);
            if(name.equals(mage.getName())){
                return null;
            }else{
                throw new IllegalArgumentException("Value for deletion hasn't been found.");
            }
        }).when(repository).delete(anyString());

        MageController controller = new MageController(repository);

        assertEquals("done",controller.delete(mage.getName()));
        assertEquals("not found",controller.delete("Lolek"));
    }

    @Test
    void save_ValidMage_ShouldReturnDone() {
        // Arrange
        MageRepository repository = Mockito.mock(MageRepository.class);
        Mage mage = new Mage("Petunia", 47);
        Mockito.doAnswer(variable -> null)
                .when(repository)
                .save(any(Mage.class));

        MageController controller = new MageController(repository);

        // Act
        String result = controller.save(mage.getName(), String.valueOf(mage.getLevel()));

        // Assert
        assertEquals("done", result);
    }

    @Test
    void save_DuplicateMage_ShouldReturnBadRequest() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        Mage mage = new Mage("Petunia", 47);
        List<Mage> mageList = new ArrayList<>();
        mageList.add(mage); // Adding mage to simulate existing mage in the repository
        Mockito.doAnswer(variable -> {
            Mage mageArgument = variable.getArgument(0);
            if (!mageList.stream().anyMatch(v -> v.getName().equals(mageArgument.getName()))) {
                mageList.add(mage);
                return null;
            } else {
                throw new IllegalArgumentException("Mage is already in the repository.");
            }
        }).when(repository).save(any(Mage.class));

        MageController controller = new MageController(repository);

        // Act
        String result = controller.save(mage.getName(), String.valueOf(mage.getLevel()));

        // Assert
        assertEquals("bad request", result);
    }


}