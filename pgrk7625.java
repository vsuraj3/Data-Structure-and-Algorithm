//SURAJ VISHWAKARMA cs610 7625 prp

import java.io.IOException;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;

public class pgrk7625 
{
    public static void main(String[] args) throws IOException 
    {
        if(args.length!=3)
        {
            System.out.println("Please enter the correct argument");
            return;
        }
        
        int iteration=Integer.parseInt(args[0]);
        int initialvalue=Integer.parseInt((args[1]));
        String filename=args[2];
        if (!(initialvalue >= -2 && initialvalue <= 1))
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
        BufferedReader file=new BufferedReader(io_File);
	    String v_edge= file.readLine();
	    String split[]=v_edge.split(" ");
	    
	    pgrk7625 obj=new pgrk7625();
	    obj.vertice=Integer.parseInt(split[0]);
	    obj.edge=Integer.parseInt(split[1]);
        obj.Adjacency_Matrix(obj.edge,obj.vertice,file);
        obj.paging(obj.vertice,initialvalue);
        if (iteration<=0)
        {
            if (iteration==0)
            {
            	int res=-1;
                obj.error_f=Math.pow(10.0,-5.0);
                iteration=res;
            }
            else
            {
            obj.error_f=Math.pow(10.0,iteration+0.0);
            }
            obj.error=1;
        }
        if (obj.vertice>10)
        {
            obj.error_f=Math.pow(10.0,-5+0.0);
            iteration=-1;
           obj.paging(obj.vertice,-1);

        }
        obj.arr_tmp=new double[obj.vertice];
        obj.show_result(false,0);
        int curr_iter=1;
        while(obj.error_f<=obj.error && iteration!=0  )
        {
            Arrays.fill(obj.arr_tmp,0.0);
            obj.error=obj.pgrk(obj.vertice,iteration);
            obj.arr=obj.arr_tmp.clone();
            obj.show_result(obj.vertice > 10 && (iteration == 1 || obj.error_f >obj.error) ,curr_iter);
            curr_iter++;
            iteration--;
        }
    }
	
     double arr_tmp[];
     Integer vertice=null;
     Integer edge=null;
     double arr[];
     double error_f=1;
     double error=2;
      
     ArrayList<ArrayList<Integer>> l;
     DecimalFormat df = new DecimalFormat("0.0000000");
     double cnstnt=0.85;
    
    public double pgrk(int vertice,int iteration)
    {
      for (int i=0;i<vertice;i++)
      {
          for(int j=0;j<l.get(i).size();j++)
          {
              arr_tmp[l.get(i).get(j)]+=arr[i]/(l.get(i).size()+0.0);
          }
      }
      double m=-1;
      for (int i=0;i<vertice;i++)
      {
          arr_tmp[i]=(cnstnt*arr_tmp[i]+((1-cnstnt)/vertice));
          if(Math.abs(arr_tmp[i]-arr[i])>m)
          {
              m=Math.abs(arr_tmp[i]-arr[i]);
          }
      }
      if (iteration<=0)
      {
          return m;
      }
      return 2;
  }
   public void paging(int vertice, int initialvalue)
    {
       double v[]={ (1/Math.sqrt(vertice)),1/(vertice+0.0),0,1};
       arr=new double[vertice];
       Arrays.fill(arr,v[2+initialvalue]);
   }
    
    public void Adjacency_Matrix(int edge , int vertice, BufferedReader txt ) throws IOException 
    {
        String str;
        l=new ArrayList<>();
        for (int i=0;i<vertice;i++)
        {
            l.add(new ArrayList<Integer>());
        }
        while((str=txt.readLine())!=null) 
        {
            String Splt[] = str.split(" ");
            l.get(Integer.parseInt(Splt[0])).add(Integer.parseInt(Splt[1]));
        }
    }

    public void show_result(boolean last_iter,int iteration)
     {
        String op_str="";
        if (iteration==0) 
        {
            op_str =op_str + "Base : " + iteration + " :";
        }
        else 
        {
            op_str =op_str + "Iter : " + iteration + " :";
            if(vertice>10 && last_iter)
            {
                System.out.println("Iter : " + iteration );
            }
        }
        for(int i=0;i<arr.length;i++)
        {
            op_str=op_str +"P["+i+"]="+ df.format(arr[i])+" ";
            if(vertice>10 && last_iter)
            {
                System.out.println("P ["+i+"] = "+ df.format(arr[i])+" ");
            }
        }
        if(vertice<=10)
        {
        System.out.println(op_str);
        }
        else if(last_iter) 
        {
            System.out.println();
        }
    }

}
