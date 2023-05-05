package com.LabWork.app.MangaStore.service;

import com.LabWork.app.MangaStore.configuration.jwt.JwtException;
import com.LabWork.app.MangaStore.configuration.jwt.JwtProvider;
import com.LabWork.app.MangaStore.model.Default.User;
import com.LabWork.app.MangaStore.model.Default.UserRole;
import com.LabWork.app.MangaStore.model.Dto.UserDto;
import com.LabWork.app.MangaStore.service.Exception.UserNotFoundException;
import com.LabWork.app.MangaStore.service.Repository.UserRepository;
import com.LabWork.app.MangaStore.util.validation.ValidationException;
import com.LabWork.app.MangaStore.util.validation.ValidatorUtil;
import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.model.Default.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ValidatorUtil validatorUtil;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CreatorService creatorService;
    private final ReaderService readerService;

    public UserService(UserRepository userRepository,
                       ValidatorUtil validatorUtil,
                       PasswordEncoder passwordEncoder,
                       CreatorService creatorService,
                       ReaderService readerService,
                       JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.validatorUtil = validatorUtil;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.creatorService = creatorService;
        this.readerService = readerService;
    }

    @Transactional(readOnly = true)
    public User findUser(Long id) {
        final Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException(id));
    }

    public User findByLogin(String login) {
        return userRepository.findOneByLoginIgnoreCase(login);
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> findAllPages(int page, int size) {
        return userRepository.findAll(PageRequest.of(page - 1, size, Sort.by("id").ascending()));
    }

    @Transactional
    public User addUser(String login, String email, String password, String passwordConfirm, UserRole role) {
        if (findByLogin(login) != null) {
            throw new ValidationException(String.format("User '%s' already exists", login));
        }
        if (!Objects.equals(password, passwordConfirm)) {
            throw new ValidationException("Passwords not equals");
        }
        final User user = new User(login, email, passwordEncoder.encode(password), role);
        //validatorUtil.validate(user);
        userRepository.save(user);
        if (role.toString().equals("ADMIN")){
            final Creator creator = creatorService.addCreator();
            bindCreator(user.getId(), creator.getId());
        }
        if (role.toString().equals("USER")){
            final Reader reader = readerService.addReader();
            bindReader(user.getId(), reader.getId());
        }
        return user;
    }

    @Transactional
    public User updateUser(UserDto userDto) {
        final User currentUser = findUser(userDto.getId());
        final User sameUser = findByLogin(userDto.getLogin());
        if (sameUser != null && !Objects.equals(sameUser.getId(), currentUser.getId())) {
            throw new ValidationException(String.format("User '%s' already exists", userDto.getLogin()));
        }
        if (!passwordEncoder.matches(userDto.getPassword(), currentUser.getPassword())) {
            throw new ValidationException("Incorrect password");
        }
        currentUser.setLogin(userDto.getLogin());
        currentUser.setEmail(userDto.getEmail());
        validatorUtil.validate(currentUser);
        return userRepository.save(currentUser);
    }

    @Transactional
    public User deleteUser(Long id) {
        final User currentUser = findUser(id);
        userRepository.delete(currentUser);
        return currentUser;
    }

    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Transactional
    public User bindCreator(Long id, Long creatorId) {
        final User user = findUser(id);
        final Creator creator = creatorService.findCreator(creatorId);
        user.setCreator(creator);
        return userRepository.save(user);
    }

    @Transactional
    public User bindReader(Long id, Long readerId) {
        final User user = findUser(id);
        final Reader reader = readerService.findReader(readerId);
        user.setReader(reader);
        return userRepository.save(user);
    }

    public String loginAndGetToken(UserDto userDto) {
        final User user = findByLogin(userDto.getLogin());
        if (user == null) {
            throw new UserNotFoundException(userDto.getLogin());
        }
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new ValidationException("Incorrect password");
        }
        return jwtProvider.generateToken(user.getLogin());
    }

    public UserDetails loadUserByToken(String token) throws UsernameNotFoundException {
        if (!jwtProvider.isTokenValid(token)) {
            throw new JwtException("Bad token");
        }
        final String userLogin = jwtProvider.getLoginFromToken(token)
                .orElseThrow(() -> new JwtException("Token is not contain Login"));
        return loadUserByUsername(userLogin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User userEntity = findByLogin(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(
                userEntity.getLogin(), userEntity.getPassword(), Collections.singleton(userEntity.getRole()));
    }
}
