package com.fortesfilmes.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fortesfilmes.R;
import com.fortesfilmes.enumeration.SortEnum;
import com.fortesfilmes.interfaces.Interfaces;

public class SortDialog {

    private Context context;

    private CheckBox cbTitleAsc;
    private CheckBox cbTitleDesc;
    private CheckBox cbRatingDesc;
    private CheckBox cbRatingAsc;
    private TextView btnApply;

    private AlertDialog.Builder helpBuilder;
    private AlertDialog dialog;

    private Interfaces.OnClickListener onClickListener = null;
    private SortEnum sortEnum = SortEnum.NONE;

    public SortDialog(Context context) {

        this.context = context;
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = inflater.inflate(R.layout.sort_dialog, null);

        helpBuilder = new AlertDialog.Builder(this.context);
        helpBuilder.setCancelable(false);
        helpBuilder.setView(inflatedView);
        //
        cbTitleAsc = (CheckBox) inflatedView.findViewById(R.id.cb_title_asc);
        cbTitleDesc = (CheckBox) inflatedView.findViewById(R.id.cb_title_desc);
        cbRatingDesc = (CheckBox) inflatedView.findViewById(R.id.cb_rating_desc);
        cbRatingAsc = (CheckBox) inflatedView.findViewById(R.id.cb_rating_asc);
        btnApply = (TextView) inflatedView.findViewById(R.id.btn_apply);
    }

    public void show() {

        //
        if (sortEnum == SortEnum.TITLE_ASC) {
            cbTitleAsc.setChecked(true);
        } else if (sortEnum == SortEnum.TITLE_DESC) {
            cbTitleDesc.setChecked(true);
        } else if (sortEnum == SortEnum.RATING_DESC) {
            cbRatingDesc.setChecked(true);
        } else if (sortEnum == SortEnum.RATING_ASC) {
            cbRatingAsc.setChecked(true);
        }

        //
        dialog = helpBuilder.create();
        cbTitleAsc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbTitleDesc.setChecked(false);
                    cbRatingDesc.setChecked(false);
                    cbRatingAsc.setChecked(false);
                    sortEnum = SortEnum.TITLE_ASC;
                } else {
                    sortEnum = SortEnum.NONE;
                }
            }
        });
        cbTitleDesc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbTitleAsc.setChecked(false);
                    cbRatingDesc.setChecked(false);
                    cbRatingAsc.setChecked(false);
                    sortEnum = SortEnum.TITLE_DESC;
                } else {
                    sortEnum = SortEnum.NONE;
                }
            }
        });
        cbRatingDesc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbTitleAsc.setChecked(false);
                    cbTitleDesc.setChecked(false);
                    cbRatingAsc.setChecked(false);
                    sortEnum = SortEnum.RATING_DESC;
                } else {
                    sortEnum = SortEnum.NONE;
                }
            }
        });
        cbRatingAsc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbTitleAsc.setChecked(false);
                    cbTitleDesc.setChecked(false);
                    cbRatingDesc.setChecked(false);
                    sortEnum = SortEnum.RATING_ASC;
                } else {
                    sortEnum = SortEnum.NONE;
                }
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClickListenerCallback(sortEnum);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setOnClickListener(Interfaces.OnClickListener listener) {
        this.onClickListener = listener;
    }

    public void setSelectedSort(SortEnum sortEnum) {
        this.sortEnum = sortEnum;
    }

    public void finish() {
        dialog.dismiss();
    }
}
