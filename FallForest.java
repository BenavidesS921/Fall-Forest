import java.io.*;
import java.util.Random;
 
class Branch {
    private int leaves;
    private Branch[] subBranches;
 
    public Branch(int leaves, int subCount) {
        this.leaves = leaves;
        subBranches = new Branch[subCount];
        Random rand = new Random();
        for (int i = 0; i < subCount; i++) {
            subBranches[i] =
                new Branch(rand.nextInt(3) + 1, rand.nextInt(2));
        }
    }
 
    public void fallLeaves(int level) {
        Random wind = new Random();
        int windStrength = wind.nextInt(3) + 1;

        if (leaves <= 0 && subBranches.length == 0) return;
 
        if (leaves > 0) {
            int leavesFalling = Math.min(windStrength, leaves);
            for(int i = 0; i < leavesFalling; i++){
                System.out.println(" ".repeat(level * 2)
                    + "🍂  A leaf falls from branch level " + level);
            }
            leaves -= leavesFalling;
            if (leaves > 0){
                fallLeaves(level);          // recurse on same branch
            }
        }
 
        for (Branch b : subBranches) {  // recurse into sub-branches
            b.fallLeaves(level + 1);
        }
    }

    public void growLeaves(int level){
        Random rain = new Random();
        int rainStrength = rain.nextInt(3) + 1;
        if (leaves == 0) {
            int leavesGrowing = Math.max(rainStrength, leaves);
            for(int i = 0; i < leavesGrowing; i++){
                System.out.println(" ".repeat(level * 2)
                    + "🍀  A leaf grows from branch level " + level);
            }
            leaves += leavesGrowing;
            growLeaves(level);          
            
        }
 
        for (Branch b : subBranches) {  
            b.growLeaves(level + 1);
        }
    }

    public void cLogFile(){
        try{
            FileOutputStream fos = new FileOutputStream("leaf_fall_log.txt");
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
            ps.println("The forest prepares for autumn...");
            fallLeaves(0);
            ps.println("The forest sleeps for winter.");
            ps.println();
            ps.println("The forest regenerates...");
            growLeaves(0);
            ps.println("The forst wakes for spring.");

            ps.close();
            fos.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
 
public class FallForest {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(500);

        System.out.println("The forest prepares for autumn...");
        Branch tree = new Branch(3, 2);
        tree.fallLeaves(0);
        System.out.println("The forest sleeps for winter.");
        
        System.out.println();

        System.out.println("The forest regenerates...");
        tree.growLeaves(0);
        System.out.println("The forst wakes for spring.");

        tree.cLogFile();
    }
}
