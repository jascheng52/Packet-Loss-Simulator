import java.util.Comparator;

/**
 * The Comparator for a list of Routers
 */
public class compareNum implements Comparator<Router>
{
	/**
	 * Compares the routers by its name and sorts ascending
	 * @param o1 the first router
	 * @param o2 the second router
	 */
	public int compare(Router o1, Router o2) 
	{
		if(o1.getNumber() > o2.getNumber())
			return 1;
		if(o1.getNumber() == o2.getNumber())
			return 0;
		return -1;
	}

}
