package com.viatorum.simulation.vehicles;

public class Sedan extends Vehicle
{

    @Override
    public int getCapacity() {
        return 4;
    }

    @Override
    public Vehicle_Type getVehicleType() {
        return Vehicle_Type.SEDAN;
    }

}
