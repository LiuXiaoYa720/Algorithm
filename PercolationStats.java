
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private final int trials;
    private final double[] opensizes; 
    private final double c=1.96;
    public PercolationStats(int n,int t){
        if(n<=0||t<=0){
            throw new IllegalArgumentException();
        }
        trials=t;
        opensizes=new double[t];
        int row;
        int col;
        for(int i=0;i<trials;i++){
            Percolation grid=new Percolation(n);
            while(!grid.percolates()){
                do{
                    row=StdRandom.uniform(1,n+1);
                    col=StdRandom.uniform(1,n+1);
                }while(grid.isOpen(row,col));
                grid.open(row,col);
            }
            double opensize=(double)grid.numberOfOpenSites()/(n*n);
            /*if(i%10==0){
                System.out.println(grid.numberOfOpenSites());
                System.out.println(size*size);
                System.out.println(opensize);
            }*/
            opensizes[i]=opensize;//store each iteration's opensize
        }
        
    }
    public double mean(){
        return StdStats.mean(opensizes);
    }
    public double stddev(){
        return StdStats.stddev(opensizes);
    }
    public double confidenceLo(){
        double mean=mean();
        double std=stddev();
        return mean-c*Math.sqrt(std/trials);
    }
    public double confidenceHi(){
        double mean=mean();
        double std=stddev();
        return mean+c*Math.sqrt(std/trials);
    }
    public static void main(String[] args){
        //get the input number
        int n=Integer.parseInt(args[0]);
        int t=Integer.parseInt(args[1]);
        PercolationStats grid=new PercolationStats(n,t);
        System.out.println("mean=                     "+grid.mean());
        System.out.println("stddev=                   "+grid.stddev());
        System.out.println("95% confidence interval = ["+grid.confidenceLo()+", "+grid.confidenceHi()+"]");
    }
}

