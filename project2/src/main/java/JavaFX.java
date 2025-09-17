import javafx.animation.Animation;
import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.RotateTransition;
import javafx.scene.layout.*;
import javafx.util.Duration;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import weather.Period;
import weather.Properties;
import weather.WeatherAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JavaFX extends Application {
	Text temperature, weather, precipitation, date, time, dayNum, nightNum, precipitation1,
			precipitation2, temp1, temp2, windSpeed, windDirection, detailedWeather;
	Button threeDay, fiveDay, sevenDay, moreInfo, backButton3, backButton5, backButton7,
	backButtonMoreInfo;
	HBox temperatureHBox, weatherHBox, moreInfoHBox, daysHBox;
	VBox v1, descriptionVBox;
	Region space1, space2, space3, space4, space5, space6;


	public static void main(String[] args) {

		launch(args);
	}
	private void dateAndTimeText() {
		Date dateNow = new Date();
		// date text
		date = new Text();
		date.setStyle("-fx-background-color: transparent; -fx-font-size: 22px;");

		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
		date.setText(dateFormatter.format(dateNow));

		// time text
		time = new Text();
		time.setStyle("-fx-background-color: transparent; -fx-font-size: 22px;");

		SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
		time.setText(timeFormatter.format(dateNow));
	}
	private void backButtonAction(Button backButton, Stage primaryStage, Scene scene) {
		backButton.setOnAction(e-> {primaryStage.setScene(scene);});
	}
	private void addNewlines(ArrayList<Period> forecast, int splitNum, Text newString, String orgString) {
		String format = "";

		String[] words = orgString.split(" ");
		int wordCount = 0;

		for (String word : words) {
			format += word + " ";
			wordCount++;
			if (wordCount % splitNum == 0) {
				format += "\n";
			}
		}
		newString.setText(format);
	}

	private void daysSetup(Period period1, Period period2, int fontSize) {
		dayNum = new Text(period1.name);
		nightNum = new Text(period2.name);

		temp1 = new Text(String.valueOf(period1.temperature + "° "
				+ period1.temperatureUnit));
		precipitation1 = new Text("Probability of \nPrecipitation:\n" +
				String.valueOf(period1.probabilityOfPrecipitation.value) + "%");
		temp2 = new Text(String.valueOf(period2.temperature + "° "
				+ period2.temperatureUnit));
		precipitation2 = new Text("Probability of \nPrecipitation:\n" +
				String.valueOf(period2.probabilityOfPrecipitation.value) + "%");

		temp1.setStyle("-fx-background-color: transparent; -fx-font-size: "
				+ (fontSize + 2) + "px;");
		temp2.setStyle("-fx-background-color: transparent; -fx-font-size: "
				+ (fontSize + 2) + "px;");
		precipitation1.setStyle("-fx-font-size:" + fontSize + "px;");
		precipitation2.setStyle("-fx-font-size:" + fontSize + "px;");
		dayNum.setStyle("-fx-font-size:" + fontSize + "px;");
		nightNum.setStyle("-fx-font-size:" + fontSize + "px;");
	}
	private void generateDaysForecast(VBox days, ArrayList<Period> forecast, int fontSize, int i) {
		Period period1 = forecast.get(i*2);
		Period period2 = forecast.get(i*2+1);

		daysSetup(period1, period2, fontSize);

		Separator daySeparator = new Separator();
		Separator nightSeparator = new Separator();
		Separator timeSeparator = new Separator();
		timeSeparator.setOrientation(Orientation.VERTICAL);
		timeSeparator.setPrefHeight(80);
		daySeparator.setPrefWidth(80);
		nightSeparator.setPrefWidth(80);

		VBox vBox1 = new VBox(5, dayNum, temp1, precipitation1, daySeparator);
		VBox vBox2 = new VBox(5, nightNum, temp2, precipitation2, nightSeparator);
		HBox hBox = new HBox(20, vBox1, timeSeparator, vBox2);
		days.getChildren().add(hBox);
	}
	private VBox numDaysForecast(ArrayList<Period> forecast, int numDays, int fontSize, VBox currentDayDetails) {
		VBox days = new VBox(10);

		if (numDays == 7) {
			for (int i = 0; i < 2; i++) {
				generateDaysForecast(currentDayDetails, forecast, fontSize, i);
			}
			for (int i = 2; i < 7; i++) {
				generateDaysForecast(days, forecast, fontSize, i);
			}
		}
		else {
			for (int i = 0; i < numDays; i++) {
				generateDaysForecast(days, forecast, fontSize, i);
			}
		}
		return days;
	}

	public void setMainText(ArrayList<Period> forecast) {
		// temperature text
		temperature = new Text();
		temperature.setStyle("-fx-background-color: transparent; -fx-font-size: 34px; ");
		temperature.setText(String.valueOf(forecast.get(0).temperature + "° "
		+ forecast.get(0).temperatureUnit));

		// weather text
		weather = new Text();
		weather.setStyle("-fx-background-color: transparent; -fx-font-size: 28px;");
		String shortForecast = String.valueOf(forecast.get(0).shortForecast);
		addNewlines(forecast, 3, weather, shortForecast);

		// precipitation text
		precipitation = new Text();
		precipitation.setStyle("-fx-font-size: 22px;");
		precipitation.setText("Probability of Precipitation: \n" +
				String.valueOf(forecast.get(0).probabilityOfPrecipitation.value) + "%");
	}
	public void setMainButtons(ArrayList<Period> forecast) {
		threeDay = new Button("3 Day Forecast");
		threeDay.setPadding(new Insets(10, 10, 10, 10));
		threeDay.setStyle("-fx-font-size: 18px; -fx-background-color: silver");

		fiveDay = new Button("5 Day Forecast");
		fiveDay.setPadding(new Insets(10, 10, 10, 10));
		fiveDay.setStyle("-fx-font-size: 18px; -fx-background-color: silver");

		sevenDay = new Button("7 Day Forecast");
		sevenDay.setPadding(new Insets(10, 10, 10, 10));
		sevenDay.setStyle("-fx-font-size: 18px; -fx-background-color: silver");

		moreInfo = new Button("More Information");
		moreInfo.setPadding(new Insets(10, 10, 10, 10));
		moreInfo.setStyle("-fx-font-size: 18px; -fx-background-color: silver");
	}
	public void setMainSpacing() {
		space1 = new Region();
		space2 = new Region();
		space3 = new Region();
		space4 = new Region();
		space5 = new Region();
		space6 = new Region();

		space1.setPrefWidth(30);
		space2.setPrefWidth(40);
		space3.setPrefWidth(40);
		space4.setPrefHeight(200);
		space5.setPrefWidth(120);
		space6.setPrefWidth(60);
	}
	public void setMainButtonActions(Stage primaryStage, ArrayList<Period> forecast, Scene scene) {
		// 3 day
		Scene scene2 = getDaysScene(primaryStage, forecast, scene, 3,22, backButton3);
		threeDay.setOnAction(e-> {primaryStage.setScene(scene2);});

		// 5 day
		Scene scene3 = getDaysScene(primaryStage, forecast, scene, 5, 16, backButton5);
		fiveDay.setOnAction(e-> {primaryStage.setScene(scene3);});

		// 7 day
		Scene scene4 = getDaysScene(primaryStage, forecast, scene, 7, 15, backButton7);
		sevenDay.setOnAction(e-> {primaryStage.setScene(scene4);});

		// More info
		Scene scene5 = moreInfoScene(primaryStage, forecast, scene);
		moreInfo.setOnAction(e-> {primaryStage.setScene(scene5);});
	}

	public ImageView getImage(ArrayList<Period> forecast, Boolean isCat) {
		String catFile, iconFile, file;
		int width;
		String weather = forecast.get(0).shortForecast.toLowerCase();
		if (weather.contains("rain") || weather.contains("showers")) {
			catFile = "rain.png";
			iconFile = "rainIcon.png";
		}
		else if (weather.contains("snow")) {
			catFile = "snow.png";
			iconFile = "snowIcon.png";
		}
		else if (weather.contains("sunny")) {
			catFile = "sunny.png";
			iconFile = "sunnyIcon.png";
		}
		else if (weather.contains("cloudy")) {
			catFile = "cloudy.png";
			iconFile = "cloudyIcon.png";
		}
		else if (weather.contains("wind") || weather.contains("windy") ||
				weather.contains("breezy") || weather.contains("strong")) {
			catFile = "windy.png";
			iconFile = "windyIcon.png";
		}
		else if (!forecast.get(0).isDaytime) {
			catFile = "night.png";
			iconFile = "nightIcon.png";
		}
		else {
			catFile = "clear.png";
			iconFile = "clearIcon.png";
		}

		if (isCat) {
			file = catFile;
			width = 350;
		}
		else {
			file = iconFile;
			width = 80;
		}
		Image image = new Image(file);
		ImageView pic = new ImageView();
		pic.setImage(image);
		pic.setFitWidth(width);
		pic.setPreserveRatio(true);
		pic.setSmooth(true);

		return pic;
	}
	public String getColor(ArrayList<Period> forecast) {
		String weather = forecast.get(0).shortForecast.toLowerCase();

		if (weather.contains("rain") || weather.contains("showers")) {
			return "Lightskyblue";
		}
		else if (weather.contains("snow")) {
			return "Ivory";
		}
		else if (weather.contains("sunny")) {
			return "Lemonchiffon";
		}
		else if (weather.contains("cloudy")) {
			return "Gainsboro";
		}
		else if (weather.contains("wind") || weather.contains("windy") ||
				weather.contains("breezy") || weather.contains("strong")) {
			return "Lightgrey";
		}
		else if (!forecast.get(0).isDaytime) {
			return "Grey";
		}
		else {
			return "Beige";
		}
	}

	private void paneSetup(ArrayList<Period> forecast, BorderPane pane, Button backButton, HBox hbox) {
		pane.setPadding(new Insets(20));
		pane.setCenter(hbox);
		pane.setBottom(backButton);
		backButton.setStyle("-fx-font-size: 18px; -fx-background-color: silver");

		String color = getColor(forecast);
		pane.setStyle("-fx-background-color: " + color + ";");
	}
	private void moreInfoTextSetup(ArrayList<Period> forecast) {
		temperature = new Text();
		temperature.setStyle("-fx-background-color: transparent; -fx-font-size: 34px; ");
		temperature.setText(String.valueOf(forecast.get(0).temperature + "° "
				+ forecast.get(0).temperatureUnit));

		windSpeed = new Text();
		windDirection = new Text();
		windSpeed.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; ");
		windDirection.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; ");
		windSpeed.setText("Today's wind speed is\n" +
				String.valueOf(forecast.get(0).windSpeed) + "\n\n");
		windDirection.setText("The direction of the wind is\n" +
				String.valueOf(forecast.get(0).windDirection + "\n\n"));

		detailedWeather = new Text();
		detailedWeather.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; ");
		detailedWeather.setText(String.valueOf(forecast.get(0).detailedForecast));
		String detailedForecast = String.valueOf(forecast.get(0).detailedForecast);
		addNewlines(forecast, 4, detailedWeather, detailedForecast);
	}

	public void mainPageScene(ArrayList<Period> forecast) {
		setMainText(forecast);
		dateAndTimeText();
		setMainButtons(forecast);
		setMainSpacing();
		ImageView cat = getImage(forecast, true);
		ImageView icon = getImage(forecast, false);

		descriptionVBox = new VBox(20, weather, precipitation, moreInfo);

		temperatureHBox = new HBox(20, icon, temperature, space5, time, space6, date);
		weatherHBox = new HBox(descriptionVBox, cat);
		daysHBox = new HBox(20, space1, threeDay, space2, fiveDay, space3, sevenDay);

		v1 = new VBox(20, temperatureHBox, weatherHBox, space4, daysHBox);
		v1.setPadding(new Insets(20, 0, 0, 0));
	}
	private Scene getDaysScene(Stage primaryStage, ArrayList<Period> forecast, Scene scene, int numDays, int fontSize, Button backButton) {
		temperature = new Text();
		temperature.setStyle("-fx-background-color: transparent; -fx-font-size: 34px; ");
		temperature.setText(String.valueOf(forecast.get(0).temperature + "° "
				+ forecast.get(0).temperatureUnit));

		dateAndTimeText();
		ImageView cat = getImage(forecast, true);
		ImageView icon = getImage(forecast, false);

		HBox timeDate = new HBox(20, time, date);
		HBox temp = new HBox(10, icon, temperature);
		VBox currentDayDetails = new VBox(20, timeDate, temp, cat);
		VBox numDayForecast = numDaysForecast(forecast, numDays, fontSize, currentDayDetails);
		HBox numDayScene = new HBox(currentDayDetails, numDayForecast);

		BorderPane pane = new BorderPane();
		paneSetup(forecast, pane, backButton, numDayScene);
		backButtonAction(backButton, primaryStage, scene);

		return new Scene(pane, 700,700);
	}
	private Scene moreInfoScene(Stage primaryStage, ArrayList<Period> forecast, Scene scene) {
		moreInfoTextSetup(forecast);

		dateAndTimeText();
		ImageView cat = getImage(forecast, true);
		cat.setRotate(-15);
		ImageView icon = getImage(forecast, false);

		RotateTransition rotate = new RotateTransition();
		rotate.setDuration(Duration.millis(1000));
		rotate.setNode(cat);
		rotate.setByAngle(45);
		rotate.setCycleCount(Animation.INDEFINITE);
		rotate.setAutoReverse(true);
		rotate.play();

		Image image = new Image("forecast.png");
		ImageView forecastPic = new ImageView();
		forecastPic.setImage(image);
		forecastPic.setFitWidth(280);
		forecastPic.setPreserveRatio(true);
		forecastPic.setSmooth(true);

		HBox timeDate = new HBox(20, time, date);
		HBox temp = new HBox(10, icon, temperature);
		VBox currentDayDetails = new VBox(20, timeDate, temp, cat);
		VBox info = new VBox(windSpeed, windDirection, detailedWeather, forecastPic);
		HBox infoScene = new HBox(currentDayDetails, info);
		//HBox infoScene = new VBox(infoDetails, forecastPic);

		BorderPane pane = new BorderPane();
		paneSetup(forecast, pane, backButtonMoreInfo, infoScene);
		backButtonAction(backButtonMoreInfo, primaryStage, scene);

		return new Scene(pane, 700,700);
	}
	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("The Weather");
		//int temp = WeatherAPI.getTodaysTemperature(77,70);
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}

		backButton3 = new Button("<- Back");
		backButton3.setPadding(new Insets(10, 10, 10, 10));
		backButton5 = new Button("<- Back");
		backButton5.setPadding(new Insets(10, 10, 10, 10));
		backButton7 = new Button("<- Back");
		backButton7.setPadding(new Insets(10, 10, 10, 10));
		backButtonMoreInfo = new Button("<- Back");
		backButtonMoreInfo.setPadding(new Insets(10, 10, 10, 10));

		mainPageScene(forecast);

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(20));
		pane.setCenter(v1);

		String color = getColor(forecast);
		pane.setStyle("-fx-background-color: " + color + ";");

		Scene scene = new Scene(pane, 700,700);

		setMainButtonActions(primaryStage, forecast, scene);

		primaryStage.resizableProperty().setValue(Boolean.FALSE);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
