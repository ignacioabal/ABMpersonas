package com.ignacioabal.ABMpersonas.Persona;

import org.springframework.data.repository.CrudRepository;

public interface PersonaRepository extends CrudRepository<Persona, Long> {

    boolean existsByDni(String dni);
}
