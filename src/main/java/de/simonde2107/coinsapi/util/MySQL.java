package de.simonde2107.coinsapi.util;

import org.bukkit.entity.Player;

import java.sql.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MySQL {


    private String HOST;
    private String PORT;
    private String DATABASE;
    private String USER;
    private String PASSWORD;
    public static Connection con;

    public MySQL(final String host, final String port, final String database, final String user, final String password) {
        this.HOST = host;
        this.PORT = port;
        this.DATABASE = database;
        this.USER = user;
        this.PASSWORD = password;
        this.connect();

        if(this.isConnected()){
            this.update("CREATE TABLE IF NOT EXISTS Coins (UUID varchar(64), COINS int);");
        }
    }

    public void connect() {
        try {
            MySQL.con = DriverManager.getConnection("jdbc:mysql://" + this.HOST + ":" + this.PORT + "/" + this.DATABASE + "?autoReconnect=true", this.USER, this.PASSWORD);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return MySQL.con != null;
    }

    public void close() {
        try {
            if (MySQL.con != null) {
                MySQL.con.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(final String qre) {
        if (MySQL.con != null) {
            try {
                final Statement st = MySQL.con.createStatement();
                st.executeUpdate(qre);
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();//DER WAR NICHT DRIN
            }
        }
    }

    public void updateWithBoolean(final String qry, final boolean value) {
        if (isConnected()) {
            (new FutureTask(new Runnable() {
                PreparedStatement ps;

                public void run() {
                    try {
                        this.ps = MySQL.this.con.prepareStatement(qry);
                        this.ps.setBoolean(1, value);
                        this.ps.executeUpdate();
                        this.ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }, 1)).run();
        }
    }
    public void updateWithString(final String qry, final String value) {
        if (isConnected()) {
            (new FutureTask(new Runnable() {
                PreparedStatement ps;

                public void run() {
                    try {
                        this.ps = MySQL.this.con.prepareStatement(qry);
                        this.ps.setString(1, value);
                        this.ps.executeUpdate();
                        this.ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }, 1)).run();
        }
    }


    public static int getCoins(Player player) {
        try {
            PreparedStatement st = MySQL.con.prepareStatement("SELECT * FROM Coins WHERE UUID = ?");
            st.setString(1, player.getUniqueId().toString());
            st.executeQuery();
            ResultSet rs = st.executeQuery();
            if (rs.next())
                return rs.getInt("COINS");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -999999999;
    }

    public static void setCoins(Player player, int coins) {
        if (getCoins(player) == -999999999) {
            try {
                String insert = "INSERT INTO Coins (UUID,COINS) VALUES (?,?)";
                PreparedStatement st = MySQL.con.prepareStatement(insert);
                st.setString(1, player.getUniqueId().toString());
                st.setInt(2, coins);
                st.executeUpdate();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String update = "UPDATE Coins SET COINS = ? WHERE UUID = ?";
                PreparedStatement st = MySQL.con.prepareStatement(update);
                st.setString(2, player.getUniqueId().toString());
                st.setInt(1, coins);
                st.executeUpdate();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void updateWithInt(final String qry, final Integer value) {
        if (isConnected()) {
            (new FutureTask(new Runnable() {
                PreparedStatement ps;

                public void run() {
                    try {
                        this.ps = MySQL.this.con.prepareStatement(qry);
                        this.ps.setInt(1, value);
                        this.ps.executeUpdate();
                        this.ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }, 1)).run();
        }
    }


    public ResultSet query(final String qre) {
        if (MySQL.con != null) {
            ResultSet rs = null;
            try {
                final Statement st = MySQL.con.createStatement();
                rs = st.executeQuery(qre);
            }
            catch (SQLException e) {
                this.connect();
                System.err.print(e);
            }
            return rs;
        }
        return null;
    }

    public ResultSet getResult(final String qry) {
        if (isConnected()) {
            try {
                FutureTask<ResultSet> task = new FutureTask<>(new Callable<ResultSet>() {
                    PreparedStatement ps;

                    public ResultSet call() throws Exception {
                        this.ps = MySQL.this.con.prepareStatement(qry);
                        return this.ps.executeQuery();
                    }
                });
                task.run();
                return task.get();
            } catch (InterruptedException|java.util.concurrent.ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
