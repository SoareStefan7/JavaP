package com.example.proiect.controller;


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
			model.addAttribute("accountList", accounts);
		}

		return "myaccounts";
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
		Account newAccount = new Account();
		newAccount.setDomain(newDomain);
		newAccount.setEmail((String) principal.getUserInfo().getClaims().get("email"));
		newAccount.setPassword(newPassword);

		accountService.saveAccount(newAccount);

		return "redirect:/myaccounts";
	}

//	@PostMapping("/delete")
//	public String deleteAccount(@RequestParam String domain , @AuthenticationPrincipal OidcUser principal ) {
//		List<Account> accounts = accountService.getAccountByEmail((String) principal.getUserInfo().getClaims().get("email"));
//		Optional<Account> accountOptional = accounts.stream()
//				.filter(account -> domain.equals(account.getDomain()))
//				.findFirst();
//
//		accountOptional.ifPresent(account -> accountService.deleteById(account.getAccount_id()));
//		return "redirect:/myaccounts";  // Redirect to the accounts page after deletion
//	}

	@PostMapping("/delete/{id}")
	public String deleteAccount(@PathVariable int id , @AuthenticationPrincipal OidcUser principal ) {
	accountService.deleteById(id);
		return "redirect:/myaccounts";  // Redirect to the accounts page after deletion
	}


}