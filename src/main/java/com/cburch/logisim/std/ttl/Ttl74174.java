/*
 * Logisim-evolution - digital logic design tool and simulator
 * Copyright by the Logisim-evolution developers
 *
 * https://github.com/logisim-evolution/
 *
 * This is free software released under GNU GPLv3 license
 */

package com.cburch.logisim.std.ttl;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.fpga.designrulecheck.netlistComponent;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import java.awt.Graphics;

/**
 * TTL 74x174 hex D-type flip-flops with asynchronous reset
 * Model based on < href="https://www.ti.com/lit/ds/symlink/sn74ls174.pdf">74LS174 datasheet</a>.
 */
public class Ttl74174 extends AbstractTtlGate {
  /**
   * Unique identifier of the tool, used as reference in project files. Do NOT change as it will
   * prevent project files from loading.
   *
   * <p>Identifier value must MUST be unique string among all tools.
   */
  public static final String _ID = "74174";

  public Ttl74174() {
    super(
        _ID,
        (byte) 16,
        new byte[] {2, 5, 7, 10, 12, 15},
        new String[] {
          "nCLR", "Q1", "D1", "D2", "Q2", "D3", "Q3", "CLK", "Q4", "D4", "Q5", "D5", "D6", "Q6"
        },
        null);
  }

  @Override
  public void paintInternal(InstancePainter painter, int x, int y, int height, boolean up) {
    final var g = painter.getGraphics();
    super.paintBase(painter, false, false);
    drawFlops(g, x, y, height);
  }

  @Override
  public void propagateTtl(InstanceState state) {
    var data = (TtlRegisterData) state.getData();
    if (data == null) {
      // changed = true;
      data = new TtlRegisterData(BitWidth.create(6));
      state.setData(data);
    }
    var triggered = data.updateClock(state.getPortValue(7));
    if (state.getPortValue(0) == Value.FALSE) {
      data.setValue(Value.createKnown(data.getWidth(), 0));
    } else if (triggered) {
      Value[] vals = data.getValue().getAll();
      vals[0] = state.getPortValue(2);
      vals[1] = state.getPortValue(3);
      vals[2] = state.getPortValue(5);
      vals[3] = state.getPortValue(9);
      vals[4] = state.getPortValue(11);
      vals[5] = state.getPortValue(12);
      data.setValue(Value.create(vals));
    }
    state.setPort(1, data.getValue().get(0), 8);
    state.setPort(4, data.getValue().get(1), 8);
    state.setPort(6, data.getValue().get(2), 8);
    state.setPort(8, data.getValue().get(3), 8);
    state.setPort(10, data.getValue().get(4), 8);
    state.setPort(13, data.getValue().get(5), 8);
  }

  private void drawFlops(Graphics g, int x, int y, int height) {
    // Reset line
    g.drawLine(x + 10, y + height - 10, x + 10, y + height - AbstractTtlGate.PIN_HEIGHT);
    g.drawLine(x + 10, y + height - 10, x + 140, y + height - 10);
    g.drawLine(x + 140, y + height - 10, x + 140, y + 10);
    g.drawLine(x + 40, y + 10, x + 140, y + 10);

    // Clock line
    g.drawLine(x + 150, y + AbstractTtlGate.PIN_HEIGHT, x + 150, y + 30);
    g.drawLine(x + 60, y + 30, x + 150, y + 30);

    Drawgates.paintFlipFlop(g, x + 40, y, false, false, true);
    Drawgates.paintOutputgate(g, x + 30, y, x + 37, y + 37, false, height);
    Drawgates.paintSingleInputgate(g, x + 50, y, x + 43, y + 37, false, height);
    // Reset
    g.drawLine(x + 40, y + 47, x + 40, y + 50);
    g.fillOval(x + 39, y + 49, 2, 2);
    // Clock
    g.drawLine(x + 43, y + 41, x + 60, y + 41);
    g.drawLine(x + 60, y + 30, x + 60, y + 40);

    Drawgates.paintFlipFlop(g, x + 80, y, false, false, false);
    Drawgates.paintOutputgate(g, x + 90, y, x + 83, y + 37, false, height);
    Drawgates.paintSingleInputgate(g, x + 70, y, x + 77, y + 37, false, height);
    // Reset
    g.drawLine(x + 80, y + 47, x + 80, y + 50);
    g.fillOval(x + 79, y + 49, 2, 2);
    // Clock
    g.drawLine(x + 60, y + 41, x + 77, y + 41);
    g.fillOval(x + 59, y + 40, 2, 2);

    Drawgates.paintFlipFlop(g, x + 120, y, false, false, false);
    Drawgates.paintOutputgate(g, x + 130, y, x + 123, y + 37, false, height);
    Drawgates.paintSingleInputgate(g, x + 110, y, x + 117, y + 37, false, height);
    // Reset
    g.drawLine(x + 120, y + 47, x + 120, y + 50);
    g.fillOval(x + 119, y + 49, 2, 2);
    // Clock
    g.drawLine(x + 100, y + 30, x + 100, y + 41);
    g.drawLine(x + 100, y + 41, x + 117, y + 41);
    g.fillOval(x + 99, y + 29, 2, 2);

    Drawgates.paintFlipFlop(g, x + 120, y, false, true, false);
    Drawgates.paintOutputgate(g, x + 130, y, x + 123, y + 23, true, height);
    Drawgates.paintSingleInputgate(g, x + 110, y, x + 117, y + 23, true, height);
    // Reset
    g.drawLine(x + 120, y + 13, x + 120, y + 10);
    g.fillOval(x + 119, y + 9, 2, 2);
    // Clock
    g.drawLine(x + 100, y + 19, x + 100, y + 30);
    g.drawLine(x + 100, y + 19, x + 117, y + 19);

    Drawgates.paintFlipFlop(g, x + 80, y, false, true, false);
    Drawgates.paintOutputgate(g, x + 90, y, x + 83, y + 23, true, height);
    Drawgates.paintSingleInputgate(g, x + 70, y, x + 77, y + 23, true, height);
    // Reset
    g.drawLine(x + 80, y + 13, x + 80, y + 10);
    g.fillOval(x + 79, y + 9, 2, 2);
    // Clock
    g.drawLine(x + 60, y + 19, x + 60, y + 30);
    g.drawLine(x + 60, y + 19, x + 77, y + 19);
    g.fillOval(x + 59, y + 29, 2, 2);

    Drawgates.paintFlipFlop(g, x + 40, y, false, true, true);
    Drawgates.paintOutputgate(g, x + 30, y, x + 37, y + 23, true, height);
    Drawgates.paintSingleInputgate(g, x + 50, y, x + 43, y + 23, true, height);
    // Reset
    g.drawLine(x + 40, y + 13, x + 40, y + 10);
    // Clock
    g.drawLine(x + 43, y + 19, x + 60, y + 19);
    g.fillOval(x + 59, y + 18, 2, 2);
}

  @Override
  public boolean checkForGatedClocks(netlistComponent comp) {
    return true;
  }

  @Override
  public int[] clockPinIndex(netlistComponent comp) {
    return new int[] {7};
  }
}
