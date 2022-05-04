package ru.nsu.ccfit.morozov.wormio.util;

import ru.nsu.ccfit.morozov.wormio.model.PlayerView;

public interface Observer {
    public void update();
    public void finalUpdate();
}
