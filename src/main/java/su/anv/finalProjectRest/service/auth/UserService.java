package su.anv.finalProjectRest.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import su.anv.finalProjectRest.entity.User;
import su.anv.finalProjectRest.error.exception.DuplicatedRegistrationParamsException;
import su.anv.finalProjectRest.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository UserRepository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return UserRepository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (UserRepository.existsByUsername(user.getUsername())) {
            // Заменить на свои исключения
            throw new DuplicatedRegistrationParamsException("Пользователь с таким именем уже существует");
        }

        if (UserRepository.existsByEmail(user.getEmail())) {
            throw new DuplicatedRegistrationParamsException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return UserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь c именем %s не найден", username)));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
}
