package com.viatorum.simulation.vehicles;

public class Suv extends Vehicle
{

    @Override
    public int getCapacity() {
        return 7;
    }

    @Override
    public Vehicle_Type getVehicleType() {
        return Vehicle_Type.SUV;
    }

}
