/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dxWeather;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;


public class Dashboard extends JFrame {

    private final static Logger LOG = Logger.getLogger(Dashboard.class);

    private JTextField apiKey;
    private JButton goButton;
    private JLabel title;
    private JLabel mapApiKey;
    private JLabel celsius;

    Widget w = new Widget();


    public Dashboard() {
        initComponents();
    }


    private void initComponents() {

        title = new JLabel();
        mapApiKey = new JLabel();
        apiKey = new JTextField();
        goButton = new JButton();

        celsius = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setType(Window.Type.UTILITY);

        mapApiKey.setText("OpenWeatherMap API Key");
        apiKey.setText("6c08c0cdb135cfa3bc45f667bd2ab819");
        apiKey.setEditable(false);
        goButton.setText("GO!");

        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                goButtonAction(evt);
            }
        });

        celsius.setText("Celcius");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(apiKey, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(title, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(107, 107, 107)
                                                .addComponent(goButton, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(46, 46, 46)
                                                .addComponent(mapApiKey)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(title)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGap(18, 18, 18)
                                .addComponent(mapApiKey)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(apiKey, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(goButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void goButtonAction(ActionEvent evt) {

        final CurrentWeather weatherObj = new CurrentWeather();
        String appKey = apiKey.getText();
        weatherObj.setApiKey(appKey);
        weatherObj.setUnit();
        weatherObj.finalize();

        IpUtils.getInstance().getIPInfo();

        boolean isSuccessfulInConnecting = weatherObj.getData();

        if (isSuccessfulInConnecting) {
            LOG.info("Successfully downloaded DATA!");

            String weatherData[] = weatherObj.getAllData();

            w.setTemperature(weatherData[2]);
            w.setUnit("°" + weatherObj.tempUnit);
            w.setWeatherDescription(weatherData[1]);
            w.setLocationName(weatherData[0]);
            w.setHumidity(weatherData[3]);
            w.setMinTemp(weatherData[4]);
            w.setMaxTemp(weatherData[5]);
            w.setVisible(true);
            w.refresh();

            dispose();
        } else {
            LOG.info("Failed to download data. Check your network connection or config");
        }

        LOG.info("Re downloading in 15 seconds");

        Timer timer = new Timer();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                boolean isSuccessfulInConnecting2 = weatherObj.getData();
                if (isSuccessfulInConnecting2) {
                    LOG.info("Successfully downloaded DATA!");
                    String weatherData[] = weatherObj.getAllData();
                    w.setTemperature(weatherData[2]);
                    w.setWeatherDescription(weatherData[1]);
                    w.setLocationName(weatherData[0]);
                    w.setHumidity(weatherData[3]);
                    w.setMinTemp(weatherData[4]);
                    w.setMaxTemp(weatherData[5]);
                    w.setVisible(true);
                    w.refresh();
                } else {
                    LOG.error("Failed to download data. Check your network connection or config");
                }

                LOG.info("Re downloading in 15 seconds");

            }
        };

        timer.schedule(myTask, 15000, 15000);

    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException ex) {
            LOG.error("",ex);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }


}
