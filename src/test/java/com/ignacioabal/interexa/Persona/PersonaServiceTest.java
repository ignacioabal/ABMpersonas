package com.ignacioabal.interexa.Persona;

import org.apache.coyote.Response;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class PersonaServiceTest {

    @MockBean
    PersonaRepository personaRepository;

    @Autowired
    PersonaService personaService;

    @Nested
    class getPersonaTests{

        @Test
        void shouldReturnPersonaIfFound(){
            long inputId = 1L;
            Persona mockPersona = new Persona(1L,"Juan","Perez","25148684",true);

            Mockito.when(personaRepository.findById(inputId)).thenReturn(Optional.of(mockPersona));

            ResponseEntity<Persona> expectedReturnedResponseEntity = new ResponseEntity<>(mockPersona, HttpStatus.OK);

            ResponseEntity<Persona> actualReturnedResponseEntity = personaService.getPersona(inputId);

            assertEquals(expectedReturnedResponseEntity,actualReturnedResponseEntity);
        }

        @Test
        void shouldReturnNotFoundIfDoesNotExist(){
            long inputId = 1L;

            Mockito.when(personaRepository.findById(inputId)).thenReturn(Optional.empty());

            ResponseEntity<Persona> expectedReturnedResponseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

            ResponseEntity<Persona> actualReturnedResponseEntity = personaService.getPersona(inputId);

            assertEquals(expectedReturnedResponseEntity,actualReturnedResponseEntity);
        }


    }

    @Nested
    class createPersonaTests{

        @Test
        void shouldReturnCreatedPersonaIfCreated(){
            Persona mockPersona = new Persona(1L,"Juan","Perez","25148684",true);

            Persona inputPersona = new Persona("Juan","Perez","25148684",true);

            Mockito.when(personaRepository.existsByDni(inputPersona.getDni())).thenReturn(false);
            Mockito.when(personaRepository.save(inputPersona)).thenReturn(mockPersona);


            ResponseEntity<Persona> expectedReturnedResponse = new ResponseEntity<>(mockPersona,HttpStatus.CREATED);

            ResponseEntity<Persona> actualReturnedResponse = personaService.createPersona(inputPersona);

            assertEquals(expectedReturnedResponse,actualReturnedResponse);
        }

        @Test
        void shouldReturnConflictIfPersonaWithDniAlreadyExists(){
            Persona mockPersona = new Persona(1L,"Juan","Perez","25148684",true);
            Persona inputPersona = new Persona("Juan","Perez","25148684",true);

            Mockito.when(personaRepository.existsByDni(mockPersona.getDni())).thenReturn(true);

            Exception exception = assertThrows(ResponseStatusException.class,()->{
                personaService.createPersona(inputPersona);
            });

            String expectedMessage = "409 CONFLICT \"Ya existe Persona con ese DNI.\"";
            String actualMessage = exception.getMessage();

            assertEquals(expectedMessage,actualMessage);
        }

    }

    @Nested
    class deletePersonaTests{

        @Test
        void shouldReturnOkIfPersonaIsDeleted(){
            long inputId = 1L;

            Mockito.when(personaRepository.existsById(inputId)).thenReturn(true);
            Mockito.doNothing().when(personaRepository).deleteById(inputId);

            ResponseEntity<Persona> expectedReturnedResponse = new ResponseEntity<>(HttpStatus.OK);
            ResponseEntity<Persona> actualReturnedResponse = personaService.deletePersona(inputId);

            assertEquals(expectedReturnedResponse,actualReturnedResponse);
        }

        @Test
        void shouldReturnNotFoundIfDoesNotExist(){
            long inputId = 1L;

            Mockito.when(personaRepository.existsById(inputId)).thenReturn(false);

            assertThrows(ResponseStatusException.class,()->{personaService.deletePersona(inputId);});
        }

    }

    @Nested
    class modifyPersonaTests{

        @Test
        void shouldReturnPersonaIfModified(){
            long inputId = 1L;
            Persona inputPersona = new Persona("Juan","Perez","25148684",true);

            Persona mockPersona = new Persona(1L,"Juan","Perez","25148684",true);
            Mockito.when(personaRepository.existsById(inputId)).thenReturn(true);
            Mockito.when(personaRepository.save(inputPersona)).thenReturn(mockPersona);

            ResponseEntity<Persona> expectedReturnedResponse = new ResponseEntity<>(mockPersona,HttpStatus.OK);
            ResponseEntity<Persona> actualReturnedResponse = personaService.modifyPersona(inputId,inputPersona);

            assertEquals(expectedReturnedResponse,actualReturnedResponse);
        }

        @Test
        void shouldReturnNotFoundIfDoesNotExist(){
            long inputId = 1L;
            Persona inputPersona = new Persona("Juan","Perez","25148684",true);

            Mockito.when(personaRepository.existsById(inputId)).thenReturn(false);

            assertThrows(ResponseStatusException.class,()->{personaService.modifyPersona(inputId,inputPersona);});
        }
    }

}
