import java.util.*;

public class Population {
   
    public int [] chromosome = new int [6];
    public int fitness;
    

    public Population()
    {
        fitness = 0;
        initialization();
    }
    
    public void initialization()
    {
        Random randy = new Random();
        
        //value2 yang akan digunakan untuk genes dalam choromosome
        int dua = randy.nextInt(2); // untuk genes limit 4
        int tiga = randy.nextInt(3); //untuk genes limit 3
        int empat = randy.nextInt(4); // untuk genes limit 4
        
        for(int i = 0; i < chromosome.length; i++)
        {
            tiga = randy.nextInt(3);
            chromosome[i] =  tiga;
        }
        
        dua = randy.nextInt(2);
        empat = randy.nextInt(4);
        chromosome[3] = empat;
        chromosome[5] = dua;
    }
    
    public int getFitness()
    {
        return fitness;
    }
    public void setFitness(int newFitness)
    {
        fitness = newFitness;
    }
    
    
    public int[] getPop()
    {
        return chromosome;
    }
    public void setPop(int index, int value)
    {
       chromosome[index] = value;
    }
    public void setChromo(int[] newChromo)
    {
        chromosome = newChromo;
    }
    public void setAll(int[] newC,int newFitness)
    {
        chromosome = newC;        
        fitness = newFitness;
    }
}