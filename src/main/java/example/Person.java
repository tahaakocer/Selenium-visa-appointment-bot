package example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Person {

	final private String country = "Turkey";
	private String passNum;
	private String date;
	private String phoneNum;
	private Random random;
	private String namesFilePath = "src/main/resources/names.txt";
	private String surnamesFilePath = "src/main/resources/surnames.txt";
	private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<String> surnames = new ArrayList<String>();
	private File file;
	private FileReader fileReader = null;
	private BufferedReader bufferedReader;

	public Person() {
		random = new Random();
		setArrays(namesFilePath, names);
		setArrays(surnamesFilePath, surnames);
	}

	public void setArrays(String filePath, ArrayList<String> list) {
		try {
	        file = new File(filePath);
	        fileReader = new FileReader(file);
	        bufferedReader = new BufferedReader(fileReader);

	        String line;

	        while ((line = bufferedReader.readLine()) != null) {
	            list.add(line);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public String getName() {
		int randomIndex = random.nextInt(names.size());
		return names.get(randomIndex);
	}

	public String getLastName() {
		int randomIndex = random.nextInt(surnames.size());
		return surnames.get(randomIndex);
	}

	public String getPassNum() {

		passNum = "U";
		int min = 10000000;
		int max = 99999999;
		int randomNumber = random.nextInt(max - min + 1) + min;
		passNum += randomNumber;

		return passNum;
	}

	public String getDate() {
		
		date = "";
		LocalDate start = LocalDate.of(2029, 1, 1);
		long days = ThreadLocalRandom.current().nextLong(365 * 3);
		LocalDate randomDate = start.plusDays(days);
		DateTimeFormatter formatter;
		formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		date = randomDate.format(formatter);
		
		return date;
	}
	
	public String getPhoneNum() {
		phoneNum = "5";
		int[] numbs = {0, 3, 4, 5};
        int min = 10000000;
        int max = 99999999;
        int randomIndex = random.nextInt(numbs.length);
        int secondDigit = numbs[randomIndex];
        phoneNum += secondDigit;
        phoneNum += random.nextInt(max - min + 1) + min;
        
        return phoneNum;
	}
	public String getMailAddress(WebDriver driver) {
		
		WebElement inputElement = driver.findElement(By.xpath("//input[@id='emailId']"));
		String value = inputElement.getAttribute("value");
		
		return value;
	}
}
