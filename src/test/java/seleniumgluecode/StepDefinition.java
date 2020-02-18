package seleniumgluecode;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import classes.WorkingClassHero;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

public class StepDefinition {
		private RestTemplate restTemplate;
		private Object response;
		private String responseBody;
		private int httpErrorCode = 0;
		
		private String singleRecordURI = "http://167.99.65.170/calculator/insert";
		private String multipleRecordURI = "http://167.99.65.170/calculator/insertMultiple";		
		private String portalURI = "http://167.99.65.170/";
		private String taxReliefURI = "http://167.99.65.170/calculator/taxRelief";
		private String rakeDatabaseURI = "http://167.99.65.170/calculator/rakeDatabase";

		private String chromeDriverPath = "src\\test\\java\\chromedriver.exe";
		private String workingClassHeroFilePath = "src\\test\\java\\WorkingClassHero.csv";
		
		private String uploadFileButton = "custom-file-input";
		private String dispenseButtonLink = "//a[@href='dispense']";
		private String dispenseButtonText = "Dispense Now";
		private String dispenseButtonColour = "btn-danger";
		private String cashDispensedMessage = "Cash dispensed";
		private String taxReliefTableColPath = "//*[@id='contents']/div[2]/table/thead";
		private String taxReliefTableRowPath = "//*[@id='contents']/div[2]/table/tbody/tr";
		
		
		private List<WorkingClassHero> workingClassHeroList = new ArrayList<WorkingClassHero>(); 
		
		private char maskedCharacter = '$';
		
		private WebDriver driver;

	    @Given("^the single record insertion api endpoint is set (correctly|wrongly)$")
	    public void setSingleRecordInsertionEndpoint(String uriStatus) {
	    	if(uriStatus.contentEquals("correctly")) {
	    		System.out.println("Setting single record endpoint: " + singleRecordURI);
	    	} else {
	    		this.singleRecordURI = "http://167.99.65.170/calculator/Insert";
	    		System.out.println("Setting single record endpoint wrongly: " + singleRecordURI);
	    	}
	    }
	    
	    @When("^the clerk sends a request for single record insertion with the following data$")
	    public void sendRequestForSingleRecordInsertion(DataTable dt){
	    	//set http headers
	    	HttpHeaders headers = setHttpHeaders();
	    	
	    	//retrieve data from user input
	    	List<Map<String, String>> list = dt.asMaps(String.class, String.class);
	    	String birthday = list.get(0).get("birthday");
	    	String gender = list.get(0).get("gender");
	    	String name = list.get(0).get("name");
	    	String natid = list.get(0).get("natid");
	    	String salary = list.get(0).get("salary");
	    	String tax = list.get(0).get("tax");
	    	
	    	String jsonBody = "{\"birthday\":\""+birthday+"\",\"gender\":\""+gender+"\",\"name\":\""+name+"\",\"natid\":\""+natid+"\",\"salary\":\""+salary+"\",\"tax\":\""+tax+"\"}";
	    	System.out.println("json body: " + jsonBody);
	    	
	    	HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);
	    	this.restTemplate = new RestTemplate();
	    	try {
	    		this.response = this.restTemplate.postForEntity(singleRecordURI, entity, String.class);
	    	}
	    	catch (HttpStatusCodeException hsce) {
	    		setAndPrintError(hsce);
	    	}
	    }
	    
	    @Then("^the clerk should receive a HTTP response (\\d+)$")
	    public void verifyHttpResponse(int desiredHttpResponse) {
	    	
	    	try {
	    		this.responseBody = ((HttpEntity<String>) this.response).getBody().toString();
	        	System.out.println("response body ---> " + responseBody);
	        	System.out.println("response code ---> " +  ((ResponseEntity<String>) this.response).getStatusCode().value());
	        	
	        	//check if the http status code matches with the test param
	        	Assert.assertEquals(desiredHttpResponse, ((ResponseEntity<String>) this.response).getStatusCode().value());
	    	}catch(NullPointerException e) {
	    		
	    	}
	    	if (httpErrorCode>0) {
				System.out.println("--Expected error code: " + desiredHttpResponse);
				System.out.println("--Actual error code: " + this.httpErrorCode);
				
				//check if the http status code matches with the test param
				Assert.assertEquals(desiredHttpResponse, this.httpErrorCode);
				System.out.println("Error code matches!");
	    	}
	    }
	    
	    @Given("^the multiple record insertion api endpoint is set (correctly|wrongly)$")
	    public void setMultipleRecordInsertionEndpoint(String uriStatus) {
	    	if (uriStatus.equals("correctly")) {
	    		System.out.println("Setting multiple record endpoint: " + multipleRecordURI);
	    	}else {
	    		this.multipleRecordURI = "http://167.99.65.170/calculator/insertmultiple";
	    	}
	    }
	    
	    @When("^the clerk sends a request for multiple record insertion with the following data$")
	    public void sendRequestForMultipleRecordInsertion(DataTable dt) {
	    	HttpHeaders headers = setHttpHeaders();
	    	
	    	List<Map<String, String>> list = dt.asMaps(String.class, String.class);
	    	
	    	String jsonBody = "[";
	    	
	    	for(int i=0; i<list.size(); i++) {
	        	String birthday = list.get(i).get("birthday");
	        	String gender = list.get(i).get("gender");
	        	String name = list.get(i).get("name");
	        	String natid = list.get(i).get("natid");
	        	String salary = list.get(i).get("salary");
	        	String tax = list.get(i).get("tax");
	        	
	        	jsonBody += "{\"birthday\":\""+birthday+"\",\"gender\":\""+gender+"\",\"name\":\""+name+"\",\"natid\":\""+natid+"\",\"salary\":\""+salary+"\",\"tax\":\""+tax+"\"}";
	        	//join the different rows of data into the same json
	        	if (i!=list.size()-1) {
	        		jsonBody += ",";
	        	}
	    	}
	    	
	    	jsonBody += "]";
	    	
	    	System.out.println("json body: " + jsonBody);
	    	
	    	HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);
	    	this.restTemplate = new RestTemplate();
	    	try {
	        	this.response = this.restTemplate.postForEntity(multipleRecordURI, entity, String.class);  	
	    	}catch (HttpStatusCodeException hsce) {
	    		setAndPrintError(hsce);
	    	}
	    }
	    
	    @Given("^the (?:Clerk|Governor|Bookkeeper)? visits the Citizen Disbursement Portal$")
	    public void visitPortal(){     
	    	setChromeDriver();
	        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	        System.out.println("Visiting the link: " + portalURI);
	        this.driver.get(portalURI);
	    }
	    
	    @When("^the clerk clicks on the upload file button$")
	    public void clickUploadButton() {
	    	  WebElement uploadElement = this.driver.findElement(By.className(uploadFileButton));
	    	  Assert.assertTrue("Upload file button not found on portal!", uploadElement!=null);
		      System.out.println("Upload file button found on portal!");
	    }
	    
	    @Then("^the clerk should be able to upload the file$")
	    public void checkIfAbleToUpload() {
	        File file = new File(workingClassHeroFilePath);
	        WebElement uploadElement = this.driver.findElement(By.className(uploadFileButton));
	        uploadElement.sendKeys(file.getAbsolutePath());
	        System.out.println("File upload successful!");
	    }
	   
	    @And("^I proceed to close the portal$")
	    public void closePortal() {
	    	exitChromeDriver();
	    }
	    
	    @When("^the Governor clicks on the dispense now button$")
	    public void clickDispenseButton() {
	    	//retrieve dispense btn element
	    	WebElement dispenseButton = this.driver.findElement(By.xpath(dispenseButtonLink));
	    	Assert.assertTrue("Dispense button not found on portal!", dispenseButton!=null);
	    	
	    	//verify if btn text matches Dispense Now
	    	Assert.assertEquals(dispenseButtonText, dispenseButton.getText());
	    	System.out.println("Text on button matches " + dispenseButtonText);
	    	
	    	//Verify btn is red colour
	    	boolean isRed = checkButtonColour(dispenseButton);	    	
	    	Assert.assertEquals("Dispense button is not red!",true, isRed);
	    	System.out.println("Dispense button matches red!");
	    	System.out.println("Clicking on the button now...");
	    	
	    	dispenseButton.click(); 
	    }

		@Then("^he should see the cash dispensed message$")
	    public void checkCashDispensedMessage() {
			WebElement element = this.driver.findElement(By.className("display-4"));
			Assert.assertEquals(cashDispensedMessage, element.getText());
			System.out.println("Text matches " + cashDispensedMessage);
			
	    }
		
		@Given("^the database has the following tax relief data$")
		public void prepopulateTaxRelief(DataTable dt) throws IOException {
			rakeDatabase();
			sendRequestForMultipleRecordInsertion(dt);
		}
		
		@When("^the Bookkeeper queries for tax relief data via API$")
		public void queryTaxRelief() throws IOException, ParseException {
			System.out.println("Retrieving tax relief data from " + taxReliefURI);
			URL url = new URL(taxReliefURI);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			 conn.setRequestMethod("GET");
			 conn.connect();
			 String jsonOutput = "";
			 Scanner sc = new Scanner(url.openStream());
			 while(sc.hasNext())
			 {
				 jsonOutput+=sc.nextLine();
			 }
			 sc.close();
			 conn.disconnect();
			 
			 System.out.println("json output received: " + jsonOutput);
			 
			 if(jsonOutput.equals("[]")) {
				 System.out.println("There is no data in the database!");
			 }
			 
			 JSONParser parser = new JSONParser(); 
			 Object obj = parser.parse(jsonOutput);
			 JSONArray jsonArr = (JSONArray) obj;
			 jsonArr.forEach(jsonObj -> getValues((JSONObject) jsonObj));
		}
		
		@Then("^he should see the same following computed tax reflief results in the UI$")
		public void verifyTaxReliefViaUi(DataTable dt) {
	    	List<Map<String, String>> expectedList = dt.asMaps(String.class, String.class);
	    	
	    	List <WebElement> columns = this.driver.findElements(By.xpath(taxReliefTableColPath));
	    	List <WebElement> taxReliefTableRows = this.driver.findElements(By.xpath(taxReliefTableRowPath));
	    	
	    	List<WorkingClassHero> actualList = new ArrayList<WorkingClassHero>();
	    	
	    	//retrieve the actual data from the table in the ui
	    	for(int i=0; i<taxReliefTableRows.size(); i ++) {
	    		String rowData = taxReliefTableRows.get(i).getText();
	    		String actualNatId = rowData.substring(0, rowData.indexOf(' '));
	    		String actualRelief = rowData.substring(rowData.indexOf(' ') + 1);
	    		WorkingClassHero wch = new WorkingClassHero(actualNatId,actualRelief);
	    		actualList.add(wch);
	    	}
	    	
	    	//compare expected results and actual results
	    	for(int i=0; i<expectedList.size(); i++) {
	        	String expectedNatId = expectedList.get(i).get("natid");
	        	String expectedRelief = expectedList.get(i).get("relief");
	        	 	
	        	String actualNatId = actualList.get(i).getNatId();
	        	String actualRelief = actualList.get(i).getReliefAmount();
	        	
	        	Assert.assertEquals(expectedNatId, actualNatId);
	        	Assert.assertEquals(expectedRelief, actualRelief);
	        	System.out.println("Working class hero data matches : " + expectedNatId + " " + expectedRelief);
	    	}
	    	System.out.println("All data matches in the portal!");
		}
		
		@Then("^he should expect the following computed tax relief results$")
		public void verifyTaxReliefViaApi(DataTable dt) {
			List<Map<String, String>> expectedList = dt.asMaps(String.class, String.class);

	    	for(int i=0; i<expectedList.size(); i++) {
	        	String expectedName = expectedList.get(i).get("name");
	        	String expectedNatId = expectedList.get(i).get("natid");
	        	String expectedRelief = expectedList.get(i).get("relief");
	        	
	        	String actualName = this.workingClassHeroList.get(i).getName();
	        	String actualNatId = this.workingClassHeroList.get(i).getNatId();
	        	String actualRelief = this.workingClassHeroList.get(i).getReliefAmount();
	        	
	        	Assert.assertEquals(expectedName, actualName);
	        	Assert.assertEquals(expectedNatId, actualNatId);
	        	Assert.assertEquals(expectedRelief, actualRelief);
	        	System.out.println("Working class hero data matches : " + expectedName +  " " + expectedNatId + " " + expectedRelief);
	    	}
	    	System.out.println("All data matches in the api!");
		}
	    private HttpHeaders setHttpHeaders() {
	    	HttpHeaders headers = new HttpHeaders();
	    	headers.add("Accept", "application/json");
	    	headers.add("Content-Type", "application/json");
	    	return headers;
	    }
	    
	    private void setAndPrintError(HttpStatusCodeException hsce) {
			this.httpErrorCode = hsce.getStatusCode().value();
			System.out.println("--ERROR DETECTED-- CODE: " + this.httpErrorCode);
	    }
	    
	    private void setChromeDriver() {
	    	System.setProperty("webdriver.chrome.driver", chromeDriverPath);
	        this.driver = new ChromeDriver();
	    }
	    
	    private void exitChromeDriver() {
	    	this.driver.quit();
	    }
	    
	    private boolean checkButtonColour(WebElement dispenseButton) {
	    	//retrieve the class in the element to match with btn-danger = red
	    	String dispenseButtonClasses = dispenseButton.getAttribute("class");
	    	
	    	for (String str: dispenseButtonClasses.split(" ")) {
	    		if(str.equals(dispenseButtonColour)) {
	    			return true;
	    		}
	    	}
			return false;
		}
	    
		private void getValues(JSONObject jsonObj)
		{
			String natid = (String) jsonObj.get("natid");
			String name = (String) jsonObj.get("name");
			String reliefAmount = (String) jsonObj.get("relief");
			
			//check if natid is masked from the 5th character onwards
			String natIdSubStr = natid.substring(4,natid.length());
			
			for(int i=0; i<natIdSubStr.length(); i++) {
				if(natIdSubStr.charAt(i) != maskedCharacter) {
					System.out.println("natid field is not masked for " + natid);
				}
			}
			
			WorkingClassHero wch = new WorkingClassHero(natid,name,reliefAmount);
			this.workingClassHeroList.add(wch);
		}
		
		private void rakeDatabase() throws IOException {
			URL url = new URL(rakeDatabaseURI);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			 conn.setRequestMethod("POST");
			 conn.connect();
			 System.out.println("raking db: " + conn.getResponseCode());
			 conn.disconnect();
		}

}
