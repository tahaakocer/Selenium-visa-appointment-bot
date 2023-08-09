package example;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DayTable {
	private static WebDriverWait webDriverWait;
    private static WebElement table;
    private static List<WebElement> satirlar; //BUNU DAHA SONRA CONST İÇERİSİNDE NEWLEMEYİ DENE DRİVER İLE BİRLİKTE
    private static List<WebElement> hucreler;
    
    public static int getColumnNumber(WebDriver driver, String arananDeger) {
    	webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        table = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
        satirlar = table.findElements(By.tagName("tr"));
        for (int i = 0; i < satirlar.size(); i++) {
            hucreler = satirlar.get(i).findElements(By.tagName("td"));
            for (int j = 0; j < hucreler.size(); j++) {
                String hucreMetni = hucreler.get(j).getText();
                if (hucreMetni.contains(arananDeger)) {
                    return j + 1;
                }
            }
        }
        return -1; // Aranan değer tabloda bulunamadıysa -1 döndürür.
    }
    
    public static int getRowNumber(WebDriver driver, String arananDeger) {
    	webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        table = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
        satirlar = table.findElements(By.tagName("tr"));
        for (int i = 0; i < satirlar.size(); i++) {
            hucreler = satirlar.get(i).findElements(By.tagName("td"));
            for (int j = 0; j < hucreler.size(); j++) {
                String hucreMetni = hucreler.get(j).getText();
                if (hucreMetni.contains(arananDeger)) {
                    return i;
                }
            }
        }
        return -1; // Aranan değer tabloda bulunamadıysa -1 döndürür.
    }
    // Tablo içinde aranan elementin sütununu bulan fonksiyon
    
   
}
