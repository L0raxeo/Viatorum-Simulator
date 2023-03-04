package com.viatorum;

import com.viatorum.simulation.DataHolder;
import com.viatorum.simulation.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Viatorum
{

    private final List<DataHolder> simulationData = new ArrayList<>();

    private Scanner input;
    private boolean running = false;

    private void createSimulation()
    {
        System.out.println("lane length (this determines how long each vehicle is on the road before it exits)");
        int laneLength = input.nextInt();

        System.out.println("# of initially queued people");
        int amtInitQueuedPeople = input.nextInt();

        System.out.println("# of queued people per iteration");
        int amtQueuedPeople = input.nextInt();

        System.out.println("# of lanes - [1, 50}");
        int amtLanes = input.nextInt();

        System.out.println("% EV - [0.00, 1.00]");
        float evComposite = input.nextFloat();

        System.out.println("% of sedans (vs. SUV) - [0.00-1.00]");
        float sedanComposite = input.nextFloat();

        System.out.println("# of public bus lanes");
        int amtPublicBusLanes = input.nextInt();

        System.out.println("# of public buses per lane - [0, 5]");
        int amtPublicBuses = input.nextInt();

        System.out.println("# of iterations");
        int amtIterations = input.nextInt();

        System.out.println("# of simulations");
        int amtSimulations = input.nextInt();

        System.out.println("|---------------------------------|");
        System.out.println("|---------------------------------|");

        for (int i = 0; i < amtSimulations; i++)
        {
            System.out.println("Creating simulation...");

            Simulation simulation = new Simulation(laneLength,
                    amtInitQueuedPeople,
                    amtQueuedPeople,
                    amtLanes,
                    evComposite,
                    sedanComposite,
                    amtPublicBusLanes,
                    amtPublicBuses);

            System.out.println("Running simulation...");

            simulation.start(amtIterations);

            while (simulation.running) {}

            recordData(simulation);
        }

        System.out.println("|---------------------------------|");
        System.out.println("|---------------------------------|");
        System.out.println();
        System.out.println("Averages of the past " + amtSimulations + " simulations");
        System.out.println("All decimals are rounded down.");
        recordData(getDataAverages());

        simulationData.clear();
    }

    private DataHolder getDataAverages()
    {
        DataHolder dataAverages = new DataHolder();

        for (DataHolder dh : simulationData)
        {
            dataAverages.queuedPeople += dh.queuedPeople;
            dataAverages.peopleProcessing += dh.peopleProcessing;
            dataAverages.peopleDequeued += dh.peopleDequeued;
            dataAverages.totalPeopleQueued += dh.totalPeopleQueued;
            dataAverages.totalPeopleProcessed += dh.totalPeopleProcessed;
            dataAverages.totalVehiclesProcessed += dh.totalVehiclesProcessed;
        }

        dataAverages.queuedPeople /= simulationData.size();
        dataAverages.peopleProcessing /= simulationData.size();
        dataAverages.peopleDequeued /= simulationData.size();
        dataAverages.totalPeopleQueued /= simulationData.size();
        dataAverages.totalPeopleProcessed /= simulationData.size();
        dataAverages.totalVehiclesProcessed /= simulationData.size();

        return dataAverages;
    }

    private void recordData(DataHolder data)
    {
        System.out.println("Recording data...");

        if (data instanceof Simulation)
            ((Simulation) data).record();

        System.out.println("Queued People: " + data.queuedPeople);
        System.out.println("People being processed: " + data.peopleProcessing);
        System.out.println("Total people Dequeued: " + data.peopleDequeued);
        System.out.println("Total people queued " + data.totalPeopleQueued);
        System.out.println("Total people processed: " + data.totalPeopleProcessed);
        System.out.println("Total vehicles processed: " + data.totalVehiclesProcessed);

        System.out.println();

        if (data instanceof Simulation)
            simulationData.add(data);
    }

    public void start()
    {
        input = new Scanner(System.in);
        running = true;

        while (running)
        {
            System.out.println("[0] exit");
            System.out.println("[1] create simulation");

            int selection = input.nextInt();

            if (selection == 0)
                stop();
            else if (selection == 1)
                createSimulation();
        }
    }

    public void stop()
    {
        running = false;
        System.exit(0);
    }

}
