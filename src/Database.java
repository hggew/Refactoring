import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    static Connection con;
    static Statement stmt;
    static PreparedStatement pstmt;
    String Driver = "";
    static String url = "jdbc:mysql://localhost:3306/yut?&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false";
    static String userid = "root";
    static String pwd = "0000";
    static ResultSet resultSet;

    public static void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver load success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("DataBase connect ready...");
            con = DriverManager.getConnection(url, userid, pwd);
            System.out.println("DabaBase connect success");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    //RankPanel의 ShowRank에서 호출
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        try {
            System.out.println("SQL : " + queryStmt + "\n");
            stmt = con.createStatement();
            // stmt에 들어있는 select 문을 사용하기 위해 executeQuery 사용
            // resultSet 객체에 결과값 저장
            resultSet = stmt.executeQuery(queryStmt);
        }
        catch (SQLException e) {
            System.out.println("DBconnect - executeQuery fail");
            throw e;
        }
        finally {
        }
        return resultSet;
    }

}