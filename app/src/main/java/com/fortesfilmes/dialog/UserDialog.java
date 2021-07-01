package com.fortesfilmes.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fortesfilmes.R;
import com.fortesfilmes.interfaces.Interfaces;


public class UserDialog {

    private Context context;

    private int dialogType = 0;
    private View imageView;
    private TextView positiveBtn;
    private TextView negativeBtn;

    private TextView titleDialog;
    private TextView textDialog;

    private AlertDialog.Builder helpBuilder;
    private AlertDialog dialog;

    private Interfaces.OnPositiveButtonDialog onPositiveListener = null;
    private Interfaces.OnNegativeButtonDialog onNegativListener = null;

    public UserDialog(Context context) {

        this.context = context;

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = inflater.inflate(R.layout.user_dialog, null);

        helpBuilder = new AlertDialog.Builder(this.context);
        helpBuilder.setCancelable(false);
        helpBuilder.setView(inflatedView);
        // Image
        imageView = (View) inflatedView.findViewById(R.id.view1);
        //
        titleDialog = (TextView) inflatedView.findViewById(R.id.dialog_title);
        titleDialog.setText("Alerta");
        //
        textDialog = (TextView) inflatedView.findViewById(R.id.dialog_message);
        positiveBtn = (TextView) inflatedView.findViewById(R.id.btn_ok);
        positiveBtn.setVisibility(View.INVISIBLE);
        negativeBtn = (TextView) inflatedView.findViewById(R.id.btn_cancel);
        negativeBtn.setVisibility(View.INVISIBLE);
    }

    public void show() {
        //
        dialog = helpBuilder.create();
        //
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPositiveListener != null) {
                    onPositiveListener.onPositiveListener(null);
                }
                dialog.dismiss();
            }
        });
        //
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNegativListener != null) {
                    onNegativListener.onNegativeListener();
                }
                dialog.dismiss();
            }
        });
        //
        dialog.show();
    }

    public void setOnPositiveButtonClickListener(Interfaces.OnPositiveButtonDialog listener) {
        this.onPositiveListener = listener;
    }

    public void setOnNegativeButtonClickListener(Interfaces.OnNegativeButtonDialog listener) {
        this.onNegativListener = listener;
    }

    public void setDialogType(int type) {
        dialogType = type;
    }

    public void setTitleDialog(String msg) {
        titleDialog.setText(msg);
    }

    public void setTextDialog(String msg) {
        textDialog.setText(msg);
    }

    public void setLabelPositiveButton(String label) {
        positiveBtn.setText(label);
        positiveBtn.setVisibility(View.VISIBLE);
    }

    public void setLabelNegativeButton(String label) {
        negativeBtn.setText(label);
        negativeBtn.setVisibility(View.VISIBLE);
    }

    public void finish() {
        dialog.dismiss();
    }
}
