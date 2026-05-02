package com.example.license;

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
public class LicenseIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LicenseRepository licenseRepository;

    private License newLicense;
    private Type existingType;

    @BeforeEach
    public void setUp() {
        newLicense = new License();
        newLicense.setNamePo("bebebeb");
        newLicense.setStartDate(LocalDate.now());
        newLicense.setEndDate(LocalDate.now().plusYears(1));
        newLicense.setCount(100);

        existingType = entityManager.find(Type.class, 2);
        newLicense.setIdType(existingType);
    }

    @Test
    public void testCreateLicense() {
        License savedLicense = entityManager.persist(newLicense);
        entityManager.flush();

        License foundLicense = licenseRepository.findById(savedLicense.getId()).orElse(null);
        assertThat(foundLicense).isNotNull();
        assertThat(foundLicense.getNamePo()).isEqualTo("bebebeb");
    }

    @Test
    public void testReadLicense() {
        License savedLicense = entityManager.persist(newLicense);
        entityManager.flush();

        License foundLicense = licenseRepository.findById(savedLicense.getId()).orElse(null);
        assertThat(foundLicense).isNotNull();
    }

    @Test
    public void testUpdateLicense() {
        License savedLicense = entityManager.persist(newLicense);
        entityManager.flush();

        License foundLicense = licenseRepository.findById(savedLicense.getId()).orElse(null);
        foundLicense.setCount(200);
        License updatedLicense = entityManager.persist(foundLicense);
        entityManager.flush();

        assertThat(updatedLicense.getCount()).isEqualTo(200);
    }

    @Test
    public void testDeleteLicense() {
        License savedLicense = entityManager.persist(newLicense);
        entityManager.flush();

        licenseRepository.delete(savedLicense);
        License deletedLicense = licenseRepository.findById(savedLicense.getId()).orElse(null);
        assertThat(deletedLicense).isNull();
    }
}
