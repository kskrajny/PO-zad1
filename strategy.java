import java.util.Scanner;

public class strategy{

	private bars B = new bars();
	private order_pricelist OP = new order_pricelist();
// these object are made int order to use constructors

	abstract class Strategy{
// every strategy needs to know pricelist and order, so it is in abstract class
		protected order_pricelist.Order order;
		protected order_pricelist.PriceList pricelist;

		public Strategy(
			order_pricelist.Order order,
			order_pricelist.PriceList pricelist
			){

			this.order = order;
			this.pricelist = pricelist;

		}

		public abstract bars.BarsSet divide();
//each subclass has its own method divide(), it makes object BarsSet which is a solution

	}

	class Minimalist extends Strategy{

		public Minimalist(
			order_pricelist.Order order,
			order_pricelist.PriceList pricelist
			){
			super(order, pricelist);
		}

		public bars.BarsSet divide(){
//greedy algorithm from task content
			bars.BarsSet setofbars = B.new BarsSet();
//this is going to be filled
			int o = order.get_tab_length()-1;
//iterator to tab of orders, starts from the lergest one
			bars.Bar help;
//this is going to be Bar taken from pricelist to cut into smallest ones
			bars.Bars curr = null;
//this is going to show how current Bar is divided
			while(o >= 0){
				if(order.get_bar_length(o) > 0){
//check if current section of order is already made
					help = pricelist.find_min(order.get_bar_length(o));
//looks for the smallest Bar so that section from order fit in this
					curr = B.new Bars(help);
//found Bar is going to be cut
					int i = o;
//iterator starts from the largest available section from order
					while(i >= 0){
						if(order.get_bar_length(i) > 0 &&
						curr.addBar(order.get_bar_length(i))){
							order.done(i);
						}
// if the section isn't made yet and fits in remainder it is cut and marked as done
						i--;
					}
					setofbars.addBars(curr);
//updates BarsSet
				}
				o--;

			}
			return setofbars;
		}
	}

	class Maximalist extends Strategy{

		public Maximalist(
			order_pricelist.Order order,
			order_pricelist.PriceList pricelist
			){
			super(order, pricelist);
			}

		public bars.BarsSet divide(){
//greedy algorithm from task content, works in similar way to previous one
			bars.BarsSet setofbars = B.new BarsSet();
			int o = order.get_tab_length()-1;
//iterator to tab of orders
			int j = pricelist.get_tab_length()-1;
			while(o >= 0){
				if(order.get_bar_length(o) > 0){
					bars.Bars curr = B.new Bars(pricelist.get_Bar(j));
					int i = o;
					while(i >= 0){
						if(order.get_bar_length(i) > 0 &&
						curr.addBar(order.get_bar_length(i))){
							order.done(i);
						}
						i--;
					}
					setofbars.addBars(curr);
				}
				o--;
			}
			return setofbars;
		}
	}

	class Ecologist extends Strategy{

		private long min_trash;
//it is going to be the smallest amount of trash that is going to be produced
		private long[] sum_of_set;
//it is sum of lengths of sections which are going to be made from one Bar
		private int[] division;
//it shows how sections from order are gonig to be divided into groups,
//one group is going to be made from one Bar

		public Ecologist(
			order_pricelist.Order order,
			order_pricelist.PriceList pricelist
			){
			super(order, pricelist);
			this.min_trash = order.get_sum();
			sum_of_set = new long[1];
			division = new int[order.get_tab_length()];
			}

		public bars.BarsSet divide(){
			int[] tab = new int[order.get_tab_length()];
			help_divide(0,0,tab);
//recursion finds the best division of sections into groups
//this information is used to make proper BarsSet
			bars.BarsSet set_of_bars = B.new BarsSet();
			for(int i=0;i<sum_of_set.length;i++){
				bars.Bar bar = pricelist.find_min(sum_of_set[i]);
				bars.Bars curr = B.new Bars(bar);
				for(int j=0;j<division.length;j++){
					if(division[j] == i){
						curr.addBar(order.get_bar_length(j));
					}
				}
				set_of_bars.addBars(curr);
			}
			return set_of_bars;
		}

		private void help_divide(int how_many_sets, int i, int tab[]){
//recursion, it looks for all divisions of set of indexes and check which are the best
			if(i < tab.length){
				for(int s=0;s<how_many_sets+1;s++){
					tab[i] = s;
					int a = how_many_sets;
					if(s == how_many_sets){
						a++;
					}
					help_divide(a, i+1, tab);
				}
			}else{
					long[] sets = new long[how_many_sets];
//it is going to show which Bars from pricelist are going to be cut
//in order to produce sections from a group
				for(int s=0;s<how_many_sets;s++){
					sets[s] = 0;
				}
				for(int j=0;j<tab.length;j++){
					sets[tab[j]] = sets[tab[j]]+order.get_bar_length(j);
				}
//summing up lenghts of sections in certain group
				long trash = 0;
				for(int s=0;s<how_many_sets;s++){
					bars.Bar bar = pricelist.find_min(sets[s]);
//checking if for each group exists Bar which is long enough
					if(bar == null){
						trash = order.get_sum();
//if one group needs too long Bar, trash is set very big
					}else{
						trash = trash + bar.get_length() - sets[s];
					}
				}
				if(trash < this.min_trash){
//if certain division is better updates data
					this.min_trash = trash;
					this.sum_of_set = sets;
					for(int j=0;j<tab.length;j++){
						this.division[j] = tab[j];
					}
				}
			}
		}
	}

	class Economist extends Strategy{

		private long min_price;
//it is going to be the lowest price of Bars
		private long[] sum_of_set;
		private int[] division;
//these two tables keeps the same information as in previous class

		public Economist(
			order_pricelist.Order order,
			order_pricelist.PriceList pricelist
			){
			super(order, pricelist);

			this.min_price = (order.get_tab_length()*pricelist.get_max());

			sum_of_set = new long[1];
			division = new int[order.get_tab_length()];
			}

		public bars.BarsSet divide(){
			int[] tab = new int[order.get_tab_length()];
			help_divide(0,0,tab);
//recursion finds the best division of sections into groups
//this information is used to make proper BarsSet
			bars.BarsSet set_of_bars = B.new BarsSet();
			for(int i=0;i<sum_of_set.length;i++){
				bars.Bar bar = pricelist.find_min_price(sum_of_set[i]);
				bars.Bars curr = B.new Bars(bar);
				for(int j=0;j<division.length;j++){
					if(division[j] == i){
						curr.addBar(order.get_bar_length(j));
					}
				}
				set_of_bars.addBars(curr);
			}
			return set_of_bars;
		}

		private void help_divide(int how_many_sets, int i, int tab[]){
//looks for the same as in previous class but instead of trash it consider price
			if(i < tab.length){
				for(int s=0;s<how_many_sets+1;s++){
					tab[i] = s;
					int a = how_many_sets;
					if(s == how_many_sets){
						a++;
					}
					help_divide(a, i+1, tab);
				}
			}else{
				long[] sets = new long[how_many_sets];
				for(int s=0;s<how_many_sets;s++){
					sets[s] = 0;
				}
				for(int j=0;j<tab.length;j++){
					sets[tab[j]] = sets[tab[j]]+order.get_bar_length(j);
				}
				long price = 0;
				for(int s=0;s<how_many_sets;s++){
					bars.Bar bar = pricelist.find_min_price(sets[s]);
					if(bar != null){
						price = price + bar.get_price();
					}else{
						price =(order.get_tab_length()*pricelist.get_max());
					}
				}
				if(price < this.min_price){
					this.min_price = price;
					this.sum_of_set = sets;
					for(int j=0;j<tab.length;j++){
						this.division[j] = tab[j];
					}
				}
			}
		}
	}
}
