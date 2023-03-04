package com.viatorum.simulation;

public class DataHolder
{

    // Independent Variables

    protected int laneLength;
    protected int amtBusLanes;
    protected int amtQueuedPeopleAddends;
    protected float evComposite;
    protected float sedanComposite;
    protected float amtPublicBuses;

    /**
     * Each vehicle will seat a random number of people [1, maxSeating]
     * randomness will simulate family owned cars and individually owned
     * cars (e.g. family of 4, person driving to work, 2 people driving
     * to a restaurant... etc)
     */

    // Dependent Variables
    public int queuedPeople;
    public int peopleDequeued;
    public int peopleProcessing;
    public int totalPeopleQueued;
    public int totalPeopleProcessed;
    public int totalVehiclesProcessed;

}
