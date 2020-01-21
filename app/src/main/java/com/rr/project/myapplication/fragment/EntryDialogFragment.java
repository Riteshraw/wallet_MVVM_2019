package com.rr.project.myapplication.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rr.project.myapplication.R;
import com.rr.project.myapplication.TabActivity;
import com.rr.project.myapplication.WalletApplication;
import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EntryDialogFragment extends DialogFragment {

    @BindView(R.id.txt_entry_date)
    TextView txt_entry_date;
    @BindView(R.id.edt_note)
    EditText edt_note;
    @BindView(R.id.edt_Amount)
    EditText edt_Amount;
    @BindView(R.id.btn_add_entry)
    Button btn_add_entry;
    @BindView(R.id.rb_debit)
    RadioButton rb_debit;
    @BindView(R.id.rb_credit)
    RadioButton rb_credit;
    @BindView(R.id.dialog_header)
    TextView dialog_header;
    @BindView(R.id.dialog_rg)
    RadioGroup dialog_rg;
    @BindView(R.id.btn_delete_entry)
    Button btn_delete_entry;
    private Entry editEntry;
    private boolean isRbChanged = false;

    public static EntryDialogFragment newInstance(String title) {
        EntryDialogFragment frag = new EntryDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        ButterKnife.bind(this, view);

        if ((getArguments().getParcelable("ENTRY")) != null) {
            editEntry = (getArguments().getParcelable("ENTRY"));
            WalletApplication.getInstance().setEditEntry(true);
            if (editEntry == null)
                Toast.makeText(getContext(), "Entry null", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        edt_note.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        if (editEntry != null)
            btn_delete_entry.setVisibility(View.VISIBLE);

        if (editEntry != null) {
            edt_note.setText(editEntry.getNote());
            if (editEntry.isDebit())
                rb_debit.setChecked(true);
            else
                rb_credit.setChecked(true);
//            edt_Amount.setText(Utils.removeTrailingZeros(String.valueOf(editEntry.getAmount())));
            edt_Amount.setText(Utils.removeTrailingZeros((editEntry.getAmount())));
            setDate(editEntry.getDateString());
        } else
            setDate(Utils.getCurrentDateInString());

        dialog_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isRbChanged = true;
            }
        });

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @OnClick(R.id.btn_add_entry)
    public void onSubmit(View view) {
        if (validate())
            return;

        if (editEntry != null) {//check if its edit case
            editEntry.setNote(edt_note.getText().toString());
            if (editEntry.getAmount() != Float.parseFloat(edt_Amount.getText().toString()) ||
                    !editEntry.getDateString().equals(txt_entry_date.getText().toString()) ||
                    isRbChanged
            ) {
                long originalDate = 0;
                boolean isDateChange = txt_entry_date.getText().toString().equals(editEntry.getDateString()) ? false : true;

                editEntry.setAmount(Float.parseFloat(edt_Amount.getText().toString()));
                if (isDateChange) {
                    originalDate = editEntry.getEntryTime();
                    editEntry.setDateString(txt_entry_date.getText().toString());
                    editEntry.setEntryTime(Utils.getEntryTime(txt_entry_date));
                }
                editEntry.setDebit(rb_debit.isChecked());

                ((TabActivity) getActivity()).updateEntry(editEntry, isDateChange, originalDate);
            } else
                ((TabActivity) getActivity()).updateEntryNote(editEntry);

        } else {
            ((TabActivity) getActivity()).addEntry(
                    edt_note.getText().toString(),
                    Float.parseFloat(edt_Amount.getText().toString()),
                    rb_debit.isChecked(),
                    0,
                    Utils.getEntryTime(txt_entry_date),
                    true
            );
        }
        dismiss();

        Utils.hideKeyboard(getActivity());

    }

    @OnClick(R.id.btn_delete_entry)
    public void onDelete() {
        ((TabActivity) getActivity()).deleteEntry(editEntry);
        dismiss();
        Utils.hideKeyboard(getActivity());
    }

    private boolean validate() {
        if (Utils.getTextFromEditText(edt_note).equals("")) {
            Toast.makeText(getContext(), "Please enter a note", Toast.LENGTH_SHORT).show();
            return true;
        } else if (Utils.getTextFromEditText(edt_Amount).equals("")) {
            Toast.makeText(getContext(), "Please enter a amount", Toast.LENGTH_SHORT).show();
            return true;
        } else if (dialog_rg.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getContext(), "Please select a transaction type", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    @OnClick(R.id.btn_cancel)
    public void onCancel(View view) {
        dismiss();
        Utils.hideKeyboard(getActivity());
    }

    private void hideKeboard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void setDate(String date) {
//        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        txt_entry_date.setText(date);
    }
}
