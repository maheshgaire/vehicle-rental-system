package com.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Rentable {
    void generateAgreement();
    void makePayment();
    void confirmReservation();
}

class Vehicle {
    String id;
    String model;
    boolean isAvailable;

    Vehicle(String id, String model, boolean isAvailable) {
        this.id = id;
        this.model = model;
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Vehicle{id='" + id + "', model='" + model + "', isAvailable=" + isAvailable + "}";
    }
}

class Car extends Vehicle {
    Car(String id, String model, boolean isAvailable) {
        super(id, model, isAvailable);
    }
}

class Truck extends Vehicle {
    Truck(String id, String model, boolean isAvailable) {
        super(id, model, isAvailable);
    }
}

class Reservation implements Rentable {
    String reservationId;
    Vehicle vehicle;
    String user;
    boolean isPaid;
    boolean isConfirmed;

    Reservation(String reservationId, Vehicle vehicle, String user) {
        this.reservationId = reservationId;
        this.vehicle = vehicle;
        this.user = user;
        this.isPaid = false;
        this.isConfirmed = false;
    }

    @Override
    public void generateAgreement() {
        System.out.println("Generating agreement for reservation ID: " + reservationId);
        System.out.println("User: " + user);
        System.out.println("Vehicle: " + vehicle.model);
        System.out.println("Agreement generated successfully.");
    }

    @Override
    public void makePayment() {
        this.isPaid = true;
        System.out.println("Payment successful for reservation ID: " + reservationId);
    }

    @Override
    public void confirmReservation() {
        if (isPaid) {
            this.isConfirmed = true;
            System.out.println("Reservation confirmed for reservation ID: " + reservationId);
        } else {
            System.out.println("Please complete payment to confirm the reservation.");
        }
    }

    @Override
    public String toString() {
        return "Reservation{reservationId='" + reservationId + "', vehicle=" + vehicle + ", user='" + user + "', isPaid=" + isPaid + ", isConfirmed=" + isConfirmed + "}";
    }
}

public class VehicleRentalSystem {
    private static List<Vehicle> vehicles = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeVehicles();

        while (true) {
            System.out.println("\nVehicle Rental System");
            System.out.println("1. Search available vehicles");
            System.out.println("2. Make a reservation");
            System.out.println("3. Generate agreement");
            System.out.println("4. Make payment");
            System.out.println("5. Confirm reservation");
            System.out.println("6. View reservations");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    searchAvailableVehicles();
                    break;
                case 2:
                    makeReservation();
                    break;
                case 3:
                    generateAgreement();
                    break;
                case 4:
                    makePayment();
                    break;
                case 5:
                    confirmReservation();
                    break;
                case 6:
                    viewReservations();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeVehicles() {
        vehicles.add(new Car("1", "Toyota Corolla", true));
        vehicles.add(new Car("2", "Honda Civic", true));
        vehicles.add(new Truck("3", "Ford F-150", false));
        vehicles.add(new Truck("4", "Chevrolet Silverado", true));
    }

    private static void searchAvailableVehicles() {
        System.out.println("Available vehicles:");
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable) {
                System.out.println(vehicle);
            }
        }
    }

    private static void makeReservation() {
        System.out.print("Enter your name: ");
        String user = scanner.nextLine();
        System.out.print("Enter vehicle ID to reserve: ");
        String vehicleId = scanner.nextLine();

        Vehicle vehicleToReserve = null;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.id.equals(vehicleId) && vehicle.isAvailable) {
                vehicleToReserve = vehicle;
                vehicle.isAvailable = false;
                break;
            }
        }

        if (vehicleToReserve != null) {
            String reservationId = "R" + (reservations.size() + 1);
            Reservation reservation = new Reservation(reservationId, vehicleToReserve, user);
            reservations.add(reservation);
            System.out.println("Reservation made successfully. Reservation ID: " + reservationId);
        } else {
            System.out.println("Vehicle not available or invalid vehicle ID.");
        }
    }

    private static void generateAgreement() {
        System.out.print("Enter reservation ID: ");
        String reservationId = scanner.nextLine();

        Reservation reservation = findReservationById(reservationId);
        if (reservation != null) {
            reservation.generateAgreement();
        } else {
            System.out.println("Invalid reservation ID.");
        }
    }

    private static void makePayment() {
        System.out.print("Enter reservation ID: ");
        String reservationId = scanner.nextLine();

        Reservation reservation = findReservationById(reservationId);
        if (reservation != null) {
            reservation.makePayment();
        } else {
            System.out.println("Invalid reservation ID.");
        }
    }

    private static void confirmReservation() {
        System.out.print("Enter reservation ID: ");
        String reservationId = scanner.nextLine();

        Reservation reservation = findReservationById(reservationId);
        if (reservation != null) {
            reservation.confirmReservation();
        } else {
            System.out.println("Invalid reservation ID.");
        }
    }

    private static void viewReservations() {
        System.out.println("Current reservations:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    private static Reservation findReservationById(String reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.reservationId.equals(reservationId)) {
                return reservation;
            }
        }
        return null;
    }
}

