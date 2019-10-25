
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private boolean[][] grid;
    private final int size;
    private int opensize;
    private final int top=0;
    private final int bottom;
    private final WeightedQuickUnionUF uf;
    public Percolation(int n){
        if(n<=0){
            throw new IllegalArgumentException();
        }
        size=n;
        opensize=0;
        grid=new boolean[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                grid[i][j]=false;
            }
        }//initiate grid[i][j]=0
        bottom=n*n+1;
        uf=new WeightedQuickUnionUF(n*n+2);
    }
    public void open(int row,int col){
        if(row<1||row>size||col<1||col>size){
            throw new IllegalArgumentException();
        }
        if(!grid[row-1][col-1]){
            grid[row-1][col-1]=true;
            if(row==1){
                uf.union(helper(row,col), top);
            }
            if(row==size){
                uf.union(helper(row,col), bottom);
            }
            if(row>1 && isOpen(row-1,col)){
                uf.union(helper(row,col),helper(row-1,col));
            }
            if(row<size&&isOpen(row+1,col)){
                uf.union(helper(row,col),helper(row+1,col));
            }
            if(col>1&&isOpen(row,col-1)){
                uf.union(helper(row,col),helper(row,col-1));
            }
            if(col<size&&isOpen(row,col+1)){
                uf.union(helper(row,col),helper(row,col+1));
            }
            opensize+=1;
        }
    }
    public boolean isOpen(int row,int col){
        if(row<1||row>size||col<1||col>size){
            throw new IllegalArgumentException();
        }
        return grid[row-1][col-1];
    }
    public boolean isFull(int row,int col){
        if(row<1||row>size||col<1||col>size){
            throw new IllegalArgumentException();
        }
        if(isOpen(row,col)){
            return uf.connected(top,(row-1)*size+col);
        }
        else{
            return false;
        }
    }
    public int numberOfOpenSites(){
        return opensize;
    }
    public boolean percolates(){
        return uf.connected(top,bottom);
    }
    private int helper(int row,int col){
        return (row-1)*size+col;
    }
    //Test code
    /*public static void main(String[] args){
        Percolation data=new Percolation(5);
        for(int i=0;i<5;i++){
            for (int j = 0;j<5;j++){
                data.open(i, j);
                System.out.println(i+" "+j);
                System.out.println(data.isOpen(i, j));
                System.out.println(data.isFull(i,j));
                System.out.println(data.numberOfOpenSites());
                System.out.println(data.percolates());    
            }
        }
    }*/
    
}
