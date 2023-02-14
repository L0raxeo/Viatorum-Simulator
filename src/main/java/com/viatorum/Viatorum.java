package com.viatorum;

import com.viatorum.simulation.Simulation;

import java.util.Scanner;

public class Viatorum
{

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

            recordSimulation(simulation);
        }
    }

    private void recordSimulation(Simulation simulation)
    {
        System.out.println("Recording simulation...");

        simulation.record();
        System.out.println("Queued People: " + simulation.queuedPeople);
        System.out.println("People being processed: " + simulation.peopleProcessing);
        System.out.println("Total people Dequeued: " + simulation.peopleDequeued);
        System.out.println("Total people queued " + simulation.totalPeopleQueued);
        System.out.println("Total people processed: " + simulation.totalPeopleProcessed);
        System.out.println("Total vehicles processed: " + simulation.totalVehiclesProcessed);

        System.out.println();
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
