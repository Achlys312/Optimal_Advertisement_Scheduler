import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SlotCalculator {
    public static void main (String[] args) throws FileNotFoundException
    {
        System.out.print("Enter a file name: ");
        Scanner scanner = new Scanner(System.in);
        File Data = new File(scanner.nextLine());
        System.out.print("Enter Timeslot of advertisement1: ");
        int Timeslot  = scanner.nextInt();
        System.out.print("Enter Timeslot of advertisement2: ");
        int Timeslot1=scanner.nextInt();
        System.out.print("Enter the required TRP : ");
        int requiredTrp  = scanner.nextInt();
        scanner.close();

        Scanner fscanner = new Scanner (Data);
        ArrayList<String> DataArray = new ArrayList<String>();
        while(fscanner.hasNext()) {
            DataArray.add(fscanner.nextLine());
        }
        fscanner.close();

        SlotCalculator Finals = new SlotCalculator();
        int Size  = DataArray.size();
        //int Timeslot = Integer.parseInt(DataArray.get(0));
        int[] Time = new int[Size + 1];
        int[] Cost = new int[Size + 1];
        String [] Brand= new String [Size + 1];
        int [] currentTrp = new int[Size + 1];
        for(int i = 0; i < DataArray.size(); i++)
        {
            String[] split = DataArray.get(i).split(",");
            currentTrp[i] = Integer.parseInt(split[3]);
            if (requiredTrp <=currentTrp[i]){
                Brand[i] = (split[0]);
                Time[i] = Integer.parseInt(split[1]);
                Cost[i] = Integer.parseInt(split[2]);
            }
            else{
                i++;
            }
        }
        Finals.solve(Brand,Time, Cost, Timeslot, Timeslot1, Size,currentTrp);
    }

    public void solve(String [] Brand,int[] Time, int[] Cost, int Timeslot, int Timeslot1, int size,int [] currentTrp)
    {
        // matrix which will store results
        int[][] Final = new int[size + 1][Timeslot + 1];
        int[][] Final1 = new int[size + 1][Timeslot1 + 1];
        // Fill in each row of matrix
        for (int i = 1; i <= size; i++)
        {
            // comparing each weight one by one
            for (int w = 0; w <= Timeslot; w++)
            {
                if (i == 0 || w == 0)
                    Final[i][w] = 0;
                else if (Time[i - 1] <= w)
                    Final[i][w] = Math.max(Cost[i - 1] +
                            Final[i - 1][w - Time[i - 1]], Final[i - 1][w]);
                else
                    Final[i][w] = Final[i - 1][w];
            }
            // comparing each weight one by one
            for (int w = 0; w <= Timeslot1; w++)
            {
                if (i == 0 || w == 0)
                    Final1[i][w] = 0;
                else if (Time[i - 1] <= w)
                    Final1[i][w] = Math.max(Cost[i - 1] +
                            Final1[i - 1][w - Time[i - 1]], Final1[i - 1][w]);
                else
                    Final1[i][w] = Final1[i - 1][w];
            }
        }
        // Result of the knapsack algorithm
        int Revenue = Final[size][Timeslot];
        int w = Timeslot;
        System.out.println("Total revenue generated1: "+ Revenue);
        System.out.println("BRAND " + " TIME " + " COST "+" TRP ");
        for (int i = size; i > 0 && Revenue > 0; i--) {
            if (Revenue == Final[i - 1][w])
                continue;
            else {

                System.out.println( Brand[i - 1] +("   ")+ Time[i - 1]+("   ")+ currentTrp[i - 1] );
                // value is deducted as weight is included in knapsack
                Revenue = Revenue - Cost[i - 1];
                w = w - Time[i - 1];
            }
        }
        int Revenue1 = Final1[size][Timeslot1];
        int y = Timeslot1;
        System.out.println("Total revenue generated2 : "+ Revenue1);
        System.out.println("BRAND " + "  TIME " +"COST"+ " TRP ");

        for (int i = size; i > 0 && Revenue1 > 0; i--) {
            if (Revenue1 == Final1[i - 1][y])
                continue;
            else {

                System.out.println( Brand[i - 1] +("   ")+ Time[i - 1]+("   ")+ currentTrp[i - 1] );
                // value is deducted as weight is included in knapsack
                Revenue1 = Revenue1 - Cost[i - 1];
                y = y - Time[i - 1];
            }
        }
    }
}