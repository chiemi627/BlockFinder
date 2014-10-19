
public class Block {
	public int x;
	public int y;
	public int width;
	public int height;
	public int orientation; //0:sideways,1:longways 
	
	public void printBlock(){
		String ori;
		if(orientation==0){
			ori="sideways";
		}
		else{
			ori="longways";
		}
		System.out.println("pos=("+x+","+y+"), width="+width+", height="+height+",orientation="+ori);
	}
	
	public String toString(){
		String ori;
		if(orientation==0){
			ori="sideways";
		}
		else{
			ori="longways";
		}
		String ret = ""+x+","+y+","+width+","+height+","+ori;
		return ret;
	}	
}
