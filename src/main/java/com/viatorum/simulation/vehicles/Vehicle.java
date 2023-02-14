package com.viatorum.simulation.vehicles;

public abstract class Vehicle
{
    protected int people;

    public abstract int getCapacity();

    public abstract Vehicle_Type getVehicleType();

    public void fill(int people)
    {
        this.people = people;
    }

    public int getPeople()
    {
        return this.people;
    }

}

