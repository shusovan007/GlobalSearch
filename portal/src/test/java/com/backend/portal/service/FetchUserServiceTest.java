
package com.backend.portal.service;

import com.backend.portal.dao.User;
import com.backend.portal.dao.UserRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FetchUserServiceTest {

    private UserRepository userRepository;
    private EntityManager entityManager;
    private FetchUserService fetchUserService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        entityManager = mock(EntityManager.class);

        fetchUserService = new FetchUserService();
        fetchUserService.userRepository = userRepository;
        fetchUserService.entityManager = entityManager;
    }

    @Test
    void testGetUserById_found() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = fetchUserService.getUserById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    void testGetUserById_notFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<User> result = fetchUserService.getUserById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    void testGetUserByEmail_found() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = fetchUserService.getUserByEmail("test@example.com");

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("test@example.com");
    }

}
