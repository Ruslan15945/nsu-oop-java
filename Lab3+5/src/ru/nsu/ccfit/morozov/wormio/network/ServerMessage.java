package ru.nsu.ccfit.morozov.wormio.network;

import ru.nsu.ccfit.morozov.wormio.model.PlayerView;
import ru.nsu.ccfit.morozov.wormio.util.StreamUtil;

public abstract class ServerMessage {
    public abstract void writeTo(java.io.OutputStream stream) throws java.io.IOException;

    public static ServerMessage readFrom(java.io.InputStream stream) throws java.io.IOException {
        switch (StreamUtil.readInt(stream)) {
            case UpdateWorld.TAG:
                return UpdateWorld.readFrom(stream);
            //case Finish.TAG:
                //return Finish.readFrom(stream);
            default:
                throw new java.io.IOException("Unexpected tag value");
        }
    }

    public static class UpdateWorld extends ServerMessage {

        public static final int TAG = 0;

        private PlayerView playerView;

        public PlayerView getPlayerView() {
            return playerView;
        }

        public void setPlayerView(PlayerView playerView) {
            this.playerView = playerView;
        }

        public UpdateWorld(PlayerView playerView){
            this.playerView = playerView;
        }

        public static UpdateWorld readFrom(java.io.InputStream stream) throws java.io.IOException {
            PlayerView playerView = PlayerView.readFrom(stream);
            return new UpdateWorld(playerView);
        }

        @Override
        public void writeTo(java.io.OutputStream stream) throws java.io.IOException {
            StreamUtil.writeInt(stream, TAG);
            playerView.writeTo(stream);
            stream.flush();
        }
    }

}
