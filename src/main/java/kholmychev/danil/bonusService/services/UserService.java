package kholmychev.danil.bonusService.services;

import kholmychev.danil.bonusService.models.User;
import kholmychev.danil.bonusService.repositories.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll()
    {
        return userRepository.findAll();
    }
    
    public User findOne(Integer id)
    {
    		Optional<User> foundUser = userRepository.findById(id);
    		return foundUser.orElse(null);
    }
    
    public User findOneByUsername(String username)
    {
    		Optional<User> foundUser = userRepository.findByUsername(username);
    		return foundUser.orElse(null);
    }
    
    @Transactional
    public void save(User user)
    {
    		userRepository.save(user);
    }
    
    @Transactional
    public void update(User updatedUser, Integer id)
    {
    		User user = userRepository.findById(id).orElse(null);
    		user.setUsername(updatedUser.getUsername());
    		user.setPassword(updatedUser.getPassword());
    		userRepository.save(user);
    }
    
    @Transactional
    public void updateRoleById(Integer id, String role)
    {
    		userRepository.updateRoleById(role, id);
    }
    
    @Transactional
    public void delete(Integer id)
    {
    		userRepository.deleteById(id);
    }
    
    @Transactional
    public void register(String username, String password, String role)
    {
    		if (userRepository.findByUsername(username).isPresent())
    		{
    			throw new IllegalArgumentException("Username already exists");
    		}
    		User user = new User();
    		user.setUsername(username);
    		user.setPassword(passwordEncoder.encode(password));
    		user.setUserRole(role);
    		userRepository.save(user);
    }

}
