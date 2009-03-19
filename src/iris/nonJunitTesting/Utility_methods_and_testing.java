package iris.nonJunitTesting;

import java.io.File;

import unittest.ImageToBitcode.ImageSaverLoader;

public class Utility_methods_and_testing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//DO NOT USE THIS CODE WITHOUT ASKING EDMUND NOON
		
		
		String load_path = "images/new";
		String save_path = "images/automatic/";
		String compare_path = "/vol/bitbucket/en108/pupilCentres/";
		String file;
		int rmin=200, xmin=400, ymin=400;
		int rmax = 0, xmax=0, ymax = 0;
		int x,y,r;
		File imagefolder = new File(load_path);
		File comparefolder = new File(compare_path);
		
		File[] listOfFiles = imagefolder.listFiles();
		File[] compareFiles = comparefolder.listFiles();
		for (int i = 0; i < compareFiles.length; i++) 
		//for (int i = 0; i < 3; i++) 
		{
			System.out.println();
			System.out.print(i);
			if (compareFiles[i].isFile())	{
				System.out.print("  "+compareFiles[i].getName()+"  ");
				System.out.print("  "+compareFiles[i].getName().length()+"  ");
				for (int j=0; j<listOfFiles.length;j++){
					
					System.out.print(" "+listOfFiles[j].getName());
					System.out.print(" "+listOfFiles[j].getName().length());
				
					if ((compareFiles[i].getName().hashCode())==(listOfFiles[j].getName().hashCode())){
						System.out.println(compareFiles[i].getName());
						boolean success = listOfFiles[j].renameTo(new File(save_path,listOfFiles[j].getName()));
						System.out.println(success);
						break;
					}
				}
			}
		}

	}
}
