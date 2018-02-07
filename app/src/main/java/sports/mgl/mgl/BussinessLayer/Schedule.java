package sports.mgl.mgl.BussinessLayer;

/**
 * Created by Abdul Rehman Sarohy on 3/22/2017.
 */

public class Schedule {
    String id,g_id,player1,player2,winner,date;

    public Schedule(String id, String g_id, String player1, String player2, String winner, String date) {
        this.id = id;
        this.g_id = g_id;
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
