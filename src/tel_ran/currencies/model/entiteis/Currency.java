package tel_ran.currencies.model.entiteis;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "currencies")
public class Currency {
	@Id
	Long id;
	Date date;
	HashMap<String,Double> rates;
	int year;
	int month;
	int day;
		
	public Currency() {
		super();
	}
	
	

	public Currency(Date date, HashMap<String, Double> rates) {
		super();
		this.id = date.getTime();
		this.date = date;
		this.rates = rates;
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		this.year = calendar.get(Calendar.YEAR);
		this.month = calendar.get(Calendar.MONTH);
		this.day = calendar.get(Calendar.DAY_OF_MONTH);
	}



//	@Override
//	public String toString() {
//		return "Currency [date=" + date + ", rates=" + rates + "]";
//	}
	
	

	public HashMap<String,Double> getRates(){
		return rates;
	}

	@Override
	public String toString() {
		return "Currency [id=" + id + ", date=" + date + ", rates=" + rates + ", year=" + year + ", month=" + month
				+ ", day=" + day + "]";
	}



	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMounth(int mounth) {
		this.month = mounth;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}



	public void setId(Long id) {
		this.id = id;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Currency other = (Currency) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
