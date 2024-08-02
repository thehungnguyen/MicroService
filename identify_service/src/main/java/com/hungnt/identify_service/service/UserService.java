package com.hungnt.identify_service.service;

import com.hungnt.identify_service.dto.request.UserCreateReq;
import com.hungnt.identify_service.dto.request.UserUpdateReq;
import com.hungnt.identify_service.dto.response.UserResponse;
import com.hungnt.identify_service.entity.Role;
import com.hungnt.identify_service.entity.User;
import com.hungnt.identify_service.exception.AppException;
import com.hungnt.identify_service.exception.ErrorCode;
import com.hungnt.identify_service.repository.RoleRepository;
import com.hungnt.identify_service.repository.UserRepository;
import event.UserEvent;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public UserResponse createUser(UserCreateReq req) throws MessagingException {
        User user = new User();

        // Tim trong DB da ton tai Username nay chua
        String username = req.getUsername();
        if(userRepository.existsByUsername(username)){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        else{
            user.setUsername(username);
        }

        user.setPassword(req.getPassword());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setDob(req.getDob());

        // Set mac dinh role USER cho new User
        HashSet<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findById("USER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOTFOUND));
        roles.add(userRole);
        user.setRoles(roles);

        // Set Email, kiem tra Email da ton tai trong User khac chua
        String email = req.getEmail();
        if(userRepository.existsByEmail(email)){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        else{
            user.setEmail(email);
        }

        // Save User
        userRepository.save(user);

        // Kafka - Message Queue
        UserEvent userEvent = UserEvent.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        kafkaTemplate.send("sendEmail", userEvent);

        // Gan User cho UserResponse
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setDob(user.getDob());
        userResponse.setRoles(user.getRoles());
        userResponse.setEmail(user.getEmail());

        return userResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize("hasAuthority('CREATE_POST')")
    public List<UserResponse> getUsers() {
        List<UserResponse> userResponses = new ArrayList<>();

        List<User> users = userRepository.findAll();

        for (User user : users) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setDob(user.getDob());
            userResponse.setRoles(user.getRoles());

            userResponses.add(userResponse);
        }

        return userResponses;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setDob(user.getDob());
        userResponse.setRoles(user.getRoles());

        return userResponse;
    }

    public UserResponse getMyInfo() {
        // Lay username tu token
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setDob(user.getDob());
        userResponse.setRoles(user.getRoles());

        return userResponse;
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateUser(String userId, UserUpdateReq req) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        user.setPassword(req.getPassword());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setDob(req.getDob());

        var roles = roleRepository.findAllById(req.getRoles());
        user.setRoles(new HashSet<>(roles));

        userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setDob(user.getDob());
        userResponse.setRoles(user.getRoles());

        return userResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

}