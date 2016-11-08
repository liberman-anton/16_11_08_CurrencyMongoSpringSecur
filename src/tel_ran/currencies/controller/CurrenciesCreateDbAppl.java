package tel_ran.currencies.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.*;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.client.RestTemplate;

import tel_ran.currencies.model.dao.CurrenciesMongo;
import tel_ran.currencies.model.entiteis.Currency;

public class CurrenciesCreateDbAppl {
	static RestTemplate restTemplate = new RestTemplate();
	static String url = "http://api.fixer.io/";
	static Calendar calendar = new GregorianCalendar();
	static Calendar when = new GregorianCalendar(2006, 0, 1);
	static List<Currency> listCurrencies = new ArrayList<>();
	static CurrenciesMongo currenciesMongo;
	static Currency currency;

	public static void main(String[] args) {
		try (AbstractApplicationContext ctx = new FileSystemXmlApplicationContext("beans.xml")) {
			currenciesMongo = ctx.getBean(CurrenciesMongo.class);
			Currency newCurr = null;
			long i = 1L;
			while (calendar.after(when)) {
				currency = restTemplate.getForObject(getUrl().toString(), Currency.class);
	
				if(newCurr == null || !newCurr.getDate().equals(currency.getDate())){
					newCurr = new Currency(currency.getDate(), currency.getRates());
				}else{
					newCurr = new Currency(currency.getDate(), currency.getRates());
					newCurr.setId(newCurr.getId() + i);
					i += 1L;
				}
				listCurrencies.add(newCurr);
				calendar.add(Calendar.DAY_OF_MONTH, -1);

				System.out.println(currency);
			}
			currenciesMongo.addCurrencies(listCurrencies);
		}
	}

	private static StringBuilder getUrl() {
		return new StringBuilder(url).append(calendar.get(Calendar.YEAR)).append("-")
				.append(formatNumber(calendar.get(Calendar.MONTH) + 1)).append("-")
				.append(formatNumber(calendar.get(Calendar.DAY_OF_MONTH)));
	}

	private static String formatNumber(int n) {
		return (n < 10) ? "0" + n : "" + n;
	}

}
