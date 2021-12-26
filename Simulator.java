import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.LinkedList;


/**
 * This is the Simulator class
 */
public class Simulator 
{

	private int packetsDropped = 0;
	private int totalPacketsArrived = 0;
	private int totalServiceTime = 0;
	public static final int MAX_PACKETS = 3;
	private Router dispatcher;
	private Collection<Router> routers;
	private int numIntRouters = 0;
	private double arrivalProb = 0;
	private int maxBufferSize = 0;
	private int minPacketSize = 0;
	private int maxPacketSize = 0;
	private int bandwidth = 0;
	private int duration = 0;
	
	public Simulator()
	{
		
	}
	
	/**
	 * Prompts the user for inputs for the simulator and runs the 
	 * simulation
	 * @param args the command line
	 */
	public static void main(String[] args)
	{
		Simulator s = new Simulator();
		Scanner usrInput = new Scanner(System.in);
		String line;
		boolean running = true;
		while(running)
		{
			System.out.println("Starting simulator...");
			boolean valid = false;
			
			while(!valid)
			{
				System.out.print("Enter the number of Intermediate routers: ");
				line = usrInput.nextLine();
				try 
				{
					int numRouters = Integer.parseInt(line);
					if(numRouters <= 0)
						throw new NumberFormatException();
					s.setNumRouter(numRouters);
					valid = true;
				}
				catch (NumberFormatException e) 
				{
					System.out.println("Invalid Input. Please try again");
				}
			}
			
			valid = false;
			while(!valid)
			{
				System.out.print("Enter the arrival probability of a packet: ");
				line = usrInput.nextLine();
				try 
				{
					double prob = Double.parseDouble(line);
					if(prob <= 0 || prob > 1)
						throw new NumberFormatException();
					s.setArrivalprob(prob);
					valid = true;
				}
				catch (NumberFormatException e) 
				{
					System.out.println("Invalid Input. Please try again");
				}
			}
			
			valid = false;
			while(!valid)
			{
				System.out.print("Enter the maximum maxBufferSize size of a router: ");
				line = usrInput.nextLine();
				try 
				{
					int bufferSize = Integer.parseInt(line);
					if(bufferSize < 0)
						throw new NumberFormatException();
					s.setMaxBuffer(bufferSize);
					valid = true;
				}
				catch (NumberFormatException e) 
				{
					System.out.println("Invalid Input. Please try again");
				}
			}
			
			valid = false;
			int minSize = 0;
			while(!valid)
			{
				System.out.print("Enter the minimum size of a packet: ");
				line = usrInput.nextLine();
				try 
				{
					int packetSize = Integer.parseInt(line);
					if(packetSize <= 0)
						throw new NumberFormatException();
					s.setMinPack(packetSize);
					minSize = packetSize;
					valid = true;
				}
				catch (NumberFormatException e) 
				{
					System.out.println("Invalid Input. Please try again");
				}
			}
			
			valid = false;
			while(!valid)
			{
				System.out.print("Enter the maximum size of a packet: ");
				line = usrInput.nextLine();
				try 
				{
					int packetSize = Integer.parseInt(line);
					if(packetSize <= minSize)
						throw new IllegalArgumentException();
					if(packetSize < 0)
						throw new NumberFormatException();
					s.setMaxPack(packetSize);
					valid = true;
				}
				catch (NumberFormatException e) 
				{
					System.out.println("Invalid Input. Please try again");
				}
				catch (IllegalArgumentException e) 
				{
					System.out.println("Please input a number greater than the minimum size");
				}
			}
			
			valid = false;
			while(!valid)
			{
				System.out.print("Enter the bandwidth size: ");
				line = usrInput.nextLine();
				try 
				{
					int band = Integer.parseInt(line);
					if(band < 0)
						throw new NumberFormatException();
					s.setBandwidth(band);
					valid = true;
				}
				catch (NumberFormatException e) 
				{
					System.out.println("Invalid Input. Please try again");
				}
			}
			
			valid = false;
			while(!valid)
			{
				System.out.print("Enter the simulation duration: ");
				line = usrInput.nextLine();
				try 
				{
					int duration = Integer.parseInt(line);
					if(duration < 0)
						throw new NumberFormatException();
					s.setDuration(duration);
					valid = true;
				}
				catch (NumberFormatException e) 
				{
					System.out.println("Invalid Input. Please try again");
				}
				
			}
			 s.simulate();
			 System.out.print("Do you want to try another simulation? (y/n): ");
			 line = usrInput.nextLine();
			 if(line.toUpperCase().equals("Y"))
				 s.resetSimul();
			 else 
			 {
				 System.out.println("Program terminating successfully...");
				 running = false;
			 }
		}

	}
	
	/**
	 * Generate a random number between minVal and maxVal, 
	 * inclusively
	 * @param minVal the lower bound
	 * @param maxVal the upper bound
	 * @return the randomly generated number, returns -1 if
	 * the maxValue {literal} minVal
	 */
	private int randInt(int minVal, int maxVal)
	{
		if(maxVal < minVal)
			return -1;
		int range = maxVal - minVal + 1;
		return  (int)(Math.random() * range) + minVal;
	}
	
	/**
	 * Simulates the network
	 * @return the average service time
	 */
	public double simulate()
	{
		int simTime = 1;
		LinkedList<Router> routers = new LinkedList<Router>();
		dispatcher = new Router(MAX_PACKETS, 0);
		this.routers = routers;
		for(int i = 0; i < numIntRouters; i++)
		{
			Router r = new Router(maxBufferSize, i + 1);
			routers.add(r);
		}
		
		while(simTime <= duration )
		{
			System.out.println("Time: " + simTime);
			//Handles distributing packets to routers
			
			Packet[] packets = new Packet[MAX_PACKETS];
			for(int i = 0; i < MAX_PACKETS; i++)
				if(Math.random() < arrivalProb)
				{
					int size = randInt(minPacketSize, maxPacketSize);
					Packet packet = new Packet(size, simTime);
					dispatcher.enqueue(packet);
					System.out.println("Packet " + dispatcher.peek().getId() + " arrives "
							+ "at dispatcher with size of " + dispatcher.peek().getPacketSize() + ".");
				}
			if(dispatcher.size() == 0)
				System.out.println("No packets arrived at destination");
			else 
			{
				int size = dispatcher.size();
				for(int i = 0; i < size; i++)
				{
					Packet p = null;
					try 
					{
						p = dispatcher.dequeue();
						int index = Router.sendPacketsTo(routers);
						routers.get(index - 1).enqueue(p);
						System.out.println("Packet " + p.getId() + " sent to Router " + index);
					} 
					catch (FullRouterException e) 
					{
						System.out.println("Network is congested. Packet " + p.getId()
							+ " is dropped." );
						packetsDropped++;
					} catch (EmptyQueueException e) {
					}
				}
			}
			//Handles removing destination time
			for(Router r: routers)
			{
				if(r.size() > 0)
					if(r.peek().timeToDest() != 0)
						r.peek().setTimeDest(r.peek().timeToDest() - 1);
			}
			//Handles sending packets
			Packet[] sendPackets = new Packet[bandwidth];
			for(Router r: routers)
			{
				if(!r.isEmpty())
					if(r.peek().timeToDest() == 0);
						r.raisePrio();
			}
			int counter = 0;
			Comparator<Router> prioCompare = new comparePrio();
			Collections.sort(routers, prioCompare);
			for(int i = 0; i < routers.size(); i++)
			{
				if(counter == bandwidth)
					break;
				if(!routers.peek().isEmpty())
					try 
					{
						if(routers.get(i).peek().timeToDest() == 0)
						{
							try 
							{
								sendPackets[counter] = routers.get(i).dequeue();
								counter++;
							} 
							catch (EmptyQueueException e) 
							{
								System.out.println("Queue is empty.");
							}
						}
						
					} 
					catch (Exception e)
					{
					
					}
					
			}
			Comparator<Router> numCompare = new compareNum();
			Collections.sort(routers, numCompare);
			for(Packet p : sendPackets)
			{
				if(p != null)
				{					int travelTime = simTime - p.getTimeArrive();
					System.out.println("Packet " + p.getId() + " has "
							+ "succesfully reached its destination: +" + travelTime);
					totalServiceTime += travelTime;
					totalPacketsArrived++;
				}
			}
			
			for(int i = 1; i <= routers.size(); i++)
				System.out.println("R" + i + ": " + routers.get(i-1).toString());	
			simTime++;
		}

		System.out.println("Simulating ending...");
		System.out.println("Total service time: " + totalServiceTime);
		System.out.println("Total packets served: " + totalPacketsArrived);
		System.out.println( String.format("%s%-1.2f", "Average service time per packet: ",
				(double)totalServiceTime/totalPacketsArrived));
		System.out.println("Total packets dropped: " + packetsDropped);
		return (double)totalServiceTime/totalPacketsArrived;
		
		
	}
	
	/**
	 * Resets the simulation variables
	 */
	public void resetSimul()
	{
		packetsDropped = 0;
		totalPacketsArrived = 0;
		totalServiceTime = 0;
		dispatcher = null;
		routers = null;
		Packet.resetPacket();
	}
	
	/**
	 * Sets the number of router
	 * @param num the number of router
	 */
	public void setNumRouter (int num)
	{
		numIntRouters = num;
	}
	
	/**
	 * Sets the arrival probability
	 * @param prob the arrival probability
	 */
	public void setArrivalprob(double prob)
	{
		arrivalProb = prob;
	}
	
	/**
	 * Sets the maximum buffer
	 * @param max the max buffer size
	 */
	public void setMaxBuffer(int max)
	{
		maxBufferSize = max;
	}
	
	/**
	 * Sets the minimum packet size
	 * @param min the minimum packet size
	 */
	public void setMinPack(int min)
	{
		minPacketSize = min;
	}
	
	/**
	 * Sets the maximum packet size
	 * @param max the max packet size
	 */
	public void setMaxPack(int max)
	{
		maxPacketSize = max;
	}
	
	/**
	 * Sets the bandwidth.
	 * @param band the bandwith
	 */
	public void setBandwidth(int band)
	{
		bandwidth = band;
	}
	
	/**
	 * Sets the duration
	 * @param dura the duration of the simulation
	 */
	public void setDuration(int dura)
	{
		duration = dura;
	}
	
	/**
	 * Gets packet dropped
	 * @return packets dropped
	 */
	public int getDropped()
	{
		return packetsDropped;
	}
	

}
