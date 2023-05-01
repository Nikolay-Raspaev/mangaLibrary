package com.LabWork.app.MangaStore.service;

import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.model.Default.Manga;
import com.LabWork.app.MangaStore.model.Default.Reader;
import com.LabWork.app.MangaStore.service.Repository.CreatorRepository;
import com.LabWork.app.MangaStore.service.Exception.CreatorNotFoundException;
import com.LabWork.app.MangaStore.model.Default.User;
import com.LabWork.app.MangaStore.service.Repository.UserRepository;
import com.LabWork.app.MangaStore.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CreatorService {
    private final CreatorRepository creatorRepository;

    private final UserRepository userRepository;
    private final MangaService mangaService;
    private final ValidatorUtil validatorUtil;

    public CreatorService(CreatorRepository creatorRepository,
                          MangaService mangaService,
                          ValidatorUtil validatorUtil,
                          UserRepository userRepository) {
        this.creatorRepository = creatorRepository;
        this.userRepository = userRepository;
        this.mangaService = mangaService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional(readOnly = true)
    public Creator findCreator(Long id) {
        final Optional<Creator> creator = creatorRepository.findById(id);
        return creator.orElseThrow(() -> new CreatorNotFoundException(id));
    }

    public Creator findByLogin(String login) {
        return findCreator(userRepository.findOneByLoginIgnoreCase(login).getId());
    }
    @Transactional(readOnly = true)
    public User findUser(Long id) {
        final Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new CreatorNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Creator> findAllCreators() { return creatorRepository.findAll(); }

    @Transactional
    public Creator addCreator(User user) {
        final Creator creator = new Creator(user);
        validatorUtil.validate(creator);
        return creatorRepository.save(creator);
    }
}
