package com.backend.portal.service;


import com.backend.portal.adater.UserApiAdapter;
import com.backend.portal.aop.LoggingAspect;
import com.backend.portal.dao.User;
import com.backend.portal.dao.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FetchUserService {

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserApiAdapter userApiAdapter;

    private static final Logger logger = LoggerFactory.getLogger(FetchUserService.class);

    public void loadUsersFromApi() throws Exception {
        List<User> users = userApiAdapter.fetchUsersFromApi();

        userRepository.saveAll(users);
        Search.session(entityManager).massIndexer().startAndWait();
    }

    public List<User> searchUsers(String query) {
        SearchSession session = Search.session(entityManager);
        return session.search(User.class)
                .where(f -> f.bool(b -> b
//                        .should(f.match()                       // can be included for remeberance of close name , that the user might forget and try to search but not included in requirement so commenting out
//                                .fields("firstName", "lastName", "ssn")
//                                .matching(query)
//                                .fuzzy(1))
                        .should(f.wildcard()
                                .fields("firstName", "lastName", "ssn")
                                .matching(query.toLowerCase() + "*"))
                ))
                .fetchHits(50);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
