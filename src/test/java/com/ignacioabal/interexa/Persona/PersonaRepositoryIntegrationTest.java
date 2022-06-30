package com.ignacioabal.interexa.Persona;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PersonaRepositoryIntegrationTest {

    @Autowired
    PersonaRepository personaRepository;

    @Nested
    class existsByDniTests {
        @BeforeEach
        void setup(){
            Persona mockPersona = new Persona(1L,"Juan","Perez","25148684",true);

            personaRepository.save(mockPersona);
        }

        @Test
        void shouldReturnTrueIfPersonaWithDniExists() {
            String mockDni = "25148684";

            boolean expectedResult = true;
            boolean actualResult = personaRepository.existsByDni(mockDni);

           assertEquals(expectedResult,actualResult);
        }

        @Test
        void shouldReturnFalseIfPersonaWithDniDoesNotExist() {
            String mockDni = "45123123";

            boolean expectedResult = false;
            boolean actualResult = personaRepository.existsByDni(mockDni);

            assertEquals(expectedResult,actualResult);
        }

        @AfterEach
        void tearDown(){
            personaRepository.deleteAll();
        }
    }

}
