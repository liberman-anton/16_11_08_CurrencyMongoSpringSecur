package tel_ran.currencies.model.dao;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import tel_ran.currencies.model.entiteis.Currency;
import tel_ran.currencies.repo.CurrenciesRepository;

public class CurrenciesMongo {
	@Autowired
	CurrenciesRepository currencies;

	public Iterable<Currency> findByYear(int year) {
		Iterable<Currency> res = currencies.findByYear(year);
		return res;
	}
	public Iterable<Currency> findByYearAndMonth(int year, int month) {
		Iterable<Currency> res = currencies.findByYearAndMonth(year, month);
		return res;
	}
	
	public Currency findOne(long id) {
		Currency res = currencies.findOne(id);
		return res;
	}
	
	public boolean addCurrencies(List<Currency> listCurrencies) {
		currencies.save(listCurrencies);
		return true;
	}

	public boolean addCurrency(Currency currency) {
		currencies.save(currency);
		return true;
	}

}
