package kholmychev.danil.bonusService.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kholmychev.danil.bonusService.models.User;

public class UserValidator implements Validator{

	private final UserDetailsService userDetailsService;
	
	@Autowired
	public UserValidator(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		try
		{
			userDetailsService.loadUserByUsername(user.getUsername());
		}
		catch (UsernameNotFoundException e)
		{
			return;
		}
		errors.rejectValue("username", "duplicate.username", "Аккаунт с таким именем пользователя уже существует");
		
	}
}
