public class bars{

	class Bar{
		private long length;
		private long price;

		public Bar(long l, long p){
			this.length = l;
			this.price = p;
		}

		public String toString(){
			return ""+this.length;
		}

		public long get_length(){
			return this.length;
		}
		public long get_price(){
			return this.price;
		}
	}

	class Bars{
//this class shows how Bar is cut
		private Bar parent;
//this is bar taken from pricelist
		private long remainder;
//this shows what length is still available
		private long[] tab = new long[1];
//lengths of sections which are made from paren bar
		private int how_many = 0;
//number of sections
		public Bars(Bar a){
			this.remainder = a.length;
			this.parent = a;
		}

		public String toString(){
			String help = ""+this.parent;
			for(int i=0;i<how_many;i++){
				help = help+" "+tab[i];
			}
			return help;
		}

		public boolean addBar(long l){
//returns true if bar fits in remainder, else return false
//if tab is too small to add next section it is going to be enlaged two times
			if(this.remainder >= l){
				if(this.how_many < this.tab.length){
					tab[how_many] = l;
				}else{
					long[] help = new long[2*how_many];
					for(int i=0;i<how_many;i++){
						help[i] = this.tab[i];
					}
					help[how_many] = l;
					this.tab = help;
				}
				how_many++;
				this.remainder = remainder - l;
				return true;
			}else{
				return false;
			}
		}
	}

	class BarsSet{
//this shows how bars from pricelists are cut
		private Bars[] tab = new Bars[1];
		private int how_many = 0;
//shows how many bars are in table
		private long remainders = 0;
//this is going to be final amount of remainders
		private long price = 0;
//final price
		/*public void removeBars(){
			if(how_many != 0){
				tab[how_many-1] = null;
				how_many--;
			}
		}*/

		public void addBars(Bars a){
			if(this.how_many < this.tab.length){
				tab[how_many] = a;
			}else{
				Bars[] help = new Bars[2*how_many];
				for(int i=0;i<how_many;i++){
					help[i] = this.tab[i];
				}
				help[how_many] = a;
				this.tab = help;
			}
			how_many++;
			this.remainders = this.remainders + a.remainder;
			this.price = this.price + a.parent.price;
		}

		public long get_price(){
			return this.price;
		}

		public String toString(){
			String help = this.price+"\n"+this.remainders;
			for(int i=0;i<how_many;i++){
				help = help+"\n"+this.tab[i];
			}
			return help;
		}
	}
}
