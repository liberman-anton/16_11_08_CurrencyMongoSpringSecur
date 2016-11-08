package tel_ran.currencies.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import tel_ran.security.accounts.Account;
import tel_ran.security.accounts.repo.AccountsRepository;

@Component
public class AdminInitializer implements CommandLineRunner {
	private static final String ADMIN_NAME = "admin";
	private static final String ADMIN_PASSWORD = "admin";
	private static final String[] ADMIN_ROLE = {"ROLE_ADMIN", "ROLE_USER"};
	@Autowired
	AccountsRepository accounts;
	
	@Override
	public void run(String... arg0) throws Exception {
		Account admin = accounts.findOne(ADMIN_NAME);
		if(admin == null){
			String adminPassword = arg0.length > 0 ? arg0[0] : ADMIN_PASSWORD;
			String[] adminRole = arg0.length > 1 ? getRoles(arg0) : ADMIN_ROLE;
			accounts.save(new Account(ADMIN_NAME, adminPassword, adminRole));
		}
	}

	private String[] getRoles(String[] arg0) {
		String[] res = new String[arg0.length - 1];
		System.arraycopy(arg0, 1, res, 0, res.length);
		return res;
	}

}
