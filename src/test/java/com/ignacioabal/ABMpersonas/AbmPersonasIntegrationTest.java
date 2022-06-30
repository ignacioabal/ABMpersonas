package com.ignacioabal.ABMpersonas;


import com.ignacioabal.ABMpersonas.Persona.Persona;
import com.ignacioabal.ABMpersonas.Persona.PersonaRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AbmPersonasIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonaRepository personaRepository;

    @Nested
    class GETPersonaIntegrationTests{

        @AfterEach
        void tearDown() {
            personaRepository.deleteAll();
        }

        @Test
        void shouldReturnPersonaWithOkStatusCode() throws Exception {
            Persona testPersona = new Persona("Juan","Perez","25148684",true);
            personaRepository.save(testPersona);


            mockMvc.perform(get(format("/%o",testPersona.getId())))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.nombre").value(testPersona.getNombre()))
                    .andExpect(jsonPath("$.apellido").value(testPersona.getApellido()))
                    .andExpect(jsonPath("$.dni").value(testPersona.getDni()))
                    .andExpect(jsonPath("$.empleado").value(testPersona.isEmpleado()));
        }

        @Test
        void shouldReturnNotFoundIfDoesNotExist() throws Exception {

            mockMvc.perform(get("/1"))
                    .andExpect(status().isNotFound());
        }


    }

    @Nested
    class POSTPersonaTests{

        @AfterEach
        void tearDown() {
            personaRepository.deleteAll();
        }

        @Test
        void shouldReturnPersonaIfCreated() throws Exception{
            Persona testPersona = new Persona(1L,"Juan","Perez","25148684",true);

            String requestBody = new JSONObject()
                    .put("nombre",testPersona.getNombre())
                    .put("apellido",testPersona.getApellido())
                    .put("dni",testPersona.getDni())
                    .put("empleado",true)
                    .toString();

            mockMvc.perform(post("/").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.nombre").value(testPersona.getNombre()))
                    .andExpect(jsonPath("$.apellido").value(testPersona.getApellido()))
                    .andExpect(jsonPath("$.dni").value(testPersona.getDni()))
                    .andExpect(jsonPath("$.empleado").value(testPersona.isEmpleado()));

            assertTrue(personaRepository.existsByDni(testPersona.getDni()));
        }

        @Test
        void shouldReturnConflictIfAlreadyExists() throws Exception{
            Persona testPersona = new Persona(1L,"Juan","Perez","25148684",true);
            personaRepository.save(testPersona);

            String requestBody = new JSONObject()
                    .put("nombre",testPersona.getNombre())
                    .put("apellido",testPersona.getApellido())
                    .put("dni",testPersona.getDni())
                    .put("empleado",true)
                    .toString();

            mockMvc.perform(post("/").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isConflict());
        }

    }

    @Nested
    class DELETEPersonaTests{
        @AfterEach
        void tearDown() {
            personaRepository.deleteAll();
        }

        @Test
        void shouldDeletePersonaIfExistsAndReturnOkStatusCode() throws Exception {
            Persona testPersona = new Persona("Juan","Perez","25148684",true);
            personaRepository.save(testPersona);

            mockMvc.perform(delete(format("/%o",testPersona.getId())))
                    .andExpect(status().isOk());

            assertFalse(personaRepository.existsById(testPersona.getId()));
        }

        @Test
        void shouldReturnNotFoundStatusCodeIfDoesNotExist() throws Exception{

            mockMvc.perform(delete("/1"))
                    .andExpect(status().isNotFound());
        }

    }

    @Nested
    class PutPersonaTests{

        @AfterEach
        void tearDown() {
            personaRepository.deleteAll();
        }

        @Test
        void shouldReturnPersonaIfModified() throws Exception{
            Persona testPersona = new Persona("Juan","Perez","25148684",true);
            personaRepository.save(testPersona);

            String modifiedName = "Rodrigo";

            String requestBody = new JSONObject()
                    .put("nombre",modifiedName)
                    .put("apellido",testPersona.getApellido())
                    .put("dni",testPersona.getDni())
                    .put("empleado",true)
                    .toString();

            mockMvc.perform(put("/1").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.nombre").value(modifiedName))
                    .andExpect(jsonPath("$.apellido").value(testPersona.getApellido()))
                    .andExpect(jsonPath("$.dni").value(testPersona.getDni()))
                    .andExpect(jsonPath("$.empleado").value(testPersona.isEmpleado()));

            assertTrue(personaRepository.existsByDni(testPersona.getDni()));
        }

        @Test
        void shouldReturnNotFoundStatusCodeIfResourceDoesNotExist() throws Exception{
            Persona testPersona = new Persona("Juan","Perez","25148684",true);

            String requestBody = new JSONObject()
                    .put("nombre",testPersona.getNombre())
                    .put("apellido",testPersona.getApellido())
                    .put("dni",testPersona.getDni())
                    .put("empleado",true)
                    .toString();

            mockMvc.perform(put("/1").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }
}
