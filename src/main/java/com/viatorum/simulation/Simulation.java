package com.viatorum.simulation;

import com.viatorum.simulation.vehicles.*;

import java.util.*;

public class Simulation extends DataHolder
{

    private final List<Queue<Vehicle>> lanes = new ArrayList<>();

    public boolean running = false;

    /**
     * @param amtInitQueuedPeople is the initial amount of people queued.
     * @param amtQueuedPeopleAddends is the number of people queued every iteration.
     * @param amtLanes is the number of non-bus lanes (traffic lanes).
     * @param evComposite is the percentage of vehicles that are electric [0.00-1.00]
     * @param  sedanComposite is the percentage of cars that are sedans [0.00-1.00]
     *                        as opposed to SUVs.
     * @param amtBusLanes is the number of bus lanes in the simulation.
     *                    all bus lanes are the first few indexes of the
     *                    lanes list.
     * @param amtPublicBuses per lane.
     */
    public Simulation(int laneLength, int amtInitQueuedPeople, int amtQueuedPeopleAddends, int amtLanes, float evComposite, float sedanComposite,int amtBusLanes, int amtPublicBuses)
    {
        // fills list of lanes with queues
        for (int i = 0; i < amtLanes; i++)
            lanes.add(new LinkedList<>());

        this.laneLength = laneLength;
        this.amtBusLanes = amtBusLanes;
        this.queuedPeople = amtInitQueuedPeople;
        this.totalPeopleQueued = queuedPeople;
        this.amtQueuedPeopleAddends = amtQueuedPeopleAddends;
        this.evComposite = evComposite;
        this.sedanComposite = sedanComposite;
        this.amtPublicBuses = amtPublicBuses;
    }

    /**
     * Update performs the following steps:
     * 1. removes the top vehicles from the queue
     * 2. queues new vehicles
     * <p>
     * In order to simulate traffic randomness, vehicles are introduced
     * to the queue at random intervals.
     */
    private void update()
    {
        for (Queue<Vehicle> lane : lanes)
        {
            if (lane.size() >= laneLength)
            {
                Vehicle vehicleProcessed = lane.poll();
                assert vehicleProcessed != null;
                if (vehicleProcessed.getVehicleType() != Vehicle_Type.EMPTY)
                {
                    totalVehiclesProcessed++;
                    totalPeopleProcessed += vehicleProcessed.getPeople();
                }
            }

            if (lane.size() < laneLength)
            {
                if ((int) (Math.random() * 6) + 1 > 2 || queuedPeople == 0) // by chance make this a blank space in the queue
                {
                    lane.offer(new Empty());
                    continue;
                }

                Vehicle_Type vehicleType;

                if (lanes.indexOf(lane) < amtBusLanes)
                {
                    boolean hasBus = false;
                    for (Vehicle v : lane)
                        if (v.getVehicleType() == Vehicle_Type.BUS)
                            hasBus = true;

                    // checks whether the number of buses per lane has been exceeded
                    if (!hasBus)
                        vehicleType = Vehicle_Type.BUS;
                    else continue;
                }
                else
                {
                    if (Math.random() < sedanComposite) // if a double [0.00, 1.00) is within the sedan frequency, a sedan is created
                        vehicleType = Vehicle_Type.SEDAN;
                    else vehicleType = Vehicle_Type.SUV;
                }

                lane.offer(generateVehicle(vehicleType));
            }
        }

        updateQueue();
    }

    /**
     * 1. creates a vehicle depending on parameter type
     * 2. subtracts the people filled from the queued people
     * 3. returns the new vehicle
     *
     * @return a new vehicle
     */
    private Vehicle generateVehicle(Vehicle_Type type)
    {
        switch (type)
        {
            case BUS -> {
                Bus bus = new Bus();
                int amtPeople = (int) ((Math.random() * (bus.getCapacity() - 20 + 1)) + 20);
                if (dequeue(amtPeople)) bus.fill(amtPeople); // if there aren't enough people just use the rest
                else
                {
                    amtPeople = queuedPeople;
                    dequeue(amtPeople);
                    bus.fill(amtPeople);
                }
                return bus;
            }
            case SEDAN -> {
                Sedan sedan = new Sedan();
                int amtPeople = (int) ((Math.random() * sedan.getCapacity()) + 1);
                if (dequeue(amtPeople)) sedan.fill(amtPeople);
                else
                {
                    amtPeople = queuedPeople;
                    dequeue(amtPeople);
                    sedan.fill(amtPeople);
                }
                return sedan;
            }
            case SUV -> {
                Suv suv = new Suv();
                int amtPeople = (int) ((Math.random() * suv.getCapacity()) + 1);
                if (dequeue(amtPeople)) suv.fill(amtPeople);
                else
                {
                    amtPeople = queuedPeople;
                    dequeue(amtPeople);
                    suv.fill(amtPeople);
                }
                return suv;
            }
            case EMPTY -> {
                return new Empty();
            }
        }

        return null;
    }

    private boolean dequeue(int people)
    {
        if (people > queuedPeople)
            return false;

        queuedPeople -= people;
        peopleDequeued += people;

        return true;
    }

    private void updateQueue()
    {
        queuedPeople += amtQueuedPeopleAddends;
        totalPeopleQueued += amtQueuedPeopleAddends;
    }

    public void record()
    {
        // records people currently on the road
        for (Queue<Vehicle> lane : lanes)
            for (Vehicle v : lane)
                peopleProcessing += v.getPeople();
    }

    public void start(int amtIterations)
    {
        running = true;

        for (int i = 0; i < amtIterations; i++)
            update();

        running = false;
    }

}
