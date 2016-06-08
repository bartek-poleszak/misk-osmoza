package bp.misk.osmosis.desktop;

import bp.misk.osmosis.GameConfig;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import bp.misk.osmosis.MyGdxGame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DesktopLauncher {
	public static void main (String[] arg) {
        if (arg.length >= 2) {
            float left = Float.parseFloat(arg[0]);
            float right = Float.parseFloat(arg[1]);
            float holeDistance = 0.3f;
            if (arg.length >= 3)
                holeDistance = Float.parseFloat(arg[2]);
            runExperiment(left, right, holeDistance);
        }
        else
            showSetupWindow();
	}

    private static void runExperiment(float leftSalt, float rightSalt, float holeDistance) {
        System.out.print("" + leftSalt + " " + rightSalt + " " + holeDistance);
        GameConfig gameConfig = new GameConfig();
        gameConfig.leftSaltiness = leftSalt;
        gameConfig.rightSaltiness = rightSalt;
        gameConfig.holeDistance = holeDistance;
        gameConfig.dataVisualizer = new FileSaveDataVisualizer();
        gameConfig.frameSkip = 100;
        runSimulation(gameConfig);
    }

    private static void showSetupWindow() {
        final JFrame frame = new JFrame("Setup");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 3));
        panel.setPreferredSize(new Dimension(600, 200));

        final JLabel leftLabel = new JLabel("Zasolenie w lewej komorze:");
        final JLabel rightLabel = new JLabel("Zasolenie w prawej komorze:");
        final JLabel holeLabel = new JLabel("Odstęp między otworami");

        final JSlider leftSlider = new JSlider(0, 100);
        leftSlider.setValue(70);
        final JLabel leftValueLabel = new JLabel("" + (leftSlider.getValue() / 100f));
        leftValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                leftValueLabel.setText("" + (leftSlider.getValue() / 100f));
            }
        });

        final JSlider rightSlider = new JSlider(0, 100);
        rightSlider.setValue(40);
        final JLabel rightValueLabel = new JLabel("" + (rightSlider.getValue() / 100f));
        rightValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rightValueLabel.setText("" + (rightSlider.getValue() / 100f));
            }
        });

        final JSlider holeSlider = new JSlider(10, 500);
        holeSlider.setValue(30);
        final JLabel holeValueLabel = new JLabel("" + (holeSlider.getValue() / 100f));
        holeValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        holeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                holeValueLabel.setText("" + (holeSlider.getValue() / 100f));
            }
        });

        JButton button = new JButton("Start");
        final JTextField frameSkip = new JTextField("0");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameConfig gameConfig = new GameConfig();
                gameConfig.leftSaltiness = leftSlider.getValue() / 100f;
                gameConfig.rightSaltiness = rightSlider.getValue() / 100f;
                gameConfig.holeDistance = holeSlider.getValue() / 100f;
                gameConfig.dataVisualizer = new JFreeGraphDataVisualizer();
                gameConfig.frameSkip = Integer.parseInt(frameSkip.getText());

                runSimulation(gameConfig);
                frame.setVisible(false);
            }
        });



        panel.add(leftLabel);
        panel.add(leftSlider);
        panel.add(leftValueLabel);

        panel.add(rightLabel);
        panel.add(rightSlider);
        panel.add(rightValueLabel);

        panel.add(holeLabel);
        panel.add(holeSlider);
        panel.add(holeValueLabel);

        panel.add(new JLabel("Liczba pomijanych klatek"));
        panel.add(frameSkip);
        panel.add(button);

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static void runSimulation(GameConfig gameConfig) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1020;
        config.height = 630;
        config.foregroundFPS = 0;
        config.backgroundFPS = 0;
        config.fullscreen = false;
        config.forceExit = true;
        config.vSyncEnabled = false;

        MyGdxGame game = new MyGdxGame(gameConfig);
        new LwjglApplication(game, config);
    }
}
