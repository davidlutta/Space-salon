import org.sql2o.Connection;

import java.util.List;

public class Client{

    private int id;
    //ID property
    //name Property
    private String name;
    //Style property
    private String style;
    //Stylist Id property
    private int stylistId;

    //Stylist Constructor
    public Client(String name, String style, int stylistId){
        this.name = name;

        this.style = style;

        this.stylistId = stylistId;
    }

    //Method to clear Test DataBase
    public static void clear(){
        //trying to make a connection to test DB
        try (Connection con = DB.sql2o.open()){

            String deleteClientQuery = "DELETE FROM clients *;";

            con.createQuery(deleteClientQuery).executeUpdate();

        }

    }

    //Method to get the name
    public String getClientName(){

        return name;

    }

    //Method to get Style of Client
    public String getClientStyle(){

        return style;

    }

    //Method to get all instances of Client from object
    public static List<Client> all() {
        String sql = "SELECT id, name, stylistId FROM clients";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Client.class);
        }
    }

    //Method to get the Stylists Id
    public int getStylistId() {
        return stylistId;
    }


    //Method To getID of a Client
    public int getId(){

        return id;

    }
    //Method to save all instances of the class
    public void save(){

        try(Connection con = DB.sql2o.open()) {

            String sql = "INSERT INTO clients(name, style, stylistId)VALUES (:name, :style, :stylistId)";

            this.id = (int) con.createQuery(sql, true)

                    .addParameter("name", this.name)

                    .addParameter("style", this.style)

                    .addParameter("stylistId", this.stylistId)

                    .executeUpdate()

                    .getKey();

        }
    }

    //Method to find the ID
    public static Client find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM clients where id=:id";
            Client client = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Client.class);
            return client;
        }
    }

    //Method to Update Info on the Database
    public void update(String name, String style){
        try(Connection con = DB.sql2o.open()){
            String sql = "UPDATE clients SET (name, style) = (:name, :style) WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("style", style)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    //Method to delete Client from DataBase
    public void deleteClient(){
        try (Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM clients WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    //Overriding method that will allow us to compare two instances

    @Override
    public boolean equals(Object otherClient) {

        if (!(otherClient instanceof Client)) {

            return false;

        } else {

            Client newClient = (Client) otherClient;

            return this.getClientName().equals(newClient.getClientName()) &&
                    this.getId() == newClient.getId() &&
                    this.getStylistId() == newClient.getStylistId();

        }
    }


}