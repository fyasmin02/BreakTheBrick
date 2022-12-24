package FinalProject;


import java.nio.file.Paths;

import javax.swing.*;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class GameBackground extends Application{

	public static void main(String[]args) {

		// set jframe
		JFrame obj = new JFrame();

		// music
		Media media = new Media(Paths.get("newhome.mp3").toUri().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();

		BallGame ballgame = new BallGame();

		// jframe parameters
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Break the Brick");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(ballgame);

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}
}
