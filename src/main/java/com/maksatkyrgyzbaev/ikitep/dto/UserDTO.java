package com.maksatkyrgyzbaev.ikitep.dto;

import com.maksatkyrgyzbaev.ikitep.entity.Role;
import com.maksatkyrgyzbaev.ikitep.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Comparable<UserDTO> {

    private Long id;
    private String username;
    private String password;
    private String matchingPassword;
    private Role role;
    private String fullName;
    private String schoolName;
    private List<Long> bookedBooksId;
    private int countBookedBooksId;

    @Override
    public int compareTo(UserDTO o) {
        if (this.getId() > o.getId()) return -1;
        return 1;
    }
}
