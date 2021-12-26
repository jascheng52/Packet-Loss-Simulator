/**
 * This is the Packet class
 */
public class Packet
{
	public static int packetCount = 0;
	private int id;
	private int packetSize;
	private int timeArrive;
	private int timeToDest;
	
	/**
	 * Creates an empty packet
	 */
	public Packet()
	{
		packetCount ++;
		id = packetCount;
	}
	/**
	 * Constructs a packet
	 * @param size the size of the packet
	 * @param arrivalTime the time the packet arrived
	 */
	public Packet(int size, int arrivalTime)
	{
		packetCount++;
		id = packetCount;
		packetSize = size;
		timeToDest = packetSize/100;
		timeArrive = arrivalTime;
	}
	
	/**
	 * Gets the id
	 * @return the packets Id
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Gets the packet size
	 * @return the size of the packet
	 */
	public int getPacketSize()
	{
		return packetSize;
	}
	
	/**
	 * Gets the arrival time
	 * @return the arrival time of the packet
	 */
	public int getTimeArrive()
	{
		return timeArrive;
	}
	
	/**
	 * Gets the destination time
	 * @return the remaining time to destination
	 */
	public int timeToDest()
	{
		return timeToDest; 
	}
	
	/**
	 * Gets the packet count
	 * @return the total number of packets
	 */
	public int getPacketCount()
	{
		return packetCount;
	}
	
	/**
	 * Sets the id of the packet to a new id
	 * @param newId the new id
	 */
	public void setId(int newId)
	{
		id = newId;
	}
	
	/**
	 * Sets the size of the packet to a new size,
	 * also adjust the timeToDest
	 * @param newSize the new packet size
	 */
	public void setpacketSize(int newSize)
	{
		packetSize = newSize;
		setTimeDest(packetSize/100);
	}
	
	/**
	 * Sets timeToDest to newTime
	 * @param newTime the new destination time
	 */
	public void setTimeDest(int newTime)
	{
		timeToDest = newTime;
	}
	
	/**
	 * Sets the new packetCount
	 * @param newCount the new packet count
	 */
	public void setPacketCount(int newCount)
	{
		packetCount = newCount;
	}
	
	/**
	 * Gets the string representation of a packet
	 * @return the string representation of a packet
	 */
	public String toString()
	{
		String str = "[" + id + ", " + timeArrive + ", "
				+ timeToDest + "]";
		return str;
	}
	
	/**
	 * Resets packet counts
	 */
	public static void resetPacket()
	{
		packetCount = 0;
	}
	

}
