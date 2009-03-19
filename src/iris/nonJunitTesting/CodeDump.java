package iris.nonJunitTesting;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class CodeDump {
double a,b,w,a2;

	/**
	 * Only for testing and report purposes, and for visually seeing the Gabor Wavelet
	 * 
	 * @param eyeImage
	 * @param x0  is the centre of the wavelet box
	 * @param y0  is the centre fo the wavelet box	
	 * @param step which of the boxes to draw
	 * @return buffered image with the gaussian, cos term sine term, and some biassed cos terms
	 */
/*	public BufferedImage drawWavelet(BufferedImage eyeImage,int x0,int y0,int step)
	{

		int h=100, width= 360;
		BufferedImage bimage = new BufferedImage(width,h*7,BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<width;i++)
			for(int j=0;j<h*5;j++)
				bimage.setRGB(i, j, 0);
		a = abPar.get_StepN(step);
		b = abPar.get_StepN(step);
		w = wPar.get_StepN(step);
		//w = wPar.lowLim/(2.0*a);
		a2 = a*a;
		b2 = b*b; 
		double k,imPart,rePart,tmpVal, imgVal;
		double sumRe = 0;
		double sumIm = 0;
		double xmin, xmax,ymin,ymax;
		xmin = Math.floor(x0-a);
		xmax = Math.ceil(x0+a);
		ymin = Math.floor(y0-b);
		ymax = Math.ceil(y0+b);
		double[][] real_part = new double[(int)xmax-(int)xmin +2][(int)ymax-(int)ymin+2];
		double total=0;
		double total_neg=0;
		double total_pos=0;
		int total_count=0;
		int colour=0;
		//System.out.println("x "+xmin+" to "+xmax+"  y "+ymin+" to "+ymax);
		for(double x = xmin; x <= xmax; x++)
		{
			for(double y =ymin; y <=ymax; y++)
			{	
				k = Math.exp( -Math.PI * (Math.pow( x - x0, 2) / a2 + Math.pow( y - y0, 2) / b2) );
				System.out.println(w);

				tmpVal =  -w * 2 * Math.PI * ( x-x0 ); 
				System.out.println(tmpVal);

				imPart = Math.sin( tmpVal );
				rePart = Math.cos( tmpVal );
				bimage.setRGB((int)x,(int) y, 0x10101* (int)(255.0*k));
				bimage.setRGB((int)x,(int) y+h, 0x10101* (int)(127.0*imPart+128.0));
				bimage.setRGB((int)x,(int) y+2*h, 0x10101* (int)(127.0*rePart+128.0));
				if (imPart<0) colour = -0x10000 *(int)(255.0*k*imPart);
				else colour = (int)(255.0*k*imPart);
				bimage.setRGB((int)x,(int) y+3*h, colour);
				if (rePart<0) colour = -0x10000 *(int)(255.0*k*rePart);
				else colour = (int)(255.0*k*rePart);
				real_part[(int)(x - xmin)][(int)(y-ymin)] = k*rePart;
				total_count++;
				if (real_part[(int)(x-xmin)][(int)(y-ymin)]>0.0) total_pos+=real_part[(int)(x-xmin)][(int)(y-ymin)];
				else total_neg-=real_part[(int)(x-xmin)][(int)(y-ymin)];
				total +=real_part[(int)(x-xmin)][(int)(y-ymin)];
				bimage.setRGB((int)x,(int) y+4*h, colour);
			}
		}
		System.out.println( total);
		System.out.println( total_count);
		System.out.println( total_pos);
		System.out.println( total_neg);
		for(double x = xmin; x <= xmax; x++)
		{
			for(double y =ymin; y <=ymax; y++)
			{	
				rePart = real_part[(int)(x-xmin)][(int)(y-ymin)] - total/(double) total_count;
				if (rePart<0) colour = -0x10000 *(int)(255.0*rePart);
				else colour = (int)(255.0*rePart);
				bimage.setRGB((int)x,(int) y+5*h, colour);
				rePart = real_part[(int)(x-xmin)][(int)(y-ymin)];
				if (rePart<0) rePart = rePart * total_pos / total_neg;
				if (rePart<0) colour = -0x10000 *(int)(255.0*rePart);
				else colour = (int)(255.0*rePart);
				bimage.setRGB((int)x,(int) y+6*h, colour);

			}
		}
		return bimage;
	}
	public BufferedImage drawBigWavelet()
	{

		int h=600, width= 600;
		BufferedImage bimage = new BufferedImage(width,h,BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<width;i++)
			for(int j=0;j<h;j++)
				bimage.setRGB(i, j, 0);
		a = width/4;
		b = a;
		w = 0.75/a;
		
		a2 = a*a;
		b2 = b*b; 
		double k,imPart,rePart,tmpVal, imgVal;
		double sumRe = 0;
		double sumIm = 0;
		double xmin, xmax,ymin,ymax;
		xmin = 0;
		xmax = 299;
		ymin = 0;
		ymax = 299;
		double[][] real_part = new double[(int)xmax-(int)xmin +2][(int)ymax-(int)ymin+2];
		double total=0;
		double total_neg=0;
		double total_pos=0;
		int total_count=0;
		int colour=0;
		//System.out.println("x "+xmin+" to "+xmax+"  y "+ymin+" to "+ymax);
		for(double x = xmin; x <= xmax; x++)
		{
			for(double y =ymin; y <=ymax; y++)
			{	
				k = Math.exp( -Math.PI * (Math.pow( x - a, 2) / a2 + Math.pow( y - a, 2) / b2) );
				//System.out.println(w);

				tmpVal =  -w * 2 * Math.PI * ( x-a ); 
			//	System.out.println(tmpVal);

				imPart = Math.sin( tmpVal );
				rePart = Math.cos( tmpVal );
				//bimage.setRGB((int)x,(int) y, 0x10101* (int)(255.0*k));
				//bimage.setRGB((int)x,(int) y+h, 0x10101* (int)(127.0*imPart+128.0));
				//bimage.setRGB((int)x,(int) y+2*h, 0x10101* (int)(127.0*rePart+128.0));
				if (imPart<0) colour = -0x10000 *(int)(255.0*k*imPart);
				else colour = (int)(255.0*k*imPart);
				bimage.setRGB((int)x,(int) y, colour);
				if (rePart<0) colour = -0x10000 *(int)(255.0*k*rePart);
				else colour = (int)(255.0*k*rePart);
				real_part[(int)(x - xmin)][(int)(y-ymin)] = k*rePart;
				total_count++;
				if (real_part[(int)(x-xmin)][(int)(y-ymin)]>0.0) total_pos+=real_part[(int)(x-xmin)][(int)(y-ymin)];
				else total_neg-=real_part[(int)(x-xmin)][(int)(y-ymin)];
				total +=real_part[(int)(x-xmin)][(int)(y-ymin)];
				bimage.setRGB((int)x+300,(int) y, colour);
			}
		}
		System.out.println( total);
		System.out.println( total_count);
		System.out.println( total_pos);
		System.out.println( total_neg);
		for(double x = xmin; x <= xmax; x++)
		{
			for(double y =ymin; y <=ymax; y++)
			{	
				rePart = real_part[(int)(x-xmin)][(int)(y-ymin)] - total/(double) total_count;
				if (rePart<0) colour = -0x10000 *(int)(255.0*rePart);
				else colour = (int)(255.0*rePart);
				bimage.setRGB((int)x,(int) y+300, colour);
				rePart = real_part[(int)(x-xmin)][(int)(y-ymin)];
				if (rePart<0) rePart = rePart * total_pos / total_neg;
				if (rePart<0) colour = -0x10000 *(int)(255.0*rePart);
				else colour = (int)(255.0*rePart);
				bimage.setRGB((int)x+300,(int) y+300, colour);
				Graphics g  = bimage.createGraphics();
			 	//String message = "Highest Match "+ _3dp.format(weakest_match) + " Lowest non match "+_3dp.format(lowest_fail);
			 	//g.setFont(new Font("SansSerif",Font.BOLD,18));
			 	g.drawString("a",10,280);
			 	g.drawString("b",310,280);
			 	g.drawString("c",10,580);
			 	g.drawString("d",310,580);
			}
		}
		return bimage;
	}*/
}
