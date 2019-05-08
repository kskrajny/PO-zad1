import java.util.Scanner;

public class order_pricelist{

	private bars B = new bars();
	private Scanner scan = new Scanner(System.in);
//these objects are made in order to use constructors

	class Order{
//it keeps data about order
		private long sum;
//sum of lengths of ordered bars
		private long[] tab;
//tab of lengths of bars
		private String strategy;
//strategy of cutting bars

		public Order(){
//constructors read input and sets all attributes
			this.sum = 0;
			int P = scan.nextInt();
			this.tab = new long[P];
			for(int i=0;i<P;i++){
				tab[i] = scan.nextLong();
				sum = sum + tab[i];
			}
			this.strategy = scan.next();
		}

		public String get_strategy(){
			return this.strategy;
		}
		public void done(int i){
//if tab[i] = -1 it means that this section is already made
			tab[i] = -1;
		}
		public long get_sum(){
			return this.sum;
		}
		public long get_bar_length(int i){
			return this.tab[i];
		}

		public int get_tab_length(){
			return this.tab.length;
		}
	}

	class PriceList{
//it keeps data about pricelist
		private bars.Bar[] tab;
// tab of available bars, Bar keeps his length and price
		private long max_price;
// max price of all available bars
		public PriceList(){
//reads input and sets attributes
			int C = scan.nextInt();
			this.tab = new bars.Bar[C];
			long max = 0;
			for(int i=0;i<C;i++){
				long l = scan.nextLong();
				long p = scan.nextLong();
				tab[i] = B.new Bar(l,p);
				if(p > max){
					max = p;
				}
			}
			this.max_price = max;
		}

		public bars.Bar get_Bar(int i){
			return this.tab[i];
		}

		public bars.Bar find_min(long l){
//looks for the smallest Bar in pricelist which size is bigger than l
			int i = 0;
			while(i < this.tab.length && l > this.tab[i].get_length()){
				i++;
			}
			if(i == this.tab.length){
				return null;
			}
			return this.tab[i];
		}

		public bars.Bar find_min_price(long l){
//looks for the cheapest Bar in pricelist which size is bigger than l
			int index = -1;
			long min = this.max_price;
			for(int i=0;i<this.tab.length;i++){
				if(this.tab[i].get_price() <= min && l <= this.tab[i].get_length()){
					index = i;
					min = this.tab[i].get_price();
				}
			}
			if(index == -1){
				return null;
			}
			return this.tab[index];
		}
		
		public long get_max(){
			return this.max_price;
		}	

		public int get_tab_length(){
			return this.tab.length;
		}
	}
}
