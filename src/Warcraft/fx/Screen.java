package Warcraft.fx;

import Warcraft.StdDraw;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;

import java.awt.*;

public class Screen {
	private Vec2i size;
	private int nTileX;
	
	
	public Screen(Vec2i size, int nTileX){
		this.nTileX = nTileX;
		this.size = size;
		
		StdDraw.setCanvasSize(size.x, size.y);
		StdDraw.enableDoubleBuffering();
	}
	
	public Vec2i getnTiles() {
		return new Vec2i(nTileX, nTileX*size.y/size.x);
	}
	private double getSizeTile() {
		return 1.0 / nTileX;
	}
	public void render(){
		StdDraw.show();
	}
	
	
	public Vec2 worldPosToScreen(Vec2i pos){
		return worldPosToScreen(new Vec2(pos));
	}
	public Vec2 worldPosToScreen(Vec2 pos){
		return (new Vec2(pos)).add(0.5).mul(getSizeTile()).mul(new Vec2(1, getRatio()));
	}
	public Vec2 worldScaleToScreen(Vec2i pos){
		return worldScaleToScreen(new Vec2(pos));
	}
	public Vec2 worldScaleToScreen(Vec2 pos){
		return pos.mul(getSizeTile()).mul(new Vec2(1, getRatio()));
	}
	
	public double getRatio(){
		return (double)size.x/size.y;
	}
	public Vec2i getSize() {
		return size;
	}
	
	public void drawRectangle(Vec2 pos, Vec2 halfSize, Color c){
		pos = worldPosToScreen(pos);
		halfSize = worldScaleToScreen(halfSize);
		
		StdDraw.setPenColor(c);
		StdDraw.filledRectangle(pos.x, pos.y, halfSize.x, halfSize.y);
	}
	public void drawRectangle(Vec2i pos, Vec2 halfSize, Color c){
		drawRectangle(new Vec2(pos), halfSize, c);
	}
	public void drawImage(Vec2 pos, Vec2 size, String file, double angle){
		pos = worldPosToScreen(pos);
		size = worldScaleToScreen(size);
		
		StdDraw.picture(pos.x, pos.y, file, size.x, size.y, angle);
	}
	public void drawImage(Vec2i pos, Vec2 size, String file, double angle){
		drawImage(new Vec2(pos), size, file, angle);
	}
	
	public void clear() {
		StdDraw.clear();
	}
}
