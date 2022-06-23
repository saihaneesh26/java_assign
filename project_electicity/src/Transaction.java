
public class Transaction {
	String meterId,date;
	int units,amount;
	public Transaction(String meterId,String date,int units,int amount) {
		this.amount = amount;
		this.units = units;
		this.meterId = meterId;
		this.date = date;
	}
}
