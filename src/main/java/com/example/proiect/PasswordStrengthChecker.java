package com.example.proiect;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class PasswordStrengthChecker {

	//TODO: maybe look into this feature
//	private static final Set<String> COMMON_ENGLISH_WORDS = loadCommonEnglishWords();
//
//
//	private static Set<String> loadCommonEnglishWords() {
//		Set<String> words = new HashSet<>();
//		try (InputStream inputStream = PasswordStrengthChecker.class.getResourceAsStream("/common-english-words.txt");
//			 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//			String line;
//			while ((line = reader.readLine()) != null) {
//				words.add(line.trim().toLowerCase());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return words;
//	}

	public static PasswordStrength checkPasswordStrength(String password) {
		int length = password.length();
		boolean hasUppercase = !password.equals(password.toLowerCase());
		boolean hasLowercase = !password.equals(password.toUpperCase());
		boolean hasDigit = password.matches(".*\\d.*");
		boolean hasSpecialChar = !password.matches("[A-Za-z0-9]*");

		if (length < 8) {
			return PasswordStrength.WEAK;
		} else if (length < 12 || (!hasUppercase && !hasLowercase)) {
			return PasswordStrength.MEDIUM;
		} else if (hasDigit && hasSpecialChar) {
			return PasswordStrength.STRONG;
		} else {
			return PasswordStrength.MEDIUM;
		}
	}
}

enum PasswordStrength {
	WEAK,
	MEDIUM,
	STRONG
}
