package ru.kata.spring.boot_security.demo.service;


import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository usersRepository;
    private final RoleRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, RoleRepository rolesRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.usersRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // методы User

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User saveUser(@Valid User user) {
        if (usersRepository.findUserByNickname(user.getUsername())!=null) {
            throw new EntityExistsException("The user with this name already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role existingRole = rolesRepository.findById(role.getId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(existingRole);
        }

        user.setRoles(roles);
        usersRepository.save(user);
        return user;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User updateUser(Long id, @Valid User updatedUser) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с ID " + id + " не найден"));
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        if (usersRepository.findUserByNickname(updatedUser.getUsername()) != null &&
                !user.getUsername().equals(updatedUser.getUsername())) {
            throw new DuplicateKeyException("Имя " + updatedUser.getUsername() + " занято, выберите другое имя!");
        }
        user.setNickname(updatedUser.getNickname());
        user.setEmail(updatedUser.getEmail());
        user.setAge(updatedUser.getAge());
        user.setRoles(updatedUser.getRoles());
        return usersRepository.save(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(long id) {
        usersRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return usersRepository.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByNickname(String nickname) {
        return usersRepository.findUserByNickname(nickname);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findByNickname(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found exception");
        }
        return user;
    }

    // методы Role

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRole(Role role) {
        rolesRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role showRoleById(Long id) {
        return rolesRepository.getById(id);
    }

}
