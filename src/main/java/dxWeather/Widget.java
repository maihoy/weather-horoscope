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


public class Widget extends JFrame {
    private final static Logger LOG = Logger.getLogger(Widget.class);
    private static final int TIMER_DELAY = 5000;
    private static final int SCROLL_AMOUNT = 25;
    String temperature;
    String weatherDescription;
    String location;
    String humidity;
    String maxTemp;
    String minTemp;

    private JLabel descriptionLabel;
    private JButton exitButton;
    private JLabel humidityLabel;
    private JLabel minTempLabel;
    private JLabel maxTempLabel;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel9;
    private JPanel jPanel4;
    private JLabel locationLabel;
    private JLabel tempLabel;
    private JLabel tempUnitLabel;
    private JTextArea horoscope;

    public void setLocationName(String locationNamePassed) {
        location = locationNamePassed;
    }

    public void setTemperature(String tempPassed) {
        temperature = tempPassed;
    }

    public void setWeatherDescription(String weatherDescriptionPassed) {
        weatherDescription = weatherDescriptionPassed;
    }

    public void setHumidity(String humidityPassed) {
        humidity = humidityPassed;
    }

    public void setMaxTemp(String maxTempPassed) {
        maxTemp = maxTempPassed;
    }

    public void setMinTemp(String minTempPassed) {
        minTemp = minTempPassed;
    }

    public void setUnit(String unitPassed) {
        tempUnitLabel.setText(unitPassed);
    }

    public void refresh() {
        tempLabel.setText(temperature);
        locationLabel.setText(location);
        descriptionLabel.setText(weatherDescription);
        jLabel6.setText(humidity);
        jLabel5.setText(maxTemp);
        jLabel7.setText(minTemp);
        horoscope.setText(Horoscope.horoscopeText);

    }

    public Widget() {
        initComponents();
    }

    private void initComponents() {

        jPanel4 = new JPanel();
        tempLabel = new JLabel();
        tempUnitLabel = new JLabel();
        locationLabel = new JLabel();
        descriptionLabel = new JLabel();
        humidityLabel = new JLabel();
        jLabel6 = new JLabel();
        maxTempLabel = new JLabel();
        jLabel5 = new JLabel();
        minTempLabel = new JLabel();
        jLabel7 = new JLabel();
        exitButton = new JButton();
        jLabel9 = new JLabel();
        horoscope = new JTextArea();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Widget");
        setFocusable(false);
        setLocation(new Point(1070, 0));
        setUndecorated(true);
        setResizable(false);
        setType(Window.Type.UTILITY);

        jPanel4.setLayout(null);

        tempLabel.setFont(new java.awt.Font("Courier 10 Pitch", 0, 48)); // NOI18N
        tempLabel.setText("69");
        jPanel4.add(tempLabel);
        tempLabel.setBounds(10, 0, 120, 60);

        tempUnitLabel.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        tempUnitLabel.setText("°C");
        jPanel4.add(tempUnitLabel);
        tempUnitLabel.setBounds(70, 0, 80, 40);

        locationLabel.setFont(new java.awt.Font("FreeSans", 0, 21)); // NOI18N
        jPanel4.add(locationLabel);
        locationLabel.setBounds(10, 50, 140, 30);

        descriptionLabel.setFont(new java.awt.Font("FreeSans", 0, 18)); // NOI18N
        jPanel4.add(descriptionLabel);
        descriptionLabel.setBounds(10, 80, 130, 23);

        horoscope.setText("");
        horoscope.setLineWrap(true);
        horoscope.setEditable(false);
        horoscope.setWrapStyleWord(true);
        horoscope.setAutoscrolls(true);
        JScrollPane scrollPane = new JScrollPane(horoscope);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        // scrollPane.getViewport().setPreferredSize(VIEWPORT_SIZE);

        // add(scrollPane);
        // *** extract the BoundedRangeModel from the vertical scroll bar
        BoundedRangeModel barModel = scrollPane.getVerticalScrollBar().getModel();
        new Timer(TIMER_DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timerActionPerformed(e, barModel);
            }
        }).start();

        jPanel4.add(scrollPane);
        scrollPane.setBounds(10, 110, 250, 55);

        humidityLabel.setText("Humidity :");
        jPanel4.add(humidityLabel);
        humidityLabel.setBounds(160, 30, 70, 18);

        jLabel6.setText("jLabel5");
        jPanel4.add(jLabel6);
        jLabel6.setBounds(240, 30, 49, 18);

        maxTempLabel.setText("Max Temp : ");
        jPanel4.add(maxTempLabel);
        maxTempLabel.setBounds(160, 50, 80, 18);

        jLabel5.setText("jLabel5");
        jPanel4.add(jLabel5);
        jLabel5.setBounds(240, 50, 49, 18);

        minTempLabel.setText("Min Temp :");
        jPanel4.add(minTempLabel);
        minTempLabel.setBounds(160, 70, 74, 18);

        jLabel7.setText("jLabel5");
        jPanel4.add(jLabel7);
        jLabel7.setBounds(240, 70, 49, 18);

        exitButton.setText("x");
        exitButton.addActionListener
                (new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        exitButtonActionPerformed(evt);
                    }
                });

        jPanel4.add(exitButton);
        exitButton.setBounds(270, 140, 30, 30);

        jLabel9.setIcon(new ImageIcon(getClass().getResource("/index.jpeg"))); // NOI18N
        jPanel4.add(jLabel9);
        jLabel9.setBounds(0, 0, 300, 170);

        GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane()
                .setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pack();
    }

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        LOG.info("Sucessfully closed!");
        System.exit(0);
    }

    private void timerActionPerformed(ActionEvent e, BoundedRangeModel barModel) {
        int newModelValue = barModel.getValue();  // Get the model's curent value
        if (newModelValue < barModel.getMaximum() - barModel.getExtent()) {  // if not at max
            newModelValue += SCROLL_AMOUNT; // add something to it
            barModel.setValue(newModelValue);  // and change the model's value
        } else {
            barModel.setValue(barModel.getMinimum());
        }
    }

    public void getNextHoroscope() {
        Horoscope.getInstance().parse();
    }
}
