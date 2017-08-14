import java.util.*;
import java.math.*;

public class GeneticAlgorithm {
    
    //Initialization
    final static int populationSize = 10;//
    final static int cs = 6; // chromosome lenght
    final static int si = 1000;//stopping iteration , one of stopping condition
    final static int parentNum = 8;//steady state model
    final static int mutationRate = 30;
    
    static int s = 0; //guna untuk iterate
    static boolean found = false;
    static Population win = new Population();
    static int[] idola = new int[6]; //target chromosome    
    static Population[] pop = new Population[populationSize];
    static Population[] cross = new Population[parentNum];
    
    /*
     *      NOTE
     *      ALL THESE PAREMETERS SETTINGS HAS BEEN TUNED
     *      IT HAS BEEN CONCLUDED
     *      THAT THIS SHOWS THE BEST RESULT : SINCE IT WONT STUCK IN LOCAL OPTIMA
     *      p/s : crossover rate is not set (basically its 1), because it would slow down the process;
     *      p/s : paremeter control will be considered on parent number.
     *      final static int populationSize = 10;//
     *      final static int cs = 6; // chromosome lenght
     *      final static int si = 10000;//stopping iteration , one of stopping condition
     *      final static int parentNum = 8;//steady state model
     *      final static int mutationRate = 30;
    */
    
    public static void main(String[] args)
    {
       long startTime = System.currentTimeMillis();
       
       
       //create population       
       //ideal chromosome
       idola[0] = 0;
       idola[1] = 0;
       idola[2] = 1;
       idola[3] = 1;
       idola[4] = 0;
       idola[5] = 0;
       //[0][0][1][1][0][0]
       

       
       System.out.print("POPULATION INITIALIZATION\n");
       System.out.print("*************************\n");
       Initialization();
       System.out.print("\nFITNESS INITIALIZATION\n");
       System.out.print("**********************\n");
       FitnessEva(); // calculate and set fitness 
        
       
       while (s<si && !found)
       {
           // calculate and set fitness 
           RankSelection();//PARENT SELECTION
           showPop();//dalam ni dia cari stopping condition           
           System.out.println("iteration : " + s + "  Best fitness : " + pop[0].fitness);
            Crossover();
           SteadyStateSelection(); //SURVIVOR SELECTION
           FitnessEva();
           
           s++;           
       }
       if(found)
       {
           showPop();
           System.out.print("**********************\n");
           System.out.print("Winner : ");
           
            for(int i =0; i <6; i++){
                   System.out.print(win.chromosome[i] + " " );
                   }
           System.out.print("   <------- TARGET ");
           System.out.print("\nFitness : "+ win.fitness);
           System.out.print("\nIteration : "+ s);
           System.out.print("\n**********************");
           System.out.println();
        }
        else
        {
            showPop();
           System.out.print("Winner : ");
            for(int i =0; i <6; i++){
                   System.out.print(pop[0].chromosome[i] + " " );
                   }
           System.out.print("\n Fitness : "+ pop[0].fitness);
           System.out.print("\n Iteraion : "+ s);
           System.out.println();
        }
      
       
       long endTime   = System.currentTimeMillis();
       long totalTime = endTime - startTime;
       System.out.println("Time taken : " +totalTime + " Millisecond" );
    }
    
    public static void Initialization()
    {
         for(int p =0; p < populationSize; p++){
           
           pop[p] = new Population();           
           for(int i =0; i <6; i++){
           System.out.print(pop[p].chromosome[i] + " " );
           }
           System.out.println();
       }
       for(int p=0; p < parentNum;p++)
       {cross[p] = new Population();}
       
    }
    public static void FitnessEva()
    {
        //fitness function
        int sum;
        for(int x =0; x<populationSize; x++){
            sum = 0;
        for(int i = 0; i < cs; i++)
        {
            sum += Math.abs(pop[x].chromosome[i] - idola[i]);// Fitness Function
            //System.out.print(Math.abs(pop[x].chromosome[i] - idola[i]) + " x ");
        }
       // System.out.print(" : " + sum);
       // System.out.println();
        
            pop[x].setFitness(sum);
            if(pop[x].fitness == 0){
                   found = true;
                   win = (Population)pop[x];
                }
        }
    }
    public static void RankSelection()
    {
        //rank selection : sort dulu, pastu amik dari atas je
        Population temp = new Population();
        for(int i =1; i < populationSize-1; i++){
            for(int z =0; z < populationSize-1; z++)
            {
                
                if(pop[z].fitness > pop[z+1].fitness)
                {
                    //System.out.println(z + " p :  " + pop[z].fitness + " p+1 : " + pop[z+1].fitness + " "+ (z+1) );
                    
                    temp.setAll(pop[z].chromosome, pop[z].fitness);
                    
                    pop[z].setAll(pop[z+1].chromosome, pop[z+1].fitness);
                    
                    pop[z+1].setAll(temp.chromosome, temp.fitness);
                }
            }
        }

    }
     public static void Crossover()//1point crossover , middle point
    {
        //setiap pasangan produce dua anak, so next population bilangannye sama
            for(int x =0; x < parentNum; x+=2){
            
                
            //mom first half
            for(int i = 0; i < 3; i++)
            {
                cross[x].setPop(i,pop[x].chromosome[i]);
            }
            //daddy second half
            for(int i = 3; i < 6; i++)
            {
               cross[x].setPop(i,pop[x+1].chromosome[i]);
            }
           
            //daddy first half            
            for(int i = 0; i < 3; i++)
            {
                cross[x+1].setPop(i,pop[x+1].chromosome[i]);
            }
            //mom second half
            for(int i = 3; i < 6; i++)
            {
               cross[x+1].setPop(i,pop[x].chromosome[i]);
            }
            
            Random randy = new Random();
            
            int mr;
            mr = randy.nextInt(100);
            if(mr < mutationRate){
             Mutation(cross[x].chromosome,x);
             
            }
            mr = randy.nextInt(100);
            if(mr < mutationRate){
             Mutation(cross[x+1].chromosome,x+1);
             
            }
            //children.add(fetusBongsu);
        }
        
        
    }
     public static void Mutation(int[] crosser, int index)
    {
        Random randy = new Random();
        int []transfer = new int[6];
        transfer = crosser;
        
        int temp;
        int ind1,ind2;
        if(transfer[3] <= 3){
        ind1 = randy.nextInt(5); //max index2
        ind2 = randy.nextInt(5); //max index2
        }else
        {
        ind1 = randy.nextInt(3); //max index2
        ind2 = randy.nextInt(3); //max index2
        }
        
        //swap
        temp = transfer[ind1];
        transfer[ind1] = transfer[ind2];
        transfer[ind2] = temp;
        System.out.println("Mutation Happen "+ ind1 + " <-> "+ ind2);
        cross[index].setChromo(transfer);
    }
     public static void SteadyStateSelection()
    {
        Population temp = new Population();
        RankSelection();// nak sort balik
        
      
         //N pasangan dipilih setiap iteration
            //System.out.println("size : " + children.size());
            for(int pa=0; pa <parentNum; pa++)
            {   
                //AMIK DARI BAWAH | bila sort automatic bawah lowest fitness
                pop[populationSize - 1 - pa].setAll(cross[pa].chromosome,cross[pa].fitness);
            
                 /*untuk check sahaja
                 for(int i =0; i <6; i++){
                   System.out.print(pop[pa].chromosome[i] + " " );
                   }
                   System.out.println("child : ");
                 */
                //setiap kali dia update dia terus update calculate fitness skali, jimat processing                 
            }
            //System.out.println("size : " + children.size());

    }
    private static void showPop()
    {
        for(int p =0; p < populationSize; p++)
        {
            if(pop[p].fitness == 0){
               found = true;
               win = (Population)pop[p];
            }
            
           for(int i =0; i <6; i++){
           System.out.print(pop[p].chromosome[i] + " " );
           }
           System.out.print ( " : " + pop[p].fitness);
           System.out.println(" ");

        }
    }

    
}