import java.util.Comparator;

/**
 * The Comparator for a list of Routers
 */
public class comparePrio implements Comparator<Router>
{

	/**
	 * Compares the router's priority and sorts descending
	 * @param o1 the first router
	 * @param o2 the second router
	 */
	public int compare(Router o1, Router o2) 
	{
		if(o2.getPrio() > o1.getPrio())
			return 1;
		if(o2.getPrio() == o1.getPrio())
			return 0;
		return -1;
	}
	
}
