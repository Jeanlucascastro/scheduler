package com.sociedade.scheduler.model.user.dto;

import java.util.List;

public record UserDTO(String id, String login, String email, String name, List<Long> companies) {

}
