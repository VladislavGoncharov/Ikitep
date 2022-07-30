package com.maksatkyrgyzbaev.ikitep.entity;

import java.util.List;

public enum Role {
    ADMIN,LIBRARIAN,STUDENT;

    Role() {
    }

    public static List<Role> getAllRole(){
        return List.of(ADMIN,LIBRARIAN,STUDENT);
    }
}
