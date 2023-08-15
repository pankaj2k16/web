package com.rajlee.jwt.service;

import com.rajlee.jwt.dao.RoleDao;
import com.rajlee.jwt.dao.UserDao;
import com.rajlee.jwt.entity.Role;
import com.rajlee.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    public User registerNewUser(User user){
        return userDao.save(user);
    }
    @Autowired
    private RoleDao roleDao;
    public void initRolesAndUser(){
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("I am user not admin");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setFirstName("Admin");
        adminUser.setLastName("Admin");
        adminUser.setUserName("admin123");
        adminUser.setPassword("admin@pass");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRoleSet(adminRoles);
        userDao.save(adminUser);

        User user = new User();
        user.setFirstName("User");
        user.setLastName("User");
        user.setUserName("user123");
        user.setPassword("user@pass");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(adminRole);
        user.setRoleSet(userRoles);
        userDao.save(user);
    }
}
