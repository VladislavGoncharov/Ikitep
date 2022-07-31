package com.maksatkyrgyzbaev.ikitep.entity;

import java.util.List;

public enum Role {
    ADMIN,LIBRARIAN,STUDENT;

    Role() {
    }

    public static List<Role> getAllRole(){
        return List.of(ADMIN,LIBRARIAN,STUDENT);
    }

    public static List<Role> getAllRoleWithoutAdmin(){
        return List.of(LIBRARIAN,STUDENT);
    }

    public static boolean isRole(Role role){
        if (role == null) return false;
        return Role.getAllRole().contains(role);
    }
}
