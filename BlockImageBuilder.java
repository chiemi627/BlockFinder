import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class BlockImageBuilder {

	public int width;
	public int height;
	public ArrayList<Block> blocks = new ArrayList<Block>();
	public ArrayList<Block> areas = new ArrayList<Block>();

	public ArrayList<Block> readBlockLayout(String filename) throws FileNotFoundException, IOException, Exception{
		ArrayList<Block> blks = new ArrayList<Block>();
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		String line = new String();
		for(int i=0;i<2;i++){
			line = br.readLine();
		}
		String[] pref = line.split(",");
		if(pref.length!=2){
			br.close();
			throw new Exception("The File does not have any file info.");
		}
		width = new Integer(pref[0]).intValue();
		height = new Integer(pref[1]).intValue();
		for(int i=0;i<2;i++){
			line = br.readLine();
		}
		
		while((line=br.readLine())!=null){
			String[] data = line.split(",");
			Block blk = new Block();
			blk.x = new Integer(data[1]).intValue();
			blk.y = new Integer(data[2]).intValue();
			blk.width = new Integer(data[3]).intValue();
			blk.height = new Integer(data[4]).intValue();
			blks.add(blk);
		}
		br.close();
		return blks;
	}
	
	public void writeBlocks(String filename){
		BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D gr = img.createGraphics();
		gr.setBackground(Color.white);
		gr.clearRect(0, 0, this.width, this.height);
		for(int i=0;i<this.blocks.size();i++){
			Block b = this.blocks.get(i);
			gr.setPaint(Color.BLACK);
			gr.fillRect(b.x, b.y, b.width, b.height);
		}
		BasicStroke st = new BasicStroke(2.0f);
		gr.setStroke(st);
		for(int i=0;i<this.areas.size();i++){
			Block b = this.areas.get(i);
			gr.setPaint(Color.RED);
			gr.drawRect(b.x, b.y, b.width, b.height);
		}
		try {
			ImageIO.write(img, "BMP", new File(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BlockImageBuilder builder = new BlockImageBuilder();
		try {
			builder.blocks = builder.readBlockLayout(args[0]);
			builder.areas = builder.readBlockLayout(args[1]);
			builder.writeBlocks(args[2]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
