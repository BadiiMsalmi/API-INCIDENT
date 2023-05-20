package com.api.backincdidents.Dto;

import com.api.backincdidents.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserWithTicketCount {
    private User user;
    private int ticketCount;

    public UserWithTicketCount(User user, int ticketCount) {
        this.user = user;
        this.ticketCount = ticketCount;
    }

    // Getters and setters for user and ticketCount
}
