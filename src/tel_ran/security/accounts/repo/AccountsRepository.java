package tel_ran.security.accounts.repo;

import org.springframework.data.repository.CrudRepository;

import tel_ran.security.accounts.Account;

public interface AccountsRepository extends CrudRepository<Account, String> {

}
