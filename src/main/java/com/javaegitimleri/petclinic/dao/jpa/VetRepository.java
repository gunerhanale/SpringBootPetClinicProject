package com.javaegitimleri.petclinic.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaegitimleri.petclinic.model.Vet;

//Spring Data Repository kabiliyetini kullandik 
public interface VetRepository extends JpaRepository<Vet, Long> {
	
}
