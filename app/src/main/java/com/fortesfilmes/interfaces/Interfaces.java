package com.fortesfilmes.interfaces;

public class Interfaces {


    public interface OnClickAdapter {
        public void onClickAdapterLintener(Object arg0);
    }

    public interface OnPositiveButtonDialog {
        public void onPositiveListener(Object arg);
    }

    public interface OnNeutralButtonDialog {
        public void onNeutralListener(String arg0);
    }

    public interface OnNegativeButtonDialog {
        public void onNegativeListener();
    }

    public interface OnClickListener {
        public void onClickListenerCallback(Object arg);
    }
}
