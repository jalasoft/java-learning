package cz.jalasoft.oauth2.ws.client.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * @author lastovicka
 */
public final class WebLoginAuthenticator {

    private final WebDriver webDriver;

    public WebLoginAuthenticator() {
        System.setProperty("webdriver.chrome.driver", "/home/FIO_DOM/lastovicka/Tools/chromedriver_v71_73");
        webDriver = new ChromeDriver();
    }

    public void fillLoginAndSubmit(OAuth2RestTemplate restTemplate) {
        try {
            restTemplate.getAccessToken();
        } catch (UserRedirectRequiredException exc) {
            String url = buildRedirectionUrl(exc);
            openBrowserAndFill(url);
        }
    }

    private String buildRedirectionUrl(UserRedirectRequiredException e) {
       String redirectUri = e.getRedirectUri();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(redirectUri);
		Map<String, String> requestParams = e.getRequestParams();
		for (Map.Entry<String, String> param : requestParams.entrySet()) {
			builder.queryParam(param.getKey(), param.getValue());
		}

		if (e.getStateKey() != null) {
			builder.queryParam("state", e.getStateKey());
		}
		return builder.build().toUriString();
    }

    private void openBrowserAndFill(String url) {
        webDriver.get(url);
        new Select(webDriver.findElement(By.cssSelector(".selectUser"))).selectByValue("8000-01-01-00.00.00.000001");
        webDriver.findElement(By.cssSelector(".loginButton")).click();
        new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".confirmButton")));
        webDriver.findElement(By.cssSelector(".confirmButton")).click();
        webDriver.close();
    }
}
