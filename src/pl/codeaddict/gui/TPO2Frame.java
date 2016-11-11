package pl.codeaddict.gui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import pl.codeaddict.service.Service;

import javax.swing.*;
import java.awt.*;


/**
 * Created by Michal Kostewicz on 22.10.16.
 */
public class TPO2Frame extends JFrame {
    private JLabel cityLabel;
    private JPanel cityInfoPanel;
    private JFormattedTextField cityValueTextArea;
    private Service service;
    private JLabel cityWeatherLabel;
    private JFormattedTextField cityWheaterValueTextArea;
    private JLabel countryCurrencyRateForProvidedCurrencyLabel;
    private JFormattedTextField countryCurrencyRateForProvidedCurrencyValueTextArea;
    private JLabel countryCurrencyRateForPLNLabel;
    private JFormattedTextField countryCurrencyRateForPLNValueTextArea;
    private String wikiPageUrl = "https://en.wikipedia.org/wiki/";

    public TPO2Frame(Service s) {
        super("TPO2");
        service = s;
        //this.debugService();  //debug
        this.setLayout(new BorderLayout());
        this.setSize(400, 400);
        prepareGUI();
        prepareWebComponent();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void prepareWebComponent() {
        JFXPanel jfxPanel = new JFXPanel();
        this.add(jfxPanel);

        Platform.runLater(() -> {
            WebView webView = new WebView();
            jfxPanel.setScene(new Scene(webView));
            webView.getEngine().load(wikiPageUrl + service.getCity());
        });
    }

    private void prepareGUI() {
        cityInfoPanel = new JPanel();
        cityInfoPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        cityLabel = new JLabel("City: ");
        cityLabel.setMaximumSize(new Dimension(50,10));
        cityLabel.setLabelFor(cityValueTextArea);
        cityValueTextArea = new JFormattedTextField(service.getCountry());
        cityValueTextArea.setEditable(false);
        cityWeatherLabel = new JLabel("Wheater in City");
        cityWheaterValueTextArea = new JFormattedTextField((service.getWeatherInfo(service.getCity())));
        cityWheaterValueTextArea.setEditable(false);
        countryCurrencyRateForProvidedCurrencyLabel = new JLabel("Country currency rate for provided currency: ");
        countryCurrencyRateForProvidedCurrencyValueTextArea = new JFormattedTextField("" + service.getRateFor("USD"));
        countryCurrencyRateForProvidedCurrencyValueTextArea.setEditable(false);
        countryCurrencyRateForPLNLabel = new JLabel("Country currency rate for PLN currency: ");
        countryCurrencyRateForPLNValueTextArea = new JFormattedTextField("" + service.getNBPRate());
        countryCurrencyRateForPLNValueTextArea.setEditable(false);

        cityInfoPanel.add(cityLabel,c);
        c.gridx = 1;
        c.weightx = 0.7;
        cityInfoPanel.add(cityValueTextArea,c);
        c.gridy = 1;
        c.gridx = 0;
        cityInfoPanel.add(cityWeatherLabel,c);
        c.gridx = 1;
        c.weightx = 0.7;
        cityInfoPanel.add(cityWheaterValueTextArea,c);
        c.gridy = 2;
        c.gridx = 0;
        cityInfoPanel.add(countryCurrencyRateForProvidedCurrencyLabel,c);
        c.gridx = 1;
        c.weightx = 0.7;
        cityInfoPanel.add(countryCurrencyRateForProvidedCurrencyValueTextArea,c);
        c.gridy = 3;
        c.gridx = 0;
        cityInfoPanel.add(countryCurrencyRateForPLNLabel,c);
        c.gridx = 1;
        c.weightx = 0.7;
        cityInfoPanel.add(countryCurrencyRateForPLNValueTextArea,c);
        this.add(cityInfoPanel, BorderLayout.NORTH);
    }

    private void debugService(){
        System.out.println("Weather: " + service.getWeather("warsaw"));
        System.out.println("Currency(PLN) rate for USD: " + service.getRateFor("USD"));
        System.out.println("NBP rate for PLN: " + service.getNBPRate());
    }
}


