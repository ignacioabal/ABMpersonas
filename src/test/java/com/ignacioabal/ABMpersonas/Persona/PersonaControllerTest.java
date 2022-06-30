package com.ignacioabal.ABMpersonas.Persona;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonaControllerTest {

    @MockBean
    private PersonaService personaService;

    @Autowired
    private PersonaController personaController;

    @Nested
    class getPersonaTests{

        @Test
        void shouldReturnPersonaIfExists(){
            Persona mockPersona = new Persona(1L,"Juan","Perez","25148684",true);

            Mockito.when(personaService.getPersona(Mockito.anyLong())).thenReturn(new ResponseEntity<>(mockPersona, HttpStatus.OK));

            Persona returnedPersona = personaController.getPersona(1).getBody();

            assertEquals(returnedPersona,mockPersona);
        }

        @Test
        void shouldReturnNotFoundIfItDoesntExist(){
            Persona mockPersona = new Persona(1L,"Juan","Perez","25148684",true);

            Mockito.when(personaService.getPersona(Mockito.anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

            ResponseEntity<Persona> returnedPersona = personaController.getPersona(1);

            assertEquals(returnedPersona.getStatusCode(),HttpStatus.NOT_FOUND);
        }

    }

    @Nested
    class createPersonaTests{

        @Test
        void shouldReturnPersonaIfCreated(){
            Persona mockPersona = new Persona(1L,"Juan","Perez","25148684",true);

            Persona inputPersona = new Persona("Juan","Perez","25148684",true);

            Mockito.when(personaService.createPersona(inputPersona)).thenReturn(new ResponseEntity<>(mockPersona, HttpStatus.CREATED));

            ResponseEntity<Persona> returnedPersonaResponse = personaController.createPersona(inputPersona);

            assertEquals(returnedPersonaResponse.getStatusCode(),HttpStatus.CREATED);
            assertEquals(returnedPersonaResponse.getBody(),mockPersona);
        }

    }

    @Nested
    class deletePersonaTests{

        @Test
        void shouldReturnOKIfDeleted(){
            long inputId = 1L;

            Mockito.when(personaService.deletePersona(inputId)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

            ResponseEntity<Persona> returnedPersonaResponse = personaController.deletePersona(inputId);

            assertEquals(returnedPersonaResponse.getStatusCode(),HttpStatus.OK);
        }

        @Test
        void shouldReturnNOTFOUNDIfDoesNotExist(){
            long inputId = 1L;

            Mockito.when(personaService.deletePersona(inputId)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

            ResponseEntity<Persona> returnedPersonaResponse = personaController.deletePersona(inputId);

            assertEquals(returnedPersonaResponse.getStatusCode(),HttpStatus.NOT_FOUND);
        }

    }


    @Nested
    class modifyPersona{

        @Test
        void shouldReturnPersonaIfModified(){
            Persona mockPersona = new Persona(1L,"Juan","Perez","25148684",true);

            Persona inputPersona = new Persona("Juan","Perez","25148684",true);
            long inputId = 1L;

            Mockito.when(personaService.modifyPersona(inputId,inputPersona)).thenReturn(new ResponseEntity<>(mockPersona, HttpStatus.OK));

            ResponseEntity<Persona> returnedPersonaResponse = personaController.modifyPersona(inputId,inputPersona);

            assertEquals(returnedPersonaResponse.getStatusCode(),HttpStatus.OK);
            assertEquals(returnedPersonaResponse.getBody(),mockPersona);
        }

        @Test
        void shouldReturnNotFoundIfItDoesNotExist(){
            Persona inputPersona = new Persona("Juan","Perez","25148684",true);
            long inputId = 1L;

            Mockito.when(personaService.modifyPersona(inputId,inputPersona)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

            ResponseEntity<Persona> returnedPersonaResponse = personaController.modifyPersona(inputId,inputPersona);

            assertEquals(returnedPersonaResponse.getStatusCode(),HttpStatus.NOT_FOUND);
        }

    }

}
