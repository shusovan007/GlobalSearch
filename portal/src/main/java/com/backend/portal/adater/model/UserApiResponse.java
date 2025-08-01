package com.backend.portal.adater.model;

import java.util.List;

public class UserApiResponse {
    private List<UserDto> users;

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
