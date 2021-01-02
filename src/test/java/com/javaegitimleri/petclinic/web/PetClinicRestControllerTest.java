package com.javaegitimleri.petclinic.web;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.javaegitimleri.petclinic.model.Owner;

public class PetClinicRestControllerTest {
	private RestTemplate restTemplate;

	@Before
	public void setUp() {
		restTemplate = new RestTemplate();
		BasicAuthorizationInterceptor basicAuthorizationInterceptor = new BasicAuthorizationInterceptor("user2", "secret");
		restTemplate.setInterceptors(Arrays.asList(basicAuthorizationInterceptor));
	}

	@Test
	public void testDeleteOwner() {
//		restTemplate.delete("http://localhost:8080/rest/owner/1"); // REST_TEMPLATE DELETE METHOD
		ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:8080/rest/owner/1", HttpMethod.DELETE, null, Void.class);
		try {
			restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
			Assert.fail("should have not returned owner");
		} catch (HttpClientErrorException ex) {
			MatcherAssert.assertThat(ex.getStatusCode().value(), Matchers.equalTo(404));
		}
	}

	@Test
	public void testUpdateOwner() {
		Owner owner = restTemplate.getForObject("http://localhost:8080/rest/owner/4", Owner.class);

		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Salim"));

		owner.setFirstName("Salim Güray");
		restTemplate.put("http://localhost:8080/rest/owner/4", owner); // REST_TEMPLATE PUT METHOD

		owner = restTemplate.getForObject("http://localhost:8080/rest/owner/4", Owner.class); // REST_TEMPLATE GET
																								// METHOD

		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Salim Güray"));
	}

	@Test
	public void testCreateOwner() {
		Owner owner = new Owner();
		owner.setFirstName("Metehan");
		owner.setLastName("Yücel");

		URI location = restTemplate.postForLocation("http://localhost:8080/rest/owner", owner); // REST_TEMPLATE POST
																								// METHOD

		Owner owner2 = restTemplate.getForObject(location, Owner.class); // REST_TEMPLATE GET METHOD

		MatcherAssert.assertThat(owner2.getFirstName(), Matchers.equalTo(owner.getFirstName()));
		MatcherAssert.assertThat(owner2.getLastName(), Matchers.equalTo(owner.getLastName()));
	}

	@Test
	public void testGetOwnerById() {
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8085/rest/owner/1", Owner.class); // REST_TEMPLATE
																														// GET
																														// METHOD

		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		// MatcherAssert.assertThat(response.getBody().getFirstName(),
		// Matchers.equalTo("Kenan"));
	}

	@Test
	public void testGetOwnerByLastName() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owner?ln=Sevindik",
				List.class); // REST_TEMPLATE GET METHOD

		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

		List<Map<String, String>> body = response.getBody();
		List<String> firstNames = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());

		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Kenan", "Hümeyra", "Salim"));
	}

	@Test
	public void testGetOwners() {

		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners", List.class);

		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

		List<Map<String, String>> body = response.getBody();
		List<String> firstNames = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());

		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Kenan", "Hümeyra", "Salim", "Muammer"));
	}

}
