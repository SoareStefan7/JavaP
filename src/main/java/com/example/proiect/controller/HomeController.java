package com.example.proiect.controller;


import com.example.proiect.PasswordStrengthChecker;
import com.example.proiect.model.Account;
import com.example.proiect.service.AccountService;
import com.example.proiect.model.User;
import com.example.proiect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Controller for the home page.
 */
@Controller
@RequiredArgsConstructor

public class HomeController {

	final UserService userService;
	final AccountService accountService;

	private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
	private static final String NUMBERS = "0123456789";
	private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:'\",.<>/?";
	@GetMapping("/")
	public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
		if (principal != null) {
			User user = new User();

			user.setEmail((String) principal.getUserInfo().getClaims().get("email"));
			user.setSub((String) principal.getUserInfo().getClaims().get("sub"));
			user.setEmail_verified((boolean) principal.getUserInfo().getClaims().get("email_verified"));
			user.setNikName((String) principal.getUserInfo().getClaims().get("nickname"));
			user.setName((String) principal.getUserInfo().getClaims().get("name"));
			userService.saveUser(user);
			//TODO maybe add logger
			//sub
			//email_verified
			//updated_at
			//nickname
			//name
			//email
			//picture


		}
		return "index";
	}

	@GetMapping("/myaccounts")
	public String myAccountsPage(Model model, @AuthenticationPrincipal OidcUser principal) {
		if (principal != null) {
			List<Account> accounts = accountService.getAccountByEmail((String) principal.getUserInfo().getClaims().get("email"));
			List<String> passwordStrengthList = new ArrayList<>();
			for (Account account : accounts) {
				String password = account.getPassword();
				// Implement your logic to determine password strength (e.g., based on length)
				String strength = String.valueOf(PasswordStrengthChecker.checkPasswordStrength(password));
				passwordStrengthList.add(strength);
			}
			model.addAttribute("accountList", accounts);
			model.addAttribute("passwordStrengthList", passwordStrengthList);
		}

		return "myaccounts";
	}

	@GetMapping("/generatepassword")
	public String myPasswordGeneratorPage(Model model, @AuthenticationPrincipal OidcUser principal) {
		String pw = generatePassword();
		model.addAttribute("generatedPassword", pw);
		return "generatepassword";
	}

	@PostMapping("/myaccounts")
	@PreAuthorize("hasAuthority('create:accounts')")
	@CrossOrigin
	public String addAccount(@RequestParam("newDomain") String newDomain,
							 @RequestParam("newPassword") String newPassword,
							 @AuthenticationPrincipal OidcUser principal) {

		if (principal == null) {
			return "redirect:/index";
		}

		//TODO: Check if this user already has an account for this domain
		Account newAccount = new Account();
		newAccount.setDomain(newDomain);
		newAccount.setEmail((String) principal.getUserInfo().getClaims().get("email"));
		newAccount.setPassword(newPassword);

		accountService.saveAccount(newAccount);

		return "redirect:/myaccounts";
	}

	@PostMapping("/delete")
	public String deleteAccount(@RequestParam int account_id , @AuthenticationPrincipal OidcUser principal ) {
	accountService.deleteById(account_id);
		return "redirect:/myaccounts";
	}


	public static String generatePassword(){
		int length = 16;
		StringBuilder password = new StringBuilder();

		// Create a pool of characters to choose from
		String allCharacters = UPPERCASE_LETTERS + LOWERCASE_LETTERS + NUMBERS + SPECIAL_CHARACTERS;

		// Use a cryptographically secure random number generator
		SecureRandom secureRandom = new SecureRandom();

		// Generate the password
		for (int i = 0; i < length; i++) {
			int randomIndex = secureRandom.nextInt(allCharacters.length());
			char randomChar = allCharacters.charAt(randomIndex);
			password.append(randomChar);
		}

		return password.toString();

	}
}