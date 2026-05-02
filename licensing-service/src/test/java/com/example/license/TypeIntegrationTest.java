package com.example.license;

import com.example.license.model.License;
import com.example.license.repository.LicenseRepository;
import com.example.license.repository.TypeRepository;
import com.example.license.service.LicenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import com.example.license.model.License;
import com.example.license.model.Type;
import com.example.license.repository.LicenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TypeIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TypeRepository typeRepository;

    private Type newType;
    private Type existingType;

    @BeforeEach
    public void setUp() {
        newType = new Type();
        newType.setName("bebebeb");
    }

    @Test
    public void testCreateType() {
        Type savedType = entityManager.persist(newType);
        entityManager.flush();

        Type foundType = typeRepository.findById(savedType.getId()).orElse(null);
        assertThat(foundType).isNotNull();
        assertThat(foundType.getName()).isEqualTo("bebebeb");
    }

    @Test
    public void testReadType() {
        Type savedType = entityManager.persist(newType);
        entityManager.flush();

        Type foundType = typeRepository.findById(savedType.getId()).orElse(null);
        assertThat(foundType).isNotNull();
    }

    @Test
    public void testUpdateType() {
        Type savedType = entityManager.persist(newType);
        entityManager.flush();

        Type foundType = typeRepository.findById(savedType.getId()).orElse(null);
        foundType.setName("��� ���� ���");
        Type updatedType = entityManager.persist(foundType);
        entityManager.flush();

        assertThat(updatedType.getName()).isEqualTo("bebebeb);
    }

    @Test
    public void testDeleteType() {
        Type savedType = entityManager.persist(newType);
        entityManager.flush();

        typeRepository.delete(savedType);
        Type deletedType = typeRepository.findById(savedType.getId()).orElse(null);
        assertThat(deletedType).isNull();
    }
}
