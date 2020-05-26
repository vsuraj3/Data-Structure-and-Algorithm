//SURAJ VISHWAKARMA cs610 7625 prp

import java.io.IOException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

public class hits7625
{
	 public static void main(String[] args) throws IOException 
	 {
	        if(args.length!=3)
	        {
	            System.out.println("Please enter the correct argument");
	            return;
	        }
	        int iterations=Integer.parseInt(args[0]);
	        int intialvalues=Integer.parseInt(args[1]);
	        String filename=args[2];

	        if ( !(intialvalues >= -2 && intialvalues <= 1))
	        {
	            System.out.println("Enter the initial value as -2, -1, 0, 1");
	            return;
	        }
	        FileReader io_File=null;
	        try
	        {
	            io_File=new FileReader(filename);
	        }
	        catch (FileNotFoundException e)
	        {
	        	System.out.println("File Not Found");
	        	System.exit(1);
	        }
	      // Scanner sc=new Scanner(io_File);
	        BufferedReader file=new BufferedReader(io_File);
	       String v_edge= file.readLine();
	     //  String v_edge= sc.next()();
	        String split[]=v_edge.split(" ");
	        hits7625 obj=new hits7625(); 
	        obj.vertice=Integer.parseInt(split[0]);
	        obj.edge=Integer.parseInt(split[1]);
	        obj.Mat=obj.Adjacency_Matrix(obj.edge,obj.vertice,file);
	        obj.hub=obj.Hub(intialvalues, obj.vertice);
	        obj.auth=obj.Auth_Set(intialvalues,obj.vertice);
	        if (iterations<=0)
	        {
	            if(iterations==0)
	            {
	                obj.error_f=Math.pow(10.0,-5+0.0);
	                iterations=-1;
	            }
	            else
	            {
	            obj.error_f=Math.pow(10.0,iterations+0.0);
	            }
	            obj.error=1;
	        }

	        if (obj.vertice>10)
	        {
	        	int ite=-1;
	            obj.error_f=Math.pow(10.0,-5+0.0);
	            obj.hub=obj.Hub(-1, obj.vertice);
	            obj.auth=obj.Auth_Set(-1,obj.vertice);
	            iterations=ite;
	        }
	        obj.show_result(false, 0);
	        int curr_iter=1;
	        while(obj.error_f<=obj.error && iterations!=0) 
	        {
	            obj.auth_prev=obj.auth.clone();
	            obj.hub_prev=obj.hub.clone();
	            obj.auth_rev();
	            obj.hub_rev();
	            obj.error=obj.scaling(iterations);
	            obj.show_result(obj.vertice > 10 && (iterations == 1 || obj.error_f > obj.error ) , curr_iter);
	            curr_iter++;
	            iterations--;
	        }
	    }
	 DecimalFormat df = new DecimalFormat("0.0000000"); 
	 double auth[];
	 double auth_prev[];
	 double hub[];
	 double hub_prev[];
	 int Mat[][]=null;
	 double error_f=1;
	 double error=2;
	 double hubSum_sqr=0.0;
	 double authSum_sqr=0.0;
	 Integer vertice=null;
	 Integer edge=null;

	public double[] Hub(int initialvalue,int vertice)
    {
    	double hub[];
    	hub=new double[vertice];
    	double[] v={1/Math.sqrt(vertice),1/(vertice+0.0),0,1};
        Arrays.fill(hub,v[2+initialvalue]);
        return hub;
    }
	public void hub_rev()
    {
    	 hubSum_sqr=0.0;
        for(int i=0;i<Mat.length;i++)
        {
            double ttl = 0.0;
            for (int j = 0; j < Mat[0].length; j++) 
            {
                ttl=ttl+(Mat[i][j] * auth[j]);
            }
            hub[i]=ttl;
            hubSum_sqr=hubSum_sqr+(hub[i]*hub[i]);
        }
    }
    
	public double[] Auth_Set(int initialvalue,int vertice)
    {
    	double auth[];
    	auth=new double[vertice];
    	double v[]={1/Math.sqrt(vertice),1/(vertice+0.0),0,1}; 
        Arrays.fill(auth,v[2+initialvalue]);
        return auth;
    }
    
	public void auth_rev()
    {
        authSum_sqr=0.0;
        for(int i=0;i<Mat.length;i++)
        {
            double ttl=0.0;
            for (int j=0;j<Mat[0].length;j++)
            {
                ttl=ttl+(Mat[j][i]*hub[j]);
            }
            auth[i]=ttl;
            authSum_sqr=(authSum_sqr+(auth[i]*auth[i]));
        }
    }
	public int[][] Adjacency_Matrix(int edge,int vertice,BufferedReader txt) throws IOException,SecurityException 
    {
    	int res=1;
    	int Mat[][];
    	Mat=new int[vertice][vertice]; 
    	String t;
        while((t=txt.readLine())!=null)
        {
        	String[] splitted_items=t.split(" ");
            Mat[Integer.parseInt(splitted_items[0])][Integer.parseInt(splitted_items[1])]=res;
        }
        return Mat;
    }
   
	public double scaling(int iterations)
    {  	
        double m=-1;
        for(int i=0;i<hub.length;i++)
        {
            hub[i]=(hub[i]/Math.sqrt(hubSum_sqr));
            auth[i]=(auth[i]/Math.sqrt(authSum_sqr));
            m=Math.max(Math.max(Math.abs(hub[i]-hub_prev[i]),Math.abs(auth[i]-auth_prev[i])),m);
        }
        if (iterations<=0)
        {
            return m;
        }
        return 2;
    }

	public void show_result(boolean last_iter, int iteration)
	{
        String op_str="";
        if (iteration==0) 
        {
            op_str =op_str + "Base : " + iteration + " :";
        }
        else 
        {
            op_str = op_str+ "Iter : " + iteration + " :";
            if(vertice>10 && last_iter)
            {
                System.out.println("Iter : " + iteration );
            }
        }
        for(int i=0;i<hub.length-1;i++)
        {
            op_str=op_str+"A/H["+i+"]="+ df.format(auth[i])+"/"+df.format(hub[i])+" ";
            if(vertice>10 && last_iter)
            {
                System.out.println("A/H ["+i+"] = "+ df.format(auth[i])+"/"+df.format(hub[i])+" ");
            }
        }
        if(vertice<=10)
        {
            System.out.println(op_str);
        }
        else if (last_iter)
        {
            System.out.println();
        }
    }
   
}
