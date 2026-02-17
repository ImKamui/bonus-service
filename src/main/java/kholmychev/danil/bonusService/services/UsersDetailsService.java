package kholmychev.danil.bonusService.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kholmychev.danil.bonusService.models.User;
import kholmychev.danil.bonusService.repositories.UserRepository;
import kholmychev.danil.bonusService.security.UsersDetails;

@Service
public class UsersDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Autowired
	public UsersDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isEmpty())
		{
			throw new UsernameNotFoundException("User not found");
		}
		return new UsersDetails(user.get());
	}
}
