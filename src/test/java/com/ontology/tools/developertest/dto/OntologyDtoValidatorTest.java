package com.ontology.tools.developertest.dto;

import com.ontology.tools.developertest.dto.OntologyDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OntologyDtoValidatorTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidOntologyDto() {
        OntologyDto ontologyDto = new OntologyDto();
        ontologyDto.setOntologyId("123");
        ontologyDto.setTitle("Sample Title");
        ontologyDto.setDescription("Sample Description");

        Set<ConstraintViolation<OntologyDto>> violations = validator.validate(ontologyDto);

        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidOntologyDto() {
        OntologyDto ontologyDto = new OntologyDto();

        Set<ConstraintViolation<OntologyDto>> violations = validator.validate(ontologyDto);

        assertEquals(3, violations.size());
    }
}
