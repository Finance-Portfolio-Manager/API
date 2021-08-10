package dev.team4.portfoliotracker.services;


import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.repositories.UserRepository;
import dev.team4.portfoliotracker.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class UserDetailsServiceTest {

	@InjectMocks
	UserDetailsService userDetailsService;

	@Mock
	UserRepository userRepository;
	
	@Test
	public void loadUserTest() throws Exception {
		User user = new User("c@c.com", "cody", "password");
		doReturn(user).when(userRepository).findByUsername("cody");

		UserPrincipal userP = new UserPrincipal(user);
		assertEquals(userDetailsService.loadUserByUsername("cody").getPassword(), userP.getPassword());
	}

	@Test
	public void loadUserTestFail() throws Exception {
		assertThrows(UsernameNotFoundException.class, () -> {
			userDetailsService.loadUserByUsername("cody");
		});
	}

	@Test
	public void createUserTest() {
		User user = new User("c@c.com", "cody", "password");
		User user2 = new User("c@c.com", "cody", "password");
		user2.setUserId(1);
		doReturn(user2).when(userRepository).save(user);
		assertEquals(userDetailsService.createUser(user), user2);
	}

	@Test
	public void removeUserTestThrowsException() {
		User user = new User();
		assertThrows(UsernameNotFoundException.class, () -> {
			userDetailsService.removeUser(user);
		});
	}

	@Test
	public void removeUserTestSuccess() {
		User user = new User("c@c.com", "cody", "password");
		doNothing().when(userRepository).delete(user);
		userDetailsService.removeUser(user);
		verify(userRepository, times(1)).delete(user);

	}

	@Test
	public void getUserTest() {
		User user = new User("c@c.com", "cody", "password");
		doReturn(user).when(userRepository).findByUsername("cody");

		assertEquals(userDetailsService.getUserByUsername("cody"), user);
	}
}
