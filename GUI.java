import java.awt.Color;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.Scanner;



public class GUI extends JFrame {
	/**
	 * maze[row][col]
	 * 
	 * values	0 = no visited
	 * 			1 = wall (needs to be translated from # in file -> 1)
	 * 			2 = visited
	 * 			3 = Exit
	 * 
	 * 			Custom trial maze below
	 */
	
	public static int [][] maze ;
	
	private final List<Integer> path = new ArrayList<Integer>();
    private int pathIndex;
    
    public static int start1=1; public static int start2=1;
    
    public GUI() {
        setTitle("Maze (solved by DFS)");
        setSize(640, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        DFS.searchPath(maze, start1, start2, path);
        pathIndex = path.size() -2;
    }
    
    @Override
    public void paint(Graphics g) {
    	super.paint(g);
    	g.translate(50, 50);
    	
    	//draw maze
    	for (int row = 0; row < maze.length; row++) {
    		for (int col = 0; col < maze[0].length; col++) {
    		Color color;
    		switch (maze[row][col]) {
    		case 1 : color = Color.BLACK; break;
    		case 9 : color = Color.RED; break;
    		default : color = Color.WHITE;
    		}
    		
    		g.setColor(color);
    		g.fillRect(30 * col, 30 * row, 30, 30);
    		g.setColor(Color.BLACK);
    		g.drawRect(30 * col, 30 * row, 30, 30);
    	}
    }
    	
    	for (int p = 0; p < path.size(); p += 2) {
            int pathX = path.get(p);
            int pathY = path.get(p + 1);
            g.setColor(Color.GREEN);
            g.fillRect(pathX * 30, pathY * 30, 30, 30);
            }
    }
      
    public static void main (String [] args) throws IOException {
    	Scanner s = new Scanner(System.in);
    	System.out.println("Please enter the name of the file to open(large or medium)");
    	String name=s.nextLine();
    	
    	String line;
    	
    	File tem= new File(name+".txt");
    	while(tem.exists()==false) {
    		System.out.println("File does not exist, try again!");
    		name=s.nextLine();
    		tem= new File(name+".txt");
    		}
    	boolean exists=tem.exists();
    
    	FileReader nam=new FileReader(name+".txt");
    	Scanner sc= new Scanner( new BufferedReader(nam));
    	
    	line=sc.nextLine();
    	String size=line;
    	int row=0;
    	int column=0;
    	String temp="";
    	
    	for(int i=0;i<size.length();i++) {
    		if(size.charAt(i)==' ') {
    			row=Integer.parseInt(temp);
    			temp="";
    		}
    		else if(i==size.length()-1){
    			temp+=""+size.charAt(i);
    			column=Integer.parseInt(temp);
    		}
    		else {
    			temp+=""+size.charAt(i);
    		
    		}
    	}
  
    	
    	int [][]mazee=new int[row][column];
    	
    	while(sc.hasNextLine()) {
    		for(int i=0;i<mazee.length;i++) {
    			String looo= sc.nextLine();
    			for(int j=0;j<looo.length();j++) {
    				if( j==column-1 && looo.charAt(j)==' ') {
    					mazee[i][j]=3;
    				}
    				else if( j==0 && looo.charAt(j)==' ') {
    					start1=i;
    					start2=j+1;
    				}
    				else if((j!=0 || j!=column-1) && looo.charAt(j)=='#') {
    					mazee[i][j]=1;
    				}
    				else if(looo.charAt(j)==' ') {
    					mazee[i][j]=0;
    				}
    			}
    		}
    	}
    	
    	int[][]trial=new int[row][column+2];
    	
    	for(int i=0;i<row;i++) {
    		for(int j=0;j<column+2;j++) {
    			if(j==0 || j==column+1) {
    				trial[i][j]=1;
    			}
    			else {
    				trial[i][j]=mazee[i][j-1];
    			}
    		}
    	}
    	
    	
    	maze=trial;
    	
    	

    		SwingUtilities.invokeLater(new Runnable(){
    	    @Override
    	    public void run() {
    	    	GUI gui = new GUI();
    	    	gui.setVisible(true);
    	    }
    	   });
    	   
    	}
    	
    }

