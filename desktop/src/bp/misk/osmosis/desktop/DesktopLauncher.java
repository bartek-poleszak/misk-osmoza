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
        showSetupWindow();
	}

    private static void showSetupWindow() {
        final JFrame frame = new JFrame("Setup");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        panel.setPreferredSize(new Dimension(400, 200));

        final JLabel leftLabel = new JLabel("Left saltiness:");
        final JLabel rightLabel = new JLabel("Right saltiness:");
        final JLabel emptyLabel = new JLabel();

        final JSlider leftSlider = new JSlider(0, 100);
        final JLabel leftValueLabel = new JLabel("" + (leftSlider.getValue() / 100f));
        leftValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                leftValueLabel.setText("" + (leftSlider.getValue() / 100f));
            }
        });

        final JSlider rightSlider = new JSlider(0, 100);
        final JLabel rightValueLabel = new JLabel("" + (rightSlider.getValue() / 100f));
        rightValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rightValueLabel.setText("" + (rightSlider.getValue() / 100f));
            }
        });

        JButton button = new JButton("Start");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameConfig gameConfig = new GameConfig();
                gameConfig.leftSaltiness = leftSlider.getValue() / 100f;
                gameConfig.rightSaltiness = rightSlider.getValue() / 100f;
                gameConfig.dataVisualizer = new JFreeGraphDataVisualizer();

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

        panel.add(emptyLabel);
        panel.add(emptyLabel);
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
        config.vSyncEnabled = false;

		new LwjglApplication(new MyGdxGame(gameConfig), config);
    }
}
