package com.backend.portal.adater;

import com.backend.portal.adater.model.UserApiResponse;
import com.backend.portal.adater.model.UserDto;
import com.backend.portal.dao.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserApiAdapter {


    @Value("${external.api.user.url}")
    private String apiUrl;

   // private static final String API_URL = "https://dummyjson.com/users";

    public List<User> fetchUsersFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserApiResponse> response = restTemplate.getForEntity(apiUrl, UserApiResponse.class);

        List<UserDto> apiUsers = response.getBody().getUsers();

        return apiUsers.stream().map(this::mapToUser).collect(Collectors.toList());
    }

    private User mapToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setSsn(dto.getSsn());
        user.setRole(dto.getRole());
        return user;
    }
}
