package com.viatorum.simulation.vehicles;

public class Bus extends Vehicle
{

    @Override
    public int getCapacity() {
        return 105;
    }

    @Override
    public Vehicle_Type getVehicleType() {
        return Vehicle_Type.BUS;
    }
}
