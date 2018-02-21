import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

/**
 * This is the main class for drawing simple symmetric Venn diagrams.
 * <p>
 * A pie slice of a simple symmetric monotone Venn diagram of n curves can be represented by a binary matrix of n-1 rows
 * and (2^n - 2)/n columns such that each column contains exactly one 1 and no two 1's are adjacent in a row. The 1's in
 * the matrix represent the points at which the curves intersect. Similarly, it can be represented by a sequence
 * of length (2^n -2)/n of integers in range [0, n-2] which is called the <b>crossing sequence</b>. The i-th element 
 * indicates the row number of the matrix which contains a 1 at column i. 
 * <p>
 * For example the famous three circle Venn diagram can be represented by the following binary matrix and crossing sequence.
 * 
 * <p><blockcode><pre>
 *   A pie-slice of symmetric 3-Venn diagram : 
 *                            ____   ___________
 *                                \ /
 *                                 *
 *                            ____/ \____   ____
 *                                       \ /
 *                                        *
 *                            ___________/ \____
 *                            
 *    Binary Matrix :              1      0          
 *                            
 *                                 0       1
 *                                 
 *                              ---------------
 *                                 
 *    Crossing Sequence :          0   ,   1  
 * </pre></blockcode>   
 * <p>
 * See <a> href="http://arxiv.org/abs/1207.6452"</a> for more information about simple symmetric Venn diagrams.   
 * <p>
 * Given the number of curves n and an input file containing the crossing sequence of a collection of simple symmetric 
 * Venn diagrams of n curves, this class generates the plot of Venn diagrams in SVG format.
 * 
 * @author khalegh Mamakani
 *
 */

public class VennPlot {
  
	/**
	 * The number of curves of the Venn diagrams.
	 */
	private int n;
	
	/**
	 * The name of the input file.
	 */
	private String inputFileName;
	
	/**
	 * The name of file containing the number and the hex code of colors used for coloring the diagrams.
	 */
	private String colorsFileName;
	
	/**
	 * Array containing the color codes.
	 */
	private String[] colors;
	
	/**
	 * This constructor creates an instance of main application of drawing simple symmetric Venn diagrams.
	 * 
	 * @param n            The number of curves of Venn diagrams.
	 * @param fileName     The name of input file that contains the crossing sequence of Venn diagrams.
	 */
	public VennPlot(int n, String fileName, String colorsFile) {
		this.n = n;
		this.inputFileName = fileName;
		this.colorsFileName = colorsFile;
	}

	/**
	 * This method creates the binary matrix of Venn diagram using the input crossing sequence x.
	 * 
	 * @param x    The crossing sequence of the Venn diagram.
	 * @return     The binary matrix of the Venn diagram.
	 */
    private int[][] createMatrix(int[] x) {
    	
    	//Allocating memory for the matrix.
        int [][] mx = new int[n-1][x.length];
        
        //For every i-th entry in the crossing sequence :
        //      set the entry at row x[i] and column i of the binary matrix to 1.
        for (int i = 0; i < x.length; i++){
        	int r = x[i];
        	mx[r][i] = 1;
        }
        return mx;   	
    }
    
    /**
     * This method reads the hex codes of the colors.
     * 
     * @return Error code. 0 : If there are no errors in reading the color codes. 1 : otherwise.
     */
    private int readColors() {
    	
    	Scanner colorFile = null;
    	try {
    		//Open the colors files.
    		colorFile = new Scanner(new BufferedReader(new FileReader(colorsFileName)));
    		
    		try {
    			//Read the number of colors.
    			int numberOfColors = colorFile.nextInt();
    			
    			//Allocate memory for the array of colors.
    			colors = new String[numberOfColors];
    			
    			//Read the color codes.
    			for (int i = 0; i < numberOfColors; i++) {
    				colors[i] = colorFile.next();
    			}
    		}
    		catch (Exception e) {
    			System.err.println("Error in reading color codes.");
    			return 1;
    		}
    		
    	}
    	catch (java.io.IOException e) {
    		System.err.println("Cannot open the colors file.");
    		return 1;
    	}
    	
    	return 0;
    }
    
    /**
     * This method reads the crossing sequence of Venn diagrams from the input file and draws them.
     */
    private void createDiagrams(int fillingMode, int drawingMode) {
    	
        //Computing the number of intersection points (the length of crossing sequence).
        int len = ((1 << n) - 2) / n;
        
        Scanner in = null;
        try {
        	
        	//Opening input file.
            in = new Scanner(new BufferedReader(new FileReader(inputFileName)));
            try {
		        int [] exchanges = new int[len];
		        
		        //Reading input data
		        while (in.hasNext()) {
		        	//For each Venn diagram,
		        	//1) Read the crossing sequence of diagram from the input file.
		        	for(int i = 0; i < len; i++) {
		        		exchanges[i] = in.nextInt();
		        	}
		        	
		        	//2) Create the binary matrix of diagram using the crossing sequence.
		        	int [][] mx = createMatrix(exchanges);
		        	
		        	//3) Create an instance of VennDiagram and call the appropriate drawing method.
		            VennDiagram diagram = new VennDiagram(mx, colors);
		            
		            if (fillingMode == 1) {
		            	//Create filled SVG diagram in cylindrical or radial mode 
			            diagram.drawFilledDiagram(drawingMode);
		            }
		            else {
		            	//Drawing only the curves of diagram without filling the regions.
		            	if (drawingMode == 0) {
		            		diagram.drawCurves();
		            	}
		            	else {
		            		diagram.drawCylindrical();
		            	}
		            }
		            
		            //diagram.createArcDiagram();
		        }
            }
            catch (Exception e) {
            	//Either there are not enough data items in the input crossing sequence
            	//Or the next item in the input is not an integer.
            	System.err.println("Error : invalid or insufficient data.");
            }
            finally {
            	in.close();
            }
        }
        catch (java.io.IOException e) {
        	//File does not exist or it cannot be opened.
        	System.err.println("Error : Cannot open the input file. No such file exists");
        }
    }
    
    
    /*---------------------------------------------------------------------------------------------*
     *                              The main method of program                                     *
    /*---------------------------------------------------------------------------------------------*/
    public static void main(String[] args) {
    	
    	/* Fetching the command-line arguments. 
    	 * The first argument is the number of curves.
    	 * The second argument is the name of input file of diagrams.
    	 * The third argument is the name file of color codes.
    	 * The forth argument is filling mode  0 : not filled  1 : filled 
    	 * The fifth argument is the drawing mode  0 : regular radial drawing	1 : Cylindrical drawing
    	 */
    	int n = 0;
    	int fillingMode = 1;
    	int drawingMode = 0;
    	
    	if (args.length == 5) {
    		//Reading the number of curves, filling mode and drawing mode
    		try{
    			n = Integer.parseInt(args[0]);
    			fillingMode = Integer.parseInt(args[3]);
    			drawingMode = Integer.parseInt(args[4]);
    			
    			//Check the validity of filling mode and drawing mode
    			if (fillingMode != 0 && fillingMode != 1) {
    				System.err.println("The filling mode could either be 0 or 1");
    				System.exit(1);
    			}

    			if (drawingMode != 0 && drawingMode != 1) {
    				System.err.println("The drawing mode could either be 0 or 1");
    				System.exit(1);
    			}
}
    		catch (NumberFormatException e) {
    			System.err.println("The number of curves must be an integer");
    			System.exit(1);
    		}
    		
    	}
    	else {
    		String errorMessage = "Invalid and/or insufficient arguments.\n" 
    							+ "Five arguments must be supplied.\n"
    							+ "The first argument is an integer specifying the numebr of curves\n"
    							+ "The second argument is a string specifying the name of input file.\n"
    							+ "Third argument is the name of file of the color codes.\n"
    							+ "Forth argument is the filling mode (0/1).\n"
    							+ "And the last argument is the drawing mode (0/1).";
    		System.err.println(errorMessage);
    		System.exit(1);
    	}
    	
    	//Create an instance of the application and generate the plot of the input Venn diagrams.
    	VennPlot app = new VennPlot(n, args[1], args[2]);
    	if (app.readColors() == 0) {
    		app.createDiagrams(fillingMode, drawingMode);
    	}    	
    }
	
}
