import java.util.Arrays;
import java.io.File;

public class solver {
	static String ALLINTS = "123456789";
	public static void main(String[] args){
		int count = 0;
		File f = new File("sudoku0.txt");
		while(f.exists() && !f.isDirectory()) {
			System.out.println("Grid "+count+"\n");
			Sudoku sudoku = new Sudoku(f.getName());
			solve(sudoku);
			System.out.println("\n");
			count=9;
			f = new File("sudoku"+count+".txt");
		}
	}
		
	public static void solve(Sudoku sudoku){
		String[] rows = sudoku.getRows();
		String[] cols = sudoku.getCols();
		String[] boxs = sudoku.getBoxs();
		int[][] grid = sudoku.getGrid();
		String[][] solutions = new String[9][9];
		boolean same = false;
		
		for(int i=0; i<solutions.length; i++){
			Arrays.fill(solutions[i], "");
			
			for(int j=0; j<solutions[i].length; j++){
				if(grid[i][j]==0){
					String notSolution = rows[i]+cols[j]+boxs[(i/3)*3+(j/3)];

					for(int k=0; k<ALLINTS.length(); k++){

						if(notSolution.indexOf(ALLINTS.charAt(k))<0){
							solutions[i][j] += ALLINTS.charAt(k);
						}
					}
				}
			}
		}
//		for(int a=0; a<solutions.length; a++){
//			for(int b=0; b<solutions[a].length; b++){
//				if(solutions[a][b].equals("")){
//					System.out.print(grid[a][b]+"*\t|");
//				}
//				else{
//					System.out.print(solutions[a][b]+"\t|");
//				}
//			}
//			System.out.println();
//		}
		
		printSudoku(grid);
//		int test = 0;
		while(!same){
//			test++;
			same = true;
			
			for(int i=0; i<rows.length; i++){

				for(int j=0; j<cols.length; j++){
					if(grid[i][j] == 0){
						
						if(solutions[i][j].length()==1){
							same = false;
							int index;
							grid[i][j] = Integer.parseInt(solutions[i][j]);
							rows[i]+=solutions[i][j];
							cols[j]+=solutions[i][j];
							boxs[(i/3)*3+j/3]+=solutions[i][j];
							
							for(int k=0; k<solutions.length; k++){

								if(grid[i][k]==0 && (index = solutions[i][k].indexOf(solutions[i][j]))>=0){
									solutions[i][k] = solutions[i][k].substring(0,index) + solutions[i][k].substring(index+1);
								}
								if(grid[k][j]==0 && (index = solutions[k][j].indexOf(solutions[i][j]))>=0){
									solutions[k][j] = solutions[k][j].substring(0,index) + solutions[k][j].substring(index+1);
								}
								if(grid[(i/3)*3+(k/3)][(j/3)*3+(k%3)]==0 && (index = solutions[(i/3)*3+(k/3)][(j/3)*3+(k%3)].indexOf(solutions[i][j]))>=0){
									solutions[(i/3)*3+(k/3)][(j/3)*3+(k%3)] = solutions[(i/3)*3+(k/3)][(j/3)*3+(k%3)].substring(0,index) + solutions[(i/3)*3+(k/3)][(j/3)*3+(k%3)].substring(index+1);
								}
							}
							solutions[i][j] = "";
						}
						else{
							int countRow = 1;
							int countCol = 1;
							int countBox = 1;

							for(int k=0; k<solutions.length; k++){
								
								if(k!=j && grid[i][k]==0 && solutions[i][k].equals(solutions[i][j])){
									
									if(++countRow == solutions[i][j].length()){
//										System.out.println("Row > i,k="+i+","+k+": " + solutions[i][k] + "\nRow > i,j="+i+","+j+": " + solutions[i][j]);
//										System.out.println("********************** ["+countRow+"]");
//										System.out.println("\n\n");
										for(int x=0; x<solutions[i][j].length(); x++){
											int index;
											for(int y=0; y<solutions.length; y++){

												if(y!=j && !solutions[i][y].equals(solutions[i][j]) && (index = solutions[i][y].indexOf(solutions[i][j].charAt(x)))>=0){
//													System.out.println("y="+y+":"+ solutions[i][y]);
													solutions[i][y] = solutions[i][y].substring(0,index) + solutions[i][y].substring(index+1);
													same = false;
//													System.out.println("y="+y+":"+ solutions[i][y]);
//													System.out.println("**********************");
												}
											}
										}
									}
								}
								if(k!=i && grid[k][j]==0 && solutions[k][j].equals(solutions[i][j])){
									
									if(++countCol == solutions[i][j].length()){
//										System.out.println("Col > k,j="+k+","+j+": " + solutions[k][j] + "\nCol > i,j="+i+","+j+": " + solutions[i][j]);
//										System.out.println("********************** ["+countCol+"]");
//										System.out.println("\n\n");
										for(int x=0; x<solutions[i][j].length(); x++){
											int index;
											for(int y=0; y<solutions.length; y++){

												if(y!=i && !solutions[y][j].equals(solutions[i][j]) && (index = solutions[y][j].indexOf(solutions[i][j].charAt(x)))>=0){
//													System.out.println("y="+y+":"+ solutions[y][j]);
													solutions[y][j] = solutions[y][j].substring(0,index) + solutions[y][j].substring(index+1);
													same = false;
//													System.out.println("y="+y+":"+ solutions[y][j]);
//													System.out.println("**********************");
												}
											}
										}
//										System.out.println("\n\n");
									}
								}
								if((i!=(i/3)*3+(k/3) || j!=(j/3)*3+(k%3)) && grid[(i/3)*3+(k/3)][(j/3)*3+(k%3)]==0 && solutions[(i/3)*3+(k/3)][(j/3)*3+(k%3)].equals(solutions[i][j])){
									
									
									if(++countBox == solutions[i][j].length()){
//										System.out.println("Box > x,y="+(i/3)*3+(k/3)+","+(j/3)*3+(k%3)+": " + solutions[i][j] + "\nBox > i,j="+i+","+j+": " + solutions[i][j]);
//										System.out.println("********************** ["+countBox+"]");
//										System.out.println("\n\n");
										for(int x=0; x<solutions[i][j].length(); x++){
											int index;
											for(int y=0; y<solutions.length; y++){

												if((i!=(i/3)*3+(y/3) || j!=(j/3)*3+(y%3)) && !solutions[(i/3)*3+(y/3)][(j/3)*3+(y%3)].equals(solutions[i][j]) && (index = solutions[(i/3)*3+(y/3)][(j/3)*3+(y%3)].indexOf(solutions[i][j].charAt(x)))>=0){
//													System.out.println("i,j="+i+","+j+":"+ solutions[(i/3)*3+(y/3)][(j/3)*3+(y%3)]);
													solutions[(i/3)*3+(y/3)][(j/3)*3+(y%3)] = solutions[(i/3)*3+(y/3)][(j/3)*3+(y%3)].substring(0,index) + solutions[(i/3)*3+(y/3)][(j/3)*3+(y%3)].substring(index+1);
													same = false;
//													System.out.println("i,j="+i+","+j+":"+ solutions[(i/3)*3+(y/3)][(j/3)*3+(y%3)]);
//													System.out.println("**********************");
												}
											}
										}
									}
								}
								
								for(int x=0; x<solutions.length; x++){
									int ksInRow = 0;
									int ksInCol = 0;
									int ksInBox = 0;
									int rowX = 0;
									int rowY = 0;
									int colX = 0;
									int colY = 0;
									int boxX = -1;
									int boxY = -1;
									boolean kAllOneRow = false;
									boolean kAllOneCol = false;
									
									for(int y=0; y<solutions[x].length; y++){
										if(solutions[x][y].indexOf(ALLINTS.charAt(k))>=0){
											ksInRow++;
											rowX = x;
											rowY = y;
										}
										if(solutions[y][x].indexOf(ALLINTS.charAt(k))>=0){
											ksInCol++;
											colX = y;
											colY = x;
										}
										if(solutions[(x/3)*3+(y/3)][(x%3)*3+(y%3)].indexOf(ALLINTS.charAt(k))>=0){
											kAllOneRow = kAllOneRow && boxX == (x/3)*3+(y/3) ? true : false;
											kAllOneRow = kAllOneRow || boxX+boxY < 0;
											kAllOneCol = kAllOneCol && boxY == (x%3)*3+(y%3) ? true : false;
											kAllOneCol = kAllOneCol || boxX+boxY < 0;
											ksInBox++;
											boxX = (x/3)*3+(y/3);
											boxY = (x%3)*3+(y%3);
										}
									}
									if(ksInRow==1){
										solutions[rowX][rowY] = ALLINTS.substring(k,k+1);
										same = false;
									}
									if(ksInCol==1){
										solutions[colX][colY] = ALLINTS.substring(k,k+1);
										same = false;
									}
									if(boxX+boxY >= 0 && ksInBox==1){
										solutions[boxX][boxY] = ALLINTS.substring(k,k+1);
										same = false;
									}
									if(kAllOneRow){
										for(int col=0; col<solutions[boxX].length; col++){
											int index;
											if((boxX/3)*3+(boxY/3) != (boxX/3)*3+(col/3) && (index = solutions[boxX][col].indexOf(ALLINTS.charAt(k))) >= 0){
												solutions[boxX][col] = solutions[boxX][col].substring(0, index) + solutions[boxX][col].substring(index+1);
												same = false;
											}
										}
									}
									if(kAllOneCol){
										for(int row=0; row<solutions[boxY].length; row++){
											int index;
											if((boxX/3)*3+(boxY/3) != (row/3)*3+(boxY/3) && (index = solutions[row][boxY].indexOf(ALLINTS.charAt(k))) >= 0){
												solutions[row][boxY] = solutions[row][boxY].substring(0, index) + solutions[row][boxY].substring(index+1);
												same = false;
											}
										}
									}
								}
							}
						}
					}
				}
			}
//			System.out.println("\n-----------\n");
//			for(int a=0; a<solutions.length; a++){
//				for(int b=0; b<solutions[a].length; b++){
//					if(solutions[a][b].equals("")){
//						System.out.print(grid[a][b]+"*\t|");
//					}
//					else{
//						System.out.print(solutions[a][b]+"\t|");
//					}
//				}
//				System.out.println();
//			}
//			System.out.println("\n-----------\n");
//			printSudoku(grid);
		}
		System.out.println("\n-----------\n");
		printSudoku(grid);
		System.out.println("\n-----------\n");
		for(int a=0; a<solutions.length; a++){
			for(int b=0; b<solutions[a].length; b++){
				if(solutions[a][b].equals("")){
					System.out.print(grid[a][b]+"*\t|");
				}
				else{
					System.out.print(solutions[a][b]+"\t|");
				}
			}
			System.out.println();
		}
	}
	public static void printSudoku(int[][] sudoku){
		for(int i=0; i<sudoku.length; i++){
			if(i!=0 && i%3==0)
				System.out.println();
			for(int j=0; j<sudoku[i].length; j++){
				if(j!=0 && j%3==0)
					System.out.print(" ");
				if(sudoku[i][j]==0){
					System.out.print("_");
				}
				else{
					System.out.print(sudoku[i][j]);
				}
			}
			System.out.println();
		}
	}
}
