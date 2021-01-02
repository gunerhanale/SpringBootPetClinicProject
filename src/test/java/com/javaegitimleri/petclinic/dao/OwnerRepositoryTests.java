package com.javaegitimleri.petclinic.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.javaegitimleri.petclinic.model.Owner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Transactional
public class OwnerRepositoryTests {

	@Autowired
	private OwnerRepository ownerRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test(expected = PersistenceException.class) // Eğer istersek burdaki null ların contraste takılmasını burada,
													// expected'ı kullanarak devre dışı bırakabilir bu şekilde
													// göremezden gelebiliriz.
	public void testCreateOwner() {
		Owner owner = new Owner();
		owner.setFirstName(null);
		owner.setLastName(null);

		ownerRepository.create(owner);

		entityManager.flush(); // Normalde bunu eklemezsek sanki test düzgün çalışıyormuş gibi devam eder,
								// halbuki null olmaması gereken alanlara null koyduk ve hata almasını
								// bekliyorduk.
	}
}
