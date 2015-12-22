import java.io.*;
import java.util.Arrays;

public class Sudoku {
	static String ALLINTS = "123456789";
	private int[][] grid = new int[9][9];
	private String[] rows = new String[9];
	private String[] cols = new String[9];
	private String[] boxs = new String[9];
	
	Sudoku(String filename){
		String line = null;
		int[] row = new int[9];
		Arrays.fill(rows, "");
		Arrays.fill(cols, "");
		Arrays.fill(boxs, "");
		int i = 0;
		
		try{
			FileReader file = new FileReader(filename);
			BufferedReader buffer = new BufferedReader(file);
			
			while((line = buffer.readLine()) != null){
				rows[i] = line;
				for(int j=0; j<line.length(); j++){
					row[j] = Character.getNumericValue(line.charAt(j));
					cols[j] += line.charAt(j);
					boxs[(i/3)*3+(j/3)] += line.charAt(j);
				}
				System.arraycopy(row, 0, grid[i++], 0, 9);
				
			}
			for(i=0; i<rows.length; i++){
				rows[i] = rows[i].replaceAll("0", "");
				cols[i] = cols[i].replaceAll("0", "");
				boxs[i] = boxs[i].replaceAll("0", "");
			}
			buffer.close();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] getRows() {
		return rows;
	}
	public void setRows(int i, String row){
		this.rows[i] = row;
	}
	public String[] getCols() {
		return cols;
	}
	public void setCols(int i, String col) {
		this.cols[i] = col;
	}
	public String[] getBoxs() {
		return boxs;
	}
	public void setBoxs(int i, String box) {
		this.boxs[i] = box;
	}
	public int[][] getGrid() {
		return grid;
	}
}
