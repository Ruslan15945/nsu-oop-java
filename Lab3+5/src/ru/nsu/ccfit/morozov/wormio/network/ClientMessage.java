package ru.nsu.ccfit.morozov.wormio.network;

import ru.nsu.ccfit.morozov.wormio.model.Action;
import ru.nsu.ccfit.morozov.wormio.util.StreamUtil;

public abstract class ClientMessage {

    public abstract void writeTo(java.io.OutputStream stream) throws java.io.IOException;


    public static ClientMessage readFrom(java.io.InputStream stream) throws java.io.IOException {
        switch (StreamUtil.readInt(stream)) {
            case ActionMessage.TAG:
                return ActionMessage.readFrom(stream);
            default:
                throw new java.io.IOException("Unexpected tag value");
        }
    }

    public static class ActionMessage extends ClientMessage {
        public static final int TAG = 1;
        private Action action;

        public Action getAction() {
            return action;
        }

        public void setAction(Action action) {
            this.action = action;
        }

        public ActionMessage(Action action){
            this.action = action;
        }


        public static ActionMessage readFrom(java.io.InputStream stream) throws java.io.IOException {
            Action action = Action.readFrom(stream);
            return new ActionMessage(action);
        }

        @Override
        public void writeTo(java.io.OutputStream stream) throws java.io.IOException {
            StreamUtil.writeInt(stream, TAG);
            action.writeTo(stream);
        }

    }
}
