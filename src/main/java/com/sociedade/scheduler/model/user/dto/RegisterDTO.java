package com.sociedade.scheduler.model.user.dto;

import com.sociedade.scheduler.model.user.UserRole;

public record RegisterDTO(String login, String email, String password, UserRole role) {
}
