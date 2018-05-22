import org.junit.*;
import org.sql2o.Sql2o;
import java.util.Arrays;
import static org.junit.Assert.*;

public class StylistTest{
    //Before Test make a Connection to Test DataBase
    @Before
    public void setUp(){

        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);

    }

    //Clearing the TestData Base after use
    @After
    public void tearDown(){ Stylist.clear(); }

    //Test to check if Stylist Instantiates With a Name
    @Test
    public void StylistInstantiatesWithName_String(){

        Stylist newStylist = new Stylist("Harry", "Fade");

        assertTrue(newStylist instanceof Stylist);

    }

    //Test to check if Stylist instantiates with a Style
    @Test
    public void StylistInstantiatesWithStyle_String(){

        Stylist newStylist = new Stylist("Potter", "fade");

        assertTrue(newStylist instanceof Stylist);

    }

    //Test to check if all instances of the Stylists are returned
    @Test
    public void StylistInstantiatesWithAllInstancesOfTheStylist_true(){

        Stylist newStylist1 = new Stylist("Harry", "Dreadlocks");

        newStylist1.save();

        Stylist newStylist2=  new Stylist("Potter", "fade");

        newStylist2.save();

        assertTrue( Stylist.allStyle().contains(newStylist2));

    }

    //Test to clear Array list
    @Test
    public void Clear_EmptiesAllStylistsFromArrayList_0(){

        Stylist newStylist = new Stylist("Harry", "Dreadlocks");

        Stylist.clear();

        assertEquals(0, Stylist.allStyle().size());

    }

    //test to check whether we are receiving all clients
    @Test
    public void getTasks_retrievesALlTasksFromDatabase_tasksList() {
        Stylist mysylist = new Stylist("Harry", "fade");
        mysylist.save();
        Client myClient1 = new Client("dr.Dre","dreadlocks", mysylist.getId());
        myClient1.save();
        Client myClient2 = new Client("Andre","fade", mysylist.getId());
        myClient2.save();
        Client[] clients = new Client[] { myClient1, myClient2 };
        assertTrue(mysylist.getClients().containsAll(Arrays.asList(clients)));
    }

    //Test to check if Stylist instantiates with ID
    @Test
    public void getID_InstantiatesWIthId_GreaterThan0(){
        Stylist.clear();

        Stylist newStylist = new Stylist("Harry", "Dreadlocks");

        newStylist.save();

        assertTrue(newStylist.getId() > 0);

    }

    //test to check if Stylist is returned with the SameID
    @Test
    public void find_FindInstantiatesWithSameId_newStylist2(){
        Stylist newStylist1 = new Stylist("Harry", "Dreadlocks");

        newStylist1.save();

        Stylist newStylist2 = new Stylist("Potter", "Fade");

        newStylist2.save();

        assertEquals(Stylist.find(newStylist2.getId()), newStylist2);

    }

    //test to check if Stylist's Details can be updated
    @Test
    public void Update_UpdateInstantiatesWithUpdatedInformation_True(){
        Stylist newStylist = new Stylist("Harry", "Dreadlock");

        newStylist.save();

        newStylist.update("Pharrel","fine Cut");

        assertEquals("Pharrel", Stylist.find(newStylist.getId()).getName());

    }

    //test to check if stylist can be deleted
    @Test
    public void Delete_DeleteDeletesInfoOfStylist_True(){

        Stylist newStylist = new Stylist("Harry", "Dreadlock");

        newStylist.save();

        int newStylistId = newStylist.getId();

        newStylist.delete();

        assertEquals(null, Stylist.find(newStylistId));

    }


}