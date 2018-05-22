import org.junit.*;
import org.sql2o.Sql2o;
import static org.junit.Assert.*;

public class ClientTest{
    //Before Test make a Connection to Test DataBase
    @Before
    public void setUp(){

        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);

    }

    //Clearing the TestData Base after use
    @After
    public void tearDown(){ Client.clear(); }
    //Test to check if Client Instantiates With a Name
    @Test
    public void StylistInstantiatesWithName_String(){

        Stylist myStylist = new Stylist("Harry", "Fade");

        myStylist.save();

        Client newClient = new Client("Harry", "Fade", myStylist.getId());

        assertTrue(newClient instanceof Client);

    }

    //Test to check if Client instantiates with a Style
    @Test
    public void StylistInstantiatesWithStyle_String(){

        Stylist myStylist = new Stylist("Harry", "Fade");

        myStylist.save();

        Client newClient = new Client("Potter", "fade", myStylist.getId());

        assertTrue(newClient instanceof Client);

    }

    //Test to check if all instances of the Client are returned
    @Ignore
    @Test
    public void StylistInstantiatesWithAllInstancesOfTheStylist_true(){

        Stylist myStylist = new Stylist("Harry", "Fade");

        myStylist.save();

        Client newClient1 = new Client("Harry", "Dreadlocks", myStylist.getId());

        newClient1.save();

        Client newClient2=  new Client("Potter", "fade", myStylist.getId());

        newClient2.save();

        assertTrue( Client.all().contains(newClient2));

    }

    //Test to clear Array list
    @Test
    public void Clear_EmptiesAllStylistsFromArrayList_0(){

        Stylist myStylist = new Stylist("Harry", "Fade");

        myStylist.save();

        Client newClient = new Client("Harry", "Dreadlocks", myStylist.getId());

        Client.clear();

        assertEquals(0, Client.all().size());

    }

    @Test
    public void save_savesCategoryIdIntoDB_true() {
        Stylist mysylist = new Stylist("Harry", "fade");
        mysylist.save();
        Client myClient = new Client("dr.Dre","dreadlocks", mysylist.getId());
        myClient.save();
        Client savedClient = Client.find(myClient.getId());
        assertEquals(savedClient.getStylistId(), mysylist.getId());
    }

    //Test to check if Stylist instantiates with ID
    @Ignore
    @Test
    public void getID_InstantiatesWIthId_GreaterThan0(){
        Client.clear();

        Stylist myStylist = new Stylist("Harry", "Fade");

        myStylist.save();

        Client newClient = new Client("Harry", "Dreadlocks", myStylist.getId());

        newClient.save();

        assertTrue(newClient.getId() > 0);

    }

    //test to check if Stylist is returned with the SameID
    @Ignore
    @Test
    public void find_FindInstantiatesWithSameId_newStylist2(){
        Stylist myStylist = new Stylist("Harry", "Fade");

        myStylist.save();

        Client newClient1 = new Client("Harry", "Dreadlocks", myStylist.getId());

        newClient1.save();

        Client newClient2 = new Client("Potter", "Fade", myStylist.getId());

        newClient2.save();

        assertEquals(Client.find(newClient2.getId()), newClient2);

    }

    //test to check if Stylist's Details can be updated
    @Ignore
    @Test
    public void Update_UpdateInstantiatesWithUpdatedInformation_True(){
        Stylist myStylist = new Stylist("Harry", "Fade");
        myStylist.save();
        Client newClient = new Client("Harry", "Dreadlock", myStylist.getId());
        newClient.save();
        newClient.update("Pharrel","fine Cut");
        assertEquals("Pharrel", Client.find(newClient.getId()).getClientName());
    }

    //test to check if stylist can be deleted
    @Ignore
    @Test
    public void Delete_DeleteDeletesInfoOfStylist_True(){
        Stylist myStylist = new Stylist("Harry", "Fade");

        myStylist.save();

        Client newClient = new Client("Harry", "Dreadlock", myStylist.getId());

        newClient.save();

        int newClientId = newClient.getId();

        newClient.deleteClient();

        assertEquals(null, Client.find(newClientId));

    }


}