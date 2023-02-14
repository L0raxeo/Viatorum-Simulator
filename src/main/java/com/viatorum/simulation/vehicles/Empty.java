package com.viatorum.simulation.vehicles;

public class Empty extends Vehicle{
    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public Vehicle_Type getVehicleType() {
        return Vehicle_Type.EMPTY;
    }

    @Override
    public int getPeople() {
        return 0;
    }
}
