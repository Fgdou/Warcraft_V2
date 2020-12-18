package Warcraft.tools;

public class Vec2i {
	public int x, y;
	
	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Vec2i(){
		x=0; y=0;
	}
	public Vec2i(Vec2 o){
		x = (int)o.x; y = (int)o.y;
	}
	public Vec2i(Vec2i o){
		x = o.x; y = o.y;
	}
	
	public boolean equals(Vec2i v){
		return (x == v.x && y == v.y);
	}
	public boolean equals(Vec2i v, int tolerance){
		return (this.distance(v) < tolerance);
	}
	public double abs(){
		return Math.sqrt(x*x + y*y);
	}
	public double distance(Vec2i v){
		return Math.abs(this.sub(v).abs());
	}
	
	public Vec2i add(int n){
		return new Vec2i(x+n, y+n);
	}
	public Vec2i sub(int n){
		return new Vec2i(x-n, y-n);
	}
	public Vec2i mul(int n){
		return new Vec2i(x*n, y*n);
	}
	public Vec2i div(int n){
		return new Vec2i(x/n, y/n);
	}
	
	public Vec2i add(Vec2i v){
		return new Vec2i(x+v.x, y+v.y);
	}
	public Vec2i sub(Vec2i v){
		return new Vec2i(x-v.x, y-v.y);
	}
	public Vec2i mul(Vec2i v){
		return new Vec2i(x*v.x, y*v.y);
	}
	public Vec2i div(Vec2i v){
		return new Vec2i(x/v.x, y/v.y);
	}
	
	public Vec2 normalize(){
		double r = abs();
		return new Vec2((double)x/r, (double)y/r);
	}
	
	public String toString(){
		return "{" + x + "," + y + "}";
	}
}
