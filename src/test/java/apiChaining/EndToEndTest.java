package apiChaining;

import org.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndTest {
	
	Response response;
	String BaseURI = "http://54.91.193.52:8088/employees";
	
	@Test
	public void test1() {
	
			
		response=GetMethodAll();
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		response=PostMethod("test","test","5000","abcd@gmail.com");
		AssertJUnit.assertEquals(response.getStatusCode(), 201);
		JsonPath jpath = response.jsonPath();
		int EmpId=jpath.get("id");
		System.out.println("ID : "+	EmpId);

	
		response=PutMethod(EmpId,"test1","test1","7000","abcd1@gmail.com");
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		jpath = response.jsonPath();
		String EmpName=jpath.get("firstName");
		System.out.println("Emp Name : "+	EmpName);
		AssertJUnit.assertEquals(EmpName,"test1");
		
		response=DeleteMethod(EmpId);
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		AssertJUnit.assertEquals(response.body().asString(), "");
		
		response=GetMethod(EmpId);
		AssertJUnit.assertEquals(response.getStatusCode(), 400);
		jpath =response.jsonPath();
        //Assert.assertEquals(Jpath.get("message"), "Entity Not Found");
		AssertJUnit.assertEquals(jpath.get("message"), "Entity Not Found");

	}
	
	public Response GetMethodAll() {
		RestAssured.baseURI=BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();

		return response;
	}

	public Response PostMethod(String FName, String LName, String Salary, String EmailId) {
		RestAssured.baseURI=BaseURI;
		JSONObject jobj=new JSONObject();
		jobj.put("firstName", FName);
		jobj.put("lastName", LName);
		jobj.put("salary", Salary);
		jobj.put("email", EmailId);

		
		RequestSpecification request = RestAssured.given();
		Response response =	request.contentType(ContentType.JSON).accept(ContentType.JSON).body(jobj.toString()).post();


		return response;
	}

	public Response PutMethod(int EmpId, String FName, String LName, String Salary, String EmailId) {
		RestAssured.baseURI=BaseURI;
		JSONObject jobj=new JSONObject();
		jobj.put("firstName", FName);
		jobj.put("lastName", LName);
		jobj.put("salary", Salary);
		jobj.put("email", EmailId);
		
		RequestSpecification request = RestAssured.given();
		Response response =	request.contentType(ContentType.JSON).accept(ContentType.JSON).body(jobj.toString()).put("/"+EmpId);

		return response;
	}

	public Response DeleteMethod(int EmpId) {
		RestAssured.baseURI=BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.delete("/"+EmpId);

		return response;
	}
	
	public Response GetMethod(int EmpId) {
		RestAssured.baseURI=BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.get("/"+EmpId);

		return response;
	}

}
