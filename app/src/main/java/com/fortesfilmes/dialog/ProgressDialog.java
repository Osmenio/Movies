package com.fortesfilmes.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fortesfilmes.R;
import com.fortesfilmes.interfaces.Interfaces;


public class ProgressDialog {

    private Context context;

    private TextView negativeBtn;
    private TextView textDialog;

    private AlertDialog.Builder helpBuilder;
    private AlertDialog dialog;

    private Interfaces.OnNegativeButtonDialog onNegativListener = null;

    public ProgressDialog(Context context) {

        this.context = context;
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = inflater.inflate(R.layout.progress_dialog, null);

        helpBuilder = new AlertDialog.Builder(this.context);
        helpBuilder.setCancelable(false);
        helpBuilder.setView(inflatedView);
        //
        textDialog = (TextView) inflatedView.findViewById(R.id.tv_info);
        textDialog.setText("Info");
        negativeBtn = (TextView) inflatedView.findViewById(R.id.btn_cancel);
        negativeBtn.setVisibility(View.INVISIBLE);
    }

    public void show() {

        //
        dialog = helpBuilder.create();
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNegativListener != null) {
                    onNegativListener.onNegativeListener();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setOnNegativeButtonClickListener(Interfaces.OnNegativeButtonDialog listener) {
        this.onNegativListener = listener;
    }

    public void setTextDialog(String msg) {
        textDialog.setText(msg);
    }

    public void setLabelNegativeButton(String label) {
        negativeBtn.setText(label);
        negativeBtn.setVisibility(View.VISIBLE);
    }

    public void finish() {
        dialog.dismiss();
    }
}
