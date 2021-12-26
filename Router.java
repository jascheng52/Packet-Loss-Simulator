import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This is the router class
 */
public class Router extends LinkedList<Packet> implements Queue<Packet>
{
	private int size;
	private int buffer;
	private int prio;
	private int routerNum;
	/** 
	 * Creates an empty Router Queue
	 */
	public Router()
	{

	}
	
	/**
	 * Creates a router with a buffer and its number
	 * @param theBuffer the maximum buffer
	 * @param theNumber the number of the router
	 */
	public Router(int theBuffer, int theNumber)
	{
		size = 0;
		buffer = theBuffer;
		prio = 0;
		routerNum = theNumber;
	}
	
	/**
	 * Adds a packet to the queue if not full,
	 * @param p the packet to add
	 */
	public void enqueue(Packet p)
	{
		if(size < buffer)
		{
			add(p);
			size++;
		}
	}
	
	/**
	 * Removes the first Packet in the buffer
	 * @return the first Packet in the buffer
	 * @throws EmptyQueueException when the router is empty
	 */
	public Packet dequeue() throws EmptyQueueException
	{
		if(isEmpty())
			throw new EmptyQueueException(); 
		size --;
		prio = 0;
		return remove();
	}
	
	/**
	 * Gets the first packet in the router
	 * @return the first Packet in the buffer
	 */
	public Packet peek()
	{
		return peekFirst();
	}
	
	/**
	 * Gets the size of the queue
	 * @return the size of the queue
	 */
	public int size()
	{
		return size;
	}
	
	/** 
	 * Checks if the router is empty
	 * @return true if the buffer is empty
	 * false otherwise
	 */
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	/**
	 * Gets the string representation of a router
	 * @return the String representation of the router 
	 */
	public String toString()
	{
		String str = "{";
		for(int i = 0; i < size; i++)
		{
			if(i == size - 1)
				str += this.get(i).toString();
			else 
				str +=  this.get(i).toString() + ", ";
		}
		str +="}";
		return str;
	}
	
	/**
	 * Gets the buffer size of the router
	 * @return the maximum buffer for the router
	 */
	public int getBuffer()
	{
		return buffer;
	}
	
	/**
	 * Gets the priority of the router
	 * @return the priority of the router
	 */
	public int getPrio()
	{
		return prio;
	}
	
	/**
	 * Raises the the priority
	 */
	public void raisePrio()
	{
		prio++;
	}
	
	/**
	 * Gets the router's number
	 * @return the number of the router
	 */
	public int getNumber()
	{
		return routerNum;
	}
	/**
	 * Find the router with the most free buffer space (contains least Packets), 
	 * and return the index of the router. If there are multiple routers, any 
	 * corresponding indices will be acceptable. If all router buffers are full, 
	 * throw an exception.
	 * @param routers the Collection of Routers
	 * @return the index of the router with the most space
	 * @throws FullRouterException when all the routers are full
	 */
	public static int sendPacketsTo(Collection<Router> routers) throws FullRouterException
	{
		int index = 0;
		int routerIndex = 0;
		int bufferSpace = -1;
		Iterator<Router> theRouters = routers.iterator();
		while(theRouters.hasNext())
		{
			Router r = theRouters.next();
			index++;
			int space = r.getBuffer() - r.size();
			if(space != 0)
				if(space > bufferSpace)
				{
					bufferSpace = space;
					routerIndex = index;
				}
		}
		if(routerIndex == 0)
			throw new FullRouterException();
		return routerIndex;
	}
	
}
