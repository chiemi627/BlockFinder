import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class BlockFinder {

	BufferedImage bi;
	ArrayList<Block> blocks = new ArrayList<Block>();
	int width;
	int height;


	public static void main(String[] args) {
		// TODO Auto-generated method stub291
		BlockFinder bf = new BlockFinder();
		bf.readBmpFile(args[0]);
		if(bf!=null){
			int[] start = {0,0};
			int[] end = {bf.width-1,bf.height-1};
			int direction = 0;
			bf.getBlocks(start,end,direction);
		}
		bf.outputBlocks2CSV(args[1]);
	}
	
	public void readBmpFile(String fname){
		try {
		      bi = ImageIO.read(new File(fname));
		      width = this.bi.getWidth();
		      height = this.bi.getHeight();

		    } catch (Exception e) {
		      e.printStackTrace();
		      bi = null;
		    }		
	}	

	public void getBlocks(int[] start,int[] end,int direction){
		int startno = this.getPositionNumber(start, direction);
		int endno =this.getPositionNumber(end, direction); 
		
		for(int i=startno;i<=endno;i++){			
			int[] pos = getPosition(i,direction);
			int color = this.bi.getRGB(pos[0], pos[1]);
			if((color&0xFFFFFF)==0x00){
				Block blk = getBlock(pos);
				this.blocks.add(blk);
				blk.printBlock();
			}
		}
	}
	
	public Block getBlock(int[] pos){
		Block block = new Block();
		block.x = pos[0]; block.y = pos[1];
		int[] cur = new int[2];
		cur[0]=pos[0]; cur[1]=pos[1];
		block.width = 0;
		block.height = 0;
		//get width
		while((this.bi.getRGB(cur[0], cur[1])&0xFFFF) == 0x00){
			block.width++;
			cur[0]++;
		}
		cur[0]=pos[0];
		//get height
		while((this.bi.getRGB(cur[0], cur[1])&0xFFFF) == 0x00){
			block.height++;
			cur[1]++;
		}
		
		if(block.width>=block.height){
			block.orientation=0;
		}else{
			block.orientation=1;
		}
		
		//remove the block
		for(int i=0;i<block.height;i++){
			for(int j=0;j<block.width;j++){
				cur[0]=pos[0]+j;
				cur[1]=pos[1]+i;
				this.bi.setRGB(cur[0], cur[1], -1);
			}
		}

		return block;
	}
	
	public int[] getPosition(int no,int direction){
		int[] pos = new int[2];
		if(direction == 0){ // left to right
			pos[1] = (int)((double)no/(double)this.width);
			pos[0] = no%this.width;
		}
		else{ //top to bottom
			pos[0] = (int)((double)no/(double)this.height);
			pos[1] = no%this.height;			
		}
		return pos;
	}
	
	public int getPositionNumber(int[] pos,int direction){
		int no=0;
		if(direction == 0){
			no = pos[1] * width + pos[0];
		}
		else{
			no = pos[0] * height + pos[1];
		}
		return no;
	}
	
	public int[] getColor(int col){
		int[] rgb = new int[3];
		rgb[0] = col & 0xFF;
		rgb[1] = (col >>> 2) & 0xFF;
		rgb[2] = (col >>> 4) & 0xFF;
		return rgb;
	}
	
	public void outputBlocks2CSV(String filename){
		try {
			FileWriter fw = new FileWriter(filename, false);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			for(int i=0;i<this.blocks.size();i++){
				Block b = this.blocks.get(i);
				pw.write(b.toString()+"\n");
			}
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
