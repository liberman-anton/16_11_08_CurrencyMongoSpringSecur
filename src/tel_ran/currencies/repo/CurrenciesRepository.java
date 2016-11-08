package tel_ran.currencies.repo;

import org.springframework.data.repository.CrudRepository;

import tel_ran.currencies.model.entiteis.Currency;

public interface CurrenciesRepository extends CrudRepository<Currency, Long> {

	Iterable<Currency> findByYear(int year);
	Iterable<Currency> findByYearAndMonth(int year, int month);

}
