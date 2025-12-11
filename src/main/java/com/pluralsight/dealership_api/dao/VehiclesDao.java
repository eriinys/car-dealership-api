package com.pluralsight.dealership_api.dao;

import com.pluralsight.dealership_api.model.Vehicle;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class VehiclesDao {
    private DataSource dataSource;

    public Vehicle insertVehicle(Vehicle vehicle){
        String sql = "INSERT INTO vehicles (VIN, `year`, make, model, vehicle_type, color, odometer, price) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, vehicle.getVin());
            ps.setInt(2, vehicle.getYear());
            ps.setString(3, vehicle.getMake());
            ps.setString(4, vehicle.getModel());
            ps.setString(5, vehicle.getVehicleType());
            ps.setString(6, vehicle.getColor());
            ps.setInt(7, vehicle.getOdometer());
            ps.setDouble(8, vehicle.getPrice());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicle;
    }

    public void updateVehicle(String vin, Vehicle vehicle){
        String sql = "UPDATE vehicles SET VIN = ?, `year` = ?, make = ?, model = ?, vehicle_type = ?, " +
                "color = ?, odometer = ?, price = ? " +
                "WHERE VIN = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, vehicle.getVin());
            ps.setInt(2, vehicle.getYear());
            ps.setString(3, vehicle.getMake());
            ps.setString(4, vehicle.getModel());
            ps.setString(5, vehicle.getVehicleType());
            ps.setString(6, vehicle.getColor());
            ps.setInt(7, vehicle.getOdometer());
            ps.setDouble(8, vehicle.getPrice());
            ps.setString(9, vin);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVehicle(String vin){
        String sql = "DELETE FROM vehicles WHERE VIN = ?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, vin);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //uses wrapper types to handle null values passed from controller
    public List<Vehicle> filterVehicle(Double minPrice,
                                       Double maxPrice,
                                       String make,
                                       String model,
                                       Integer minYear,
                                       Integer maxYear,
                                       String color,
                                       Integer minMiles,
                                       Integer maxMiles,
                                       String type
    ){
        List<Vehicle> vehicles = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM vehicles WHERE TRUE");

        if(minPrice != null) sql.append(" AND price >= ?");
        if(maxPrice != null) sql.append(" AND price <= ?");
        if(make != null) sql.append(" AND make = ?");
        if(model != null) sql.append(" AND model = ?");
        if(minYear != null) sql.append(" AND `year` >= ?");
        if(maxYear != null) sql.append(" AND `year` <= ?");
        if(color != null) sql.append(" AND color = ?");
        if(minMiles != null) sql.append(" AND odometer >= ?");
        if(maxMiles != null) sql.append(" AND odometer <= ?");
        if(type != null) sql.append(" AND vehicle_type = ?");

        int index = 1;

        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql.toString())){
            if (minPrice != null) ps.setDouble(index++, minPrice);
            if(maxPrice != null) ps.setDouble(index++, maxPrice);
            if(make != null) ps.setString(index++, make);
            if(model != null) ps.setString(index++, model);
            if(minYear != null) ps.setInt(index++, minYear);
            if(maxYear != null) ps.setInt(index++, maxYear);
            if(color != null) ps.setString(index++, color);
            if(minMiles != null) ps.setInt(index++, minMiles);
            if(maxMiles != null) ps.setInt(index++, maxMiles);
            if(type != null) ps.setString(index++, type);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    String vin = rs.getString("VIN");
                    int year = rs.getInt("year");
                    String vmake = rs.getString("make");
                    String vmodel = rs.getString("model");
                    String vehicleType = rs.getString("vehicle_type");
                    String vcolor = rs.getString("color");
                    int odometer = rs.getInt("odometer");
                    double price = rs.getDouble("price");

                    vehicles.add(new Vehicle(vin, year, vmake, vmodel, vehicleType, vcolor, odometer, price));
                }
                return vehicles;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getAllVehicles(){
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String vin = rs.getString("VIN");
                int year = rs.getInt("year");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String vehicleType = rs.getString("vehicle_type");
                String color = rs.getString("color");
                int odometer = rs.getInt("odometer");
                double price = rs.getDouble("price");
                vehicles.add(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> getVehiclesByPrice(double min, double max){
        List<Vehicle> filtered = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE price >= ? AND price <= ?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, min);
            ps.setDouble(2, max);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    String vin = rs.getString("VIN");
                    int year = rs.getInt("year");
                    String make = rs.getString("make");
                    String model = rs.getString("model");
                    String vehicleType = rs.getString("vehicle_type");
                    String color = rs.getString("color");
                    int odometer = rs.getInt("odometer");
                    double price = rs.getDouble("price");

                    filtered.add(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price));
                }
                return filtered;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        ArrayList<Vehicle> filtered = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE LOWER(make) = LOWER(?) OR LOWER(model) = LOWER(?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, make);
            ps.setString(2, model);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String vin = rs.getString("VIN");
                    int year = rs.getInt("year");
                    String vehicleMake = rs.getString("make");
                    String vehicleModel = rs.getString("model");
                    String vehicleType = rs.getString("vehicle_type");
                    String color = rs.getString("color");
                    int odometer = rs.getInt("odometer");
                    double price = rs.getDouble("price");

                    filtered.add(new Vehicle(vin, year, vehicleMake, vehicleModel, vehicleType, color, odometer, price));
                }
                return filtered;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehiclesByYear(int min, int max){
        List<Vehicle> filtered = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE `year` >= ? AND `year` <= ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, min);
            ps.setInt(2, max);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String vin = rs.getString("VIN");
                    int year = rs.getInt("year");
                    String make = rs.getString("make");
                    String model = rs.getString("model");
                    String vehicleType = rs.getString("vehicle_type");
                    String color = rs.getString("color");
                    int odometer = rs.getInt("odometer");
                    double price = rs.getDouble("price");

                    filtered.add(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price));
                }
                return filtered;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        List<Vehicle> filtered = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE LOWER(color) = LOWER(?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, color);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String vin = rs.getString("VIN");
                    int year = rs.getInt("year");
                    String make = rs.getString("make");
                    String model = rs.getString("model");
                    String vehicleType = rs.getString("vehicle_type");
                    String vehicleColor = rs.getString("color");
                    int odometer = rs.getInt("odometer");
                    double price = rs.getDouble("price");

                    filtered.add(new Vehicle(vin, year, make, model, vehicleType, vehicleColor, odometer, price));
                }
                return filtered;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehiclesByMileage(int min, int max){
        List<Vehicle> filtered = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE odometer >= ? AND odometer <= ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, min);
            ps.setInt(2, max);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String vin = rs.getString("VIN");
                    int year = rs.getInt("year");
                    String make = rs.getString("make");
                    String model = rs.getString("model");
                    String vehicleType = rs.getString("vehicle_type");
                    String color = rs.getString("color");
                    int odometer = rs.getInt("odometer");
                    double price = rs.getDouble("price");

                    filtered.add(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price));
                }
                return filtered;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehiclesByType(String type){
        List<Vehicle> filtered = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE LOWER(vehicle_type) = LOWER(?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, type);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String vin = rs.getString("VIN");
                    int year = rs.getInt("year");
                    String make = rs.getString("make");
                    String model = rs.getString("model");
                    String vehicleType = rs.getString("vehicle_type");
                    String vehicleColor = rs.getString("color");
                    int odometer = rs.getInt("odometer");
                    double price = rs.getDouble("price");

                    filtered.add(new Vehicle(vin, year, make, model, vehicleType, vehicleColor, odometer, price));
                }
                return filtered;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Vehicle getVehicleByVin(String vin){
        String sql = "SELECT * FROM vehicles WHERE VIN = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, vin);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Vehicle(
                            rs.getString("VIN"),
                            rs.getInt("year"),
                            rs.getString("make"),
                            rs.getString("model"),
                            rs.getString("vehicle_type"),
                            rs.getString("color"),
                            rs.getInt("odometer"),
                            rs.getDouble("price")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
 