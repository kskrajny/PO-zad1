import java.util.Scanner;

public class Main{

	private static bars B = new bars();
	private static order_pricelist OP = new order_pricelist();
	private static strategy S = new strategy();
//these objects are made in order to use constructors

	public static void main (String[] args){
		order_pricelist.PriceList pricelist = OP.new PriceList();
		order_pricelist.Order order = OP.new Order();
//these constructors read input and properly set objects
		strategy.Strategy curr = null;
//initialization, curr will be changed
		boolean success = false;
//it say if curr is properly set
		String s = order.get_strategy();
		if(s.equals("minimalistyczna")){
			curr = S.new Minimalist(order, pricelist);
			success = true;
		}
		if(s.equals("maksymalistyczna")){
			curr = S.new Maximalist(order, pricelist);
			success = true;
		}
		if(s.equals("ekologiczna")){
			curr = S.new Ecologist(order, pricelist);
			success = true;
		}
		if(s.equals("ekonomiczna")){
			curr = S.new Economist(order, pricelist);
			success = true;
		}
		if(success == true){
			bars.BarsSet setofbars = curr.divide();
			System.out.println(""+setofbars);
//almost each class has method toString(), so it is that easy to write a solution
		}
	}
}
		
