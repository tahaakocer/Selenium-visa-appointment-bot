package example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class Selenium {
	public static boolean next = false;
	public static boolean autoGet = false;
	public static boolean running = false;
	public static Integer numOfSpan = 0;
	private static LocalTime time;
	private final static String port = "localhost:9222";
	private static String infoXpath = "/html/body/app-root/app-appointment-layout/html/body/div/div/div/div[2]/app-appointment-slots/div/form/div[2]/div/div/form/div/h2";
	private static String buttonXpath = "/html/body/app-root/app-appointment-layout/html/body/div/div/div/div[2]/app-appointment-slots/div/form/div[2]/div/div/div/p-calendar/span/div/div/div[2]/table/tbody/tr[%d]/td[%d]/a";
	private static String nextMonthXpath = "/html/body/app-root/app-appointment-layout/html/body/div/div/div/div[2]/app-appointment-slots/div/form/div[2]/div/div/div/p-calendar/span/div/div/div[1]/a[2]";
	private static String locationXpath = "//p-dropdown[@id='location']/div";
	private static String istanbulXpath = "//li[@aria-label='Istanbul']";
	private static String numOfAppXpath = "//p-dropdown[@id='noOfApplicants']/div/div[3]/span";
	private static String appTimeXpath = "//span[@class='ui-button-text ui-unselectable-text']";
	private static String appNextXpath = "//span[@class='ui-button-text ui-clickable']";

	private static String nationalityXpath = "//p-dropdown[@id='nationality%d']/div/div[3]/span";
	private static String turkeyXpath = "//li[@aria-label='Turkey']";
	private static String lastNameXpath = "//input[@id='lastName%d']";
	private static String firstNameXpath = "//input[@id='firstAndMiddleName%d']";
	private static String passNoXpath = "//input[@id='passportNumber%d']";
	private static String dateXpath = "//p-calendar[@id='passportExpiryDate%d']/span/input";
	private static String typeXpath = "//p-dropdown[@id='visaType%d']/div/div[3]/span";
	private static String businessXpath = "//li[@aria-label='Business']";
	private static String phoneNumXpath = "//input[@id='contactNo.%d']";
	private static String mailAddressXpath = "//input[@id='emailAddress%d']";
	private static String saveXpath = "//div[@id='ui-accordiontab-0-content']//span[@class='ui-button-text ui-clickable'][normalize-space()='Save']";
	// ----------------------------------------------------------0 1 2 3 4 diye
	// gidiyor
	private static String proceedXpath = "//span[normalize-space()='Proceed']";

	private static ChromeOptions options;
	public static WebDriver driver;
	private static ChromeDriverService service;
	private static String mailAddress = "taha.kocer317@gmail.com";
	final private static String cmd = "cmd.exe";
	final private static String parameter = "/c";
	final private static String commandRunBrowser = "chrome.exe -remote-debugging-port=9222 --user-data-dir=\"C:\\Selenium\\Chrome_Test_Profile\"";
	final private static String commandPriority1 = "wmic process where name=\"chrome.exe\" CALL setpriority realtime";
	final private static String commandPriority2 = "wmic process where name=\"chromedriver.exe\" CALL setpriority realtime";
	final private static String commandTaskKill = "Taskkill /f /im \"chromedriver.exe\"";

	public Selenium() {
		service = new ChromeDriverService.Builder().withLogOutput(System.out).build();
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
		options = new ChromeOptions();
		options.setExperimentalOption("debuggerAddress", port); // açık pencere portu
		options.addArguments("--remote-allow-origins=*"); // popuplar ile ilgili
		options.addArguments("--priority=high");
	}

	public static void startDriver() {
		driver = new ChromeDriver(service, options);
		System.out.println("Driver baslatildi.");
	}

	public static String targetXpath(int x, int y) {
		String format = String.format(buttonXpath, x, y);
		return format;
	}

	public static void runBrowser() {
		ProcessBuilder debuggerBuilder;
		Process process;
		try {
			debuggerBuilder = new ProcessBuilder(cmd, parameter, commandRunBrowser);
			process = debuggerBuilder.start();
			System.out.println("chrome.exe debugger mod'ta baslatildi. port=" + port);
			Thread.sleep(2000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setPriority() {
		ProcessBuilder priorityBuilder1, priorityBuilder2;
		Process process;
		priorityBuilder1 = new ProcessBuilder(cmd, parameter, commandPriority1);
		try {
			process = priorityBuilder1.start();
			Thread.sleep(2000);
			priorityBuilder2 = new ProcessBuilder(cmd, parameter, commandPriority2);
			process = priorityBuilder2.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Priorty is set.");
	}

	public static void taskKill() {
		ProcessBuilder processBuilder;
		Process process;
		try {
			processBuilder = new ProcessBuilder(cmd, parameter, commandTaskKill);
			process = processBuilder.start();
			System.out.println("Tum \"chromedriver.exe\" yazilimlari durduruldu.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void fillFirstPage(Integer selectedDay) {
		Duration duration = Duration.ofSeconds(15);
		WebDriverWait wait = new WebDriverWait(driver, duration);

		WebElement travelAgency = wait.until(ExpectedConditions.elementToBeClickable(By.id("travelAgency")));

		WebElement locationElement = driver.findElement(By.xpath(locationXpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", locationElement);
		WebElement istanbulElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(istanbulXpath)));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", istanbulElement);

		WebElement numOfappElement = driver.findElement(By.xpath(numOfAppXpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", numOfappElement);
		String numXpath = "//li[@aria-label='" + selectedDay + "']";
		System.out.println(numXpath);
		WebElement numElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(numXpath)));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", numElement);

		travelAgency.sendKeys("Atadem");
		((JavascriptExecutor) driver).executeScript("document.getElementById('travelAgency').value='Atadem';");

		WebElement mailElement = driver.findElement(By.id("emailId"));
		mailElement.sendKeys(mailAddress);
		((JavascriptExecutor) driver).executeScript("document.getElementById('emailId').value='" + mailAddress + "';");

		WebElement confirmElement = driver.findElement(By.id("confirmEmailId"));
		confirmElement.sendKeys(mailAddress);
		((JavascriptExecutor) driver)
				.executeScript("document.getElementById('confirmEmailId').value='" + mailAddress + "';");

		System.out.println("Giris sayfasi dolduruldu.");
	}

	public static void getAppointment(Integer count) {

		List<WebElement> spanElements = driver.findElements(By.xpath(appTimeXpath));
		numOfSpan = spanElements.size();
		try {
			for (int i = 0; i < count; i++) {
				Thread.sleep(50);
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", spanElements.get(i));
			}
			Thread.sleep(500);
			WebElement nextButtonElement = driver.findElement(By.xpath(appNextXpath));
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", nextButtonElement);

			System.out.println("Otomatik randevu alindi.");
		} catch (InterruptedException e) {
			e.printStackTrace();
			getAppointment(count);

		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			getAppointment(count);

		} catch (NoSuchElementException e) {
			e.printStackTrace();
			getAppointment(count);

		}

	}

	public static void fillAppointment(Integer count) {

		Duration duration = Duration.ofSeconds(15);
		WebDriverWait waitt = new WebDriverWait(driver, duration);
		waitt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(saveXpath, 0))));

		List<WebElement> nationElements = new ArrayList<WebElement>();
		WebElement turkeyElement; // p-dropdown'ı açtıktan sonra
		WebElement typeElement; // p-dropdown'ı açtıktan sonra
		List<WebElement> lastNameElements = new ArrayList<WebElement>();
		List<WebElement> firstNameElements = new ArrayList<WebElement>();
		List<WebElement> passNoElements = new ArrayList<WebElement>();
		List<WebElement> dateElements = new ArrayList<WebElement>();
		List<WebElement> typeElements = new ArrayList<WebElement>();
		List<WebElement> phoneNumElements = new ArrayList<WebElement>();
		List<WebElement> mailAddressElements = new ArrayList<WebElement>();
		List<WebElement> saveElements = new ArrayList<WebElement>();
		List<Person> costumers = new ArrayList<Person>();

		try {
			Thread.sleep(6000);
			System.out.println("Form doldurma islemi baslatildi.");
			System.out.println("5 saniye bekletilecek");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int say = 0; say < count; say++) {
			costumers.add(new Person());
		}

		for (int i = 1; i < count + 1; i++) {
			String formattedNationXpath = String.format(nationalityXpath, i);
			System.out.println(formattedNationXpath);
			nationElements.add(driver.findElement(By.xpath(formattedNationXpath)));
		}

		for (int j = 1; j < count + 1; j++) {
			String formattedLastNameXpath = String.format(lastNameXpath, j);
			System.out.println(formattedLastNameXpath);
			lastNameElements.add(driver.findElement(By.xpath(formattedLastNameXpath)));
		}

		for (int k = 1; k < count + 1; k++) {
			String formattedFirstNameXpath = String.format(firstNameXpath, k);
			System.out.println(formattedFirstNameXpath);
			firstNameElements.add(driver.findElement(By.xpath(formattedFirstNameXpath)));
		}

		for (int l = 1; l < count + 1; l++) {
			String formattedPassNoXpath = String.format(passNoXpath, l);
			System.out.println(formattedPassNoXpath);
			passNoElements.add(driver.findElement(By.xpath(formattedPassNoXpath)));
		}

		for (int m = 1; m < count + 1; m++) {
			String formattedDateXpath = String.format(dateXpath, m);
			System.out.println(formattedDateXpath);
			dateElements.add(driver.findElement(By.xpath(formattedDateXpath)));
		}

		for (int n = 1; n < count + 1; n++) {
			String formattedTypeXpath = String.format(typeXpath, n);
			System.out.println(formattedTypeXpath);
			typeElements.add(driver.findElement(By.xpath(formattedTypeXpath)));
		}

		for (int o = 1; o < count + 1; o++) {
			String formattedPhoneNumXpath = String.format(phoneNumXpath, o);
			System.out.println(formattedPhoneNumXpath);
			phoneNumElements.add(driver.findElement(By.xpath(formattedPhoneNumXpath)));
		}

		for (int p = 1; p < count + 1; p++) {
			String formattedMailAddressXpath = String.format(mailAddressXpath, p);
			System.out.println(formattedMailAddressXpath);
			mailAddressElements.add(driver.findElement(By.xpath(formattedMailAddressXpath)));
		}
		for (int r = 0; r < count; r++) {
			String formattedSaveXpath = String.format(saveXpath, r);
			System.out.println(formattedSaveXpath);
			saveElements.add(driver.findElement(By.xpath(formattedSaveXpath)));
		}

		try {

			Thread.sleep(3000);

			for (int f = 0; f < count; f++) {

				((JavascriptExecutor) driver).executeScript("arguments[0].click()", nationElements.get(f));
				turkeyElement = waitt.until(ExpectedConditions.elementToBeClickable(By.xpath(turkeyXpath)));
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", turkeyElement);
				Thread.sleep(500);
				lastNameElements.get(f).sendKeys(costumers.get(f).getLastName());
				((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];",
						lastNameElements.get(f), costumers.get(f).getLastName());
				Thread.sleep(500);
				firstNameElements.get(f).sendKeys(costumers.get(f).getName());
				((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];",
						firstNameElements.get(f), costumers.get(f).getName());
				Thread.sleep(500);
				passNoElements.get(f).sendKeys(costumers.get(f).getPassNum());
				((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", passNoElements.get(f),
						costumers.get(f).getPassNum());
				Thread.sleep(500);
				dateElements.get(f).sendKeys(costumers.get(f).getDate());
				((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", dateElements.get(f),
						costumers.get(f).getDate());
				Thread.sleep(500);
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", typeElements.get(f));
				typeElement = waitt.until(ExpectedConditions.elementToBeClickable(By.xpath(businessXpath)));
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", typeElement);
				Thread.sleep(500);
				phoneNumElements.get(f).sendKeys(costumers.get(f).getPhoneNum());
				((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];",
						phoneNumElements.get(f), costumers.get(f).getPhoneNum());
				Thread.sleep(500);
				mailAddressElements.get(f).sendKeys(costumers.get(f).getMailAddress(driver));
				((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];",
						mailAddressElements.get(f), costumers.get(f).getMailAddress(driver));

				Thread.sleep(2500);
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", saveElements.get(f));

				Thread.sleep(3000);
				System.out.println("2.5 saniye bekletilecek");

			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void Sound() throws LineUnavailableException {
		float frequency = 240; // 440 Hz
		int durationMs = 500; // 1 saniye
		byte[] buffer = new byte[durationMs * 8 * 2]; // PCM verileri için buffer boyutunu hesaplar

		for (int i = 0; i < buffer.length; i++) {
			double angle = i / (8000.0 / frequency) * 2.0 * Math.PI;
			short amplitude = (short) (Math.sin(angle) * 32767);
			buffer[i++] = (byte) (amplitude & 0xFF);
			buffer[i] = (byte) (amplitude >> 8);
		}

		AudioFormat format = new AudioFormat(8000, 16, 1, true, true);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

		line.open(format);
		line.start();
		line.write(buffer, 0, buffer.length);
		line.drain();
		line.close();
	}

	public static void startBot(final String selectedDay, final JLabel label, final Integer countOfApp)
			throws InterruptedException {

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				options.addArguments("--headless");
				Duration duration2 = Duration.ofSeconds(25);
				WebDriverWait wait2;
				WebElement button, nextMonth;
				Integer x, y;
				String formattedXpath;
				x = DayTable.getColumnNumber(driver, selectedDay);
				y = DayTable.getRowNumber(driver, selectedDay);
				try {
					Integer sayac = 0;
					while (running) {
						if (sayac % 40 == 0) {
							setPriority();
						}
						if (next == true) {
							wait2 = new WebDriverWait(driver, duration2);
							nextMonth = wait2
									.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nextMonthXpath)));
							((JavascriptExecutor) driver).executeScript("arguments[0].click()", nextMonth);
							Thread.sleep(1000);
						}
						formattedXpath = String.format(buttonXpath, y, x);
						wait2 = new WebDriverWait(driver, duration2);
						button = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(formattedXpath)));
						time = LocalTime.now();
						System.out.println(time + " sayfa yuklendi");
						label.setText("<html>Sayfa yenilendi.</html>");

						((JavascriptExecutor) driver).executeScript("arguments[0].click()", button);
						System.out.println("Butona tıklandı ve 3 saniye bekletilecek");
						label.setText("<html>Kontrol ediliyor..</html>a");
						Thread.sleep(3000);

						if (!driver.findElements(By.xpath(infoXpath)).isEmpty()) {
							System.out.println("XPath bulundu.");
							label.setText("<html>Randevu Yok.<br>Sayfa yenileniyor..</html>");
							driver.navigate().refresh();
							System.out.println("Sayfa yenileniyor..");
							driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(17));
							sayac++;

						} else if (!driver.findElements(By.xpath(appTimeXpath)).isEmpty()) {

							System.out.println("XPath bulunamadı.");
							label.setText("<html><b>Randevu bulundu!!</></html>");

							Selenium.Sound();

							if (autoGet == true) {
								Selenium.getAppointment(countOfApp);
							}
							running = false;
							MailSender.sendMail(selectedDay);
							Thread.sleep(1000);
							// Selenium.fillAppointment(countOfApp);

						}
					}
				} catch (NoSuchElementException e) {
					e.printStackTrace();
					Selenium.startBot(selectedDay, label, countOfApp);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
					Selenium.startBot(selectedDay, label, countOfApp);
				}
				return null;
			}

			@Override
			protected void done() {
				
			}
		};
		worker.execute();

	}
}
