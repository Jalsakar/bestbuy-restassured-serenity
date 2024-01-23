package com.bestbuy.crudsuite;

import com.bestbuy.steps.StoreSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class StoreCRUDTestWithSteps extends TestBase {

    static String name = TestUtils.getRandomValue() + "Minnetonka";
    static String type = TestUtils.getRandomValue() + "BigBox";
    static String address = TestUtils.getRandomValue() + "Bhuvneshwar";
    static String address2 = TestUtils.getRandomValue() + "Nava Vadaj";
    static String city = TestUtils.getRandomValue() + "Ahmedabad";
    static String state = TestUtils.getRandomValue() + "Gujarat";
    static String zip = TestUtils.getRandomValue() + "363002";
    static double lat = Double.parseDouble(TestUtils.getRandomValue());
    static double lng = Double.parseDouble(TestUtils.getRandomValue());
    static String hours = TestUtils.getRandomValue();
    static int storeID;

    @Steps
    StoreSteps storeSteps;

    @Title("Create New Store")
    @Test
    public void test01(){

        ValidatableResponse response = storeSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours).statusCode(201);
        storeID = response.log().all().extract().path("id");
    }

    @Title("Verify if the store was added to application")
    @Test
    public void test02() {

        HashMap<String, Object> storeMap = storeSteps.getStoreInfoByStoreName(storeID);
        Assert.assertThat(storeMap, hasValue(name));
    }

    @Title("Update the store information and verify the updated information")
    @Test
    public void test03() {

        name = "Laser" + TestUtils.getRandomString();
        type = "Lasertype" + TestUtils.getRandomValue();

        ValidatableResponse response = storeSteps.updateStore(storeID,name,type,address,address2,city,state,zip,lat,lng,hours);
        response.log().all().statusCode(200);

    }

    @Title("Delete the Store and verify if the store has been deleted")
    @Test
    public void test04(){

        storeSteps.deleteStore(storeID).statusCode(200);
        storeSteps.getStoreById(storeID).statusCode(404);
    }

}
