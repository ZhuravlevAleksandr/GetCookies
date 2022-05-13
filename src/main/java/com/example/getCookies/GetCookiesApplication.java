package com.example.getCookies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class GetCookiesApplication {

	static String url = "http://94.198.50.185:7081/api/users";
	static RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		SpringApplication.run(GetCookiesApplication.class, args);
		ResponseEntity<String> response1 = restTemplate.getForEntity(url, String.class);
		HttpHeaders headers = response1.getHeaders();
		List<String> cookie = headers.get("Set-Cookie");

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("cookie", cookie.stream().collect(Collectors.joining(";")));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		User user = new User(3L, "James", "Brown", (byte) 33);
		HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

		User user1 = new User(3L, "Thomas", "Shelby", (byte) 33);
		HttpEntity<User> httpEntity1 = new HttpEntity<>(user1, httpHeaders);

		saveUser(httpEntity);
		updateUser(httpEntity1);
		deleteUser(httpEntity1);

	}

	public static void saveUser(HttpEntity<User> httpEntity) {
		ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
		System.out.println(exchange);
	}

	public static void updateUser(HttpEntity<User> httpEntity) {
		ResponseEntity<String> exchange1 = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
		System.out.println(exchange1);
	}

	public static void deleteUser(HttpEntity<User> httpEntity) {
		ResponseEntity<String> exchange2 = restTemplate.exchange(url + "/3", HttpMethod.DELETE, httpEntity, String.class);
		System.out.println(exchange2);
	}
}

class User {
	private Long id;
	private String name;
	private String lastName;
	private Byte age;

	public User(Long id, String name, String lastName, Byte age) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Byte getAge() {
		return age;
	}

	public void setAge(Byte age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", lastName='" + lastName + '\'' +
				", age=" + age +
				'}';
	}
}
