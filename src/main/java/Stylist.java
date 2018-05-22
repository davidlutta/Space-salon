import org.sql2o.Connection;

import java.util.List;

public class Stylist{
    //ID property
    private  int id;
    //name Property
    private String name;
    //Style property
    private String style;

    //Stylist Constructor
    public Stylist(String name, String style){
        this.name = name;

        this.style = style;
    }

    //Method to clear Test DataBase
    public static void clear(){
        //trying to make a connection to test DB
        try (Connection con = DB.sql2o.open()){

            String deleteStylistQuery = "DELETE FROM stylist *;";

            con.createQuery(deleteStylistQuery).executeUpdate();

        }

    }

    //Method to get the name
    public String getName(){

        return name;

    }

    //Method to get Style of Stylist
    public String getStyle(){

        return style;

    }

    //Method to get all instances of Stylist from object
    public static List<Stylist> allStyle(){

        String sql = "SELECT * FROM stylist;";

        try(Connection con = DB.sql2o.open()){

            return con.createQuery(sql).executeAndFetch(Stylist.class);

        }

    }

    //Method to save all instances of the class
    public void save(){

        try(Connection con = DB.sql2o.open()) {

            String sql = "INSERT INTO stylist (name, style)VALUES (:name, :style)";

            this.id = (int) con.createQuery(sql, true)

                    .addParameter("name", this.name)

                    .addParameter("style", this.style)

                    .executeUpdate()

                    .getKey();

        }
    }

    //Method To getID of a stylist
    public int getId(){

        return id;

    }

    //Method to find the ID
    public static Stylist find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM stylist where id=:id";
            Stylist stylist = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Stylist.class);
            return stylist;
        }
    }

    //Method to retrieve Clients
    public List<Client> getClients() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM clients where stylistid=:id";
            return con.createQuery(sql)
                    .addParameter("id", this.id)
                    .executeAndFetch(Client.class);
        }
    }

    //Method to Update Info on the Database
    public void update(String name, String style){

        try(Connection con = DB.sql2o.open()){

            String sql = "UPDATE stylist SET (name, style) = (:name, :style) WHERE id = :id;";

            con.createQuery(sql)

                    .addParameter("name", name)

                    .addParameter("style", style)

                    .addParameter("id", this.id)

                    .executeUpdate();
        }
    }

    //Method to delete Stylist from DataBase
        public void delete(){
        try (Connection con = DB.sql2o.open()) {

            String sql = "DELETE FROM stylist WHERE id = :id";

            con.createQuery(sql)

                    .addParameter("id", this.id)

                    .executeUpdate();

        }

    }

    //Method to get all the clients from the DataBase
    public List<Client> all(){

        String sql = "SELECT * FROM clients WHERE id = :id";

        try(Connection con = DB.sql2o.open()){

            return con.createQuery(sql)

                    .addParameter("id", this.id)

                    .executeAndFetch(Client.class);

        }
    }

    //Overriding method that will allow us to compare two instances

    @Override
    public boolean equals(Object otherStylist) {

        if (!(otherStylist instanceof Stylist)) {

            return false;

        } else {

            Stylist newStylist = (Stylist) otherStylist;

            return this.getName().equals(newStylist.getName());

        }
    }
}