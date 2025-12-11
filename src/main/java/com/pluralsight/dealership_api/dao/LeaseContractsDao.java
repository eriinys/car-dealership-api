package com.pluralsight.dealership_api.dao;

import com.pluralsight.dealership_api.model.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class LeaseContractsDao{
    private DataSource dataSource;

    @Autowired
    public LeaseContractsDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public LeaseContract addLeaseContract(LeaseContract lease){
        String sql = "INSERT INTO lease_contract (date, customer_name, email, VIN) " +
                "VALUES (?, ?, ?, ?)";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setDate(1,Date.valueOf(lease.getDate()));
            ps.setString(2, lease.getCustomerName());
            ps.setString(3, lease.getEmail());
            ps.setString(4, lease.getVin());
            ps.executeUpdate();

            try(ResultSet keys = ps.getGeneratedKeys()){
                if(keys.next()){
                    int salesId = keys.getInt(1);
                    LeaseContract lc = getLeaseContract(salesId);
                    return lc;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LeaseContract getLeaseContract(int id){
        String sql = "SELECT * FROM lease_contract WHERE lease_id = ?";

        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return new LeaseContract(
                            rs.getDate("date").toLocalDate(),
                            rs.getString("customer_name"),
                            rs.getString("email"),
                            rs.getString("VIN"),
                            rs.getInt("lease_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
