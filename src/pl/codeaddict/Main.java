/**
 *
 *  @author Kostewicz Michał S11474
 *
 */

package pl.codeaddict;


import pl.codeaddict.gui.TPO2Frame;
import pl.codeaddict.service.Service;

import java.awt.*;

public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();
    // ...
    // część uruchamiająca GUI
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        new TPO2Frame(s);
      }
    });
  }
}
