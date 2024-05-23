package com.assignment;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class VehicleRentalSystemTest {
    
    private VehicleRentalSystem system;
    private List<Vehicle> vehicles;
    private List<Reservation> reservations;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Before
    public void setUp() {
        system = new VehicleRentalSystem();
        vehicles = new ArrayList<>();
        reservations = new ArrayList<>();
        initializeVehicles();
    }

    private void initializeVehicles() {
        vehicles.add(new Car("1", "Toyota Corolla", true));
        vehicles.add(new Car("2", "Honda Civic", true));
        vehicles.add(new Truck("3", "Ford F-150", false));
        vehicles.add(new Truck("4", "Chevrolet Silverado", true));
    }

    @Test
    public void testSearchAvailableVehicles() {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable) {
                availableVehicles.add(vehicle);
            }
        }
        assertEquals(3, availableVehicles.size());
    }

    @Test
    public void testMakeReservation() {
        Vehicle vehicleToReserve = null;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.id.equals("1") && vehicle.isAvailable) {
                vehicleToReserve = vehicle;
                vehicle.isAvailable = false;
                break;
            }
        }

        String user = "Test User";
        if (vehicleToReserve != null) {
            String reservationId = "R" + (reservations.size() + 1);
            Reservation reservation = new Reservation(reservationId, vehicleToReserve, user);
            reservations.add(reservation);
        }

        assertNotNull(vehicleToReserve);
        assertEquals(1, reservations.size());
        assertEquals("R1", reservations.get(0).reservationId);
        assertEquals("Test User", reservations.get(0).user);
    }

    @Test
    public void testGenerateAgreement() {
        Vehicle vehicleToReserve = new Car("1", "Toyota Corolla", true);
        String user = "Test User";
        String reservationId = "R1";
        Reservation reservation = new Reservation(reservationId, vehicleToReserve, user);

        reservation.generateAgreement();

        assertTrue(outContent.toString().contains("Generating agreement for reservation ID: R1"));
        assertTrue(outContent.toString().contains("User: Test User"));
        assertTrue(outContent.toString().contains("Vehicle: Toyota Corolla"));
    }

    @Test
    public void testMakePayment() {
        Vehicle vehicleToReserve = new Car("1", "Toyota Corolla", true);
        String user = "Test User";
        String reservationId = "R1";
        Reservation reservation = new Reservation(reservationId, vehicleToReserve, user);

        reservation.makePayment();

        assertTrue(reservation.isPaid);
        assertTrue(outContent.toString().contains("Payment successful for reservation ID: R1"));
    }

    @Test
    public void testConfirmReservation() {
        Vehicle vehicleToReserve = new Car("1", "Toyota Corolla", true);
        String user = "Test User";
        String reservationId = "R1";
        Reservation reservation = new Reservation(reservationId, vehicleToReserve, user);

        reservation.makePayment();
        reservation.confirmReservation();

        assertTrue(reservation.isConfirmed);
        assertTrue(outContent.toString().contains("Reservation confirmed for reservation ID: R1"));
    }
}
