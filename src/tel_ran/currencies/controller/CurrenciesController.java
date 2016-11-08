package tel_ran.currencies.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import tel_ran.currencies.model.dao.CurrenciesMongo;
import tel_ran.currencies.model.entiteis.Currency;
import tel_ran.security.accounts.Account;
import tel_ran.security.accounts.repo.AccountsRepository;

@RestController
@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class CurrenciesController {

	static RestTemplate restTemplate = new RestTemplate();
	static String url = "http://api.fixer.io/latest";
	@Autowired
	CurrenciesMongo currMongo;
	@Autowired
	AccountsRepository accounts;
	
	@RequestMapping(value = "account", method = RequestMethod.PUT)
	public void addAccount(@RequestBody Account account){
		accounts.save(account);
	}

	public CurrenciesController() {
	}

	@RequestMapping(value = "latest")
	public Currency latest() {
		return restTemplate.getForObject(url, Currency.class);
	}
	
	@RequestMapping(value = "currencies")
	public Set<String> getCurrencies() {
		return restTemplate.getForObject(url, Currency.class).getRates().keySet();
	}
	
	@RequestMapping(value = "convert")
	public Double convert(String rate1, String rate2, Double n) {
		Currency currency = restTemplate.getForObject(url, Currency.class);
		Double res = currency.getRates().get(rate1) * n
				/ currency.getRates().get(rate2);
		return res;
	}

	@RequestMapping(value = "findOne")
	public Currency findOne(long id) {
		return currMongo.findOne(id);
	}

	@RequestMapping(value = "findByYear")
	public Iterable<Currency> findByYear(int year) {
		return currMongo.findByYear(year);
	}

	@RequestMapping(value = "findByYearAndMonth")
	public Iterable<Currency> findByYearAndMonth(int year, int month) {
		return currMongo.findByYearAndMonth(year, month);
	}

	@RequestMapping(value = "avgOfRateByMonth")
	public Double avgOfRateByMonth(String rate, int year, int month) {
		Iterable<Currency> currs = currMongo.findByYearAndMonth(year, month);
		Double res = (double) 0;
		int n = 0;
		if (currs.iterator().hasNext())
			for (Currency curr : currs) {
				HashMap<String, Double> rates = curr.getRates();
				if(rates.get(rate) == null) return (double) 0;
				double r = rates.get(rate);
				res += r;
				n++;
			}
		return (n != 0) ? res / n : (double) 0;
	}

	@RequestMapping(value = "statisticOfYear")
	public List<Double> statisticOfYear(String rate, int year) {
		List<Double> res = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			res.add(avgOfRateByMonth(rate, year, i));
		}
		return res;
	}

	@RequestMapping(value = "statisticAll")
	public LinkedHashMap<Integer, Double> statisticAll(String rate) {
		LinkedHashMap<Integer, Double> res = new LinkedHashMap<Integer, Double>();
		for (int year = 2006; year < 2017; year++)
			res.put(year, avg(statisticOfYear(rate, year)));
		return res;
	}

	private Double avg(List<Double> statisticOfYear) {
		Double res = (double) 0;
		int n = 0;
		for (Double avgOfMonth : statisticOfYear) {
			res += avgOfMonth;
			n++;
		}
		return (n != 0) ? res / n : (double) 0;
	}

	public static void main(String[] args) {
		SpringApplication.run(CurrenciesController.class, args);
	}
}
