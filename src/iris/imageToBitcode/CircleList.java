package iris.imageToBitcode;
import java.util.ArrayList;

public class CircleList {
	public CircleList(int max_elements)
	{
		max_size = max_elements;
		list = new ArrayList<CircleType>(max_size);
		current_size=0;
		lowest_max = 0.0;
		CircleType c = new CircleType();
		for (int i=0;i<max_size;i++)
			list.add(c);
	}
	public double add_circle(CircleType c)
	{
		if (lowest_max<c.gradient)
		{
			if (current_size == 0) 
			{
				current_size++; 
				list.set(0,c);
				if (current_size<max_size) lowest_max= 0.0;
				else lowest_max=list.get(current_size-1).gradient;
				return lowest_max;
			}
			else if (current_size< max_size) 
			{
				int i;
				for (i = current_size; i>0 && c.gradient>list.get(i-1).gradient;i-- )
					list.set(i,list.get(i-1));
				list.set(i,c);
				current_size++; 
				if (current_size<max_size) lowest_max= 0.0;
				else lowest_max=list.get(current_size-1).gradient;
				return lowest_max;
			}
			else 
			{
				int i;
				for (i = current_size-1; i>0 && c.gradient>list.get(i-1).gradient;i-- )
					list.set(i,list.get(i-1));
				list.set(i,c);
				lowest_max=list.get(current_size-1).gradient;
				return lowest_max;
			}
		}
		return lowest_max;
	}
	public CircleType get_circle(int pos)
	{
		if (pos>= current_size)	return null;
		else return list.get(pos);
	}
	public int get_size(){return current_size;}
	private void copy_circle(int pos_from,int pos_to){};
	private int max_size;
	private int current_size;
	private ArrayList<CircleType> list;
	private double lowest_max;
}
