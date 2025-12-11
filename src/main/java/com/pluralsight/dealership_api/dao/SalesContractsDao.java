package com.pluralsight.dealership_api.dao;

import com.pluralsight.dealership_api.model.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class SalesContractsDao {
    private DataSource dataSource;

    @Autowired
    public SalesContractsDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SalesContract addSalesContract(SalesContract sales){
        String sql = "INSERT INTO sales_contract (date, customer_name, email, VIN, financed) " +
                "VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setDate(1,Date.valueOf(sales.getDate()));
            ps.setString(2, sales.getCustomerName() );
            ps.setString(3, sales.getEmail());
            ps.setString(4, sales.getVin());
            ps.setBoolean(5, sales.isFinanced());
            ps.executeUpdate();

            try(ResultSet keys = ps.getGeneratedKeys()){
                if(keys.next()){
                    int salesId = keys.getInt(1);
                    SalesContract sc = getSalesContract(salesId);
                    return sc;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    public SalesContract getSalesContract(int id){
        String sql = "SELECT * FROM sales_contract WHERE sales_id = ?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return new SalesContract(
                            rs.getDate("date").toLocalDate(),
                            rs.getString("customer_name"),
                            rs.getString("email"),
                            rs.getString("VIN"),
                            rs.getInt("sales_id"),
                            rs.getBoolean("financed")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
