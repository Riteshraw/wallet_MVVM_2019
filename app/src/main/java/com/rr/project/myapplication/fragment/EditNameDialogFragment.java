package com.rr.project.myapplication.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.rr.project.myapplication.R;
import com.rr.project.myapplication.SuperTabViewModel;
import com.rr.project.myapplication.dao.SuperTab;

import java.util.Date;
import java.util.List;
// https://developer.android.com/reference/android/app/DialogFragment
// https://guides.codepath.com/android/using-dialogfragment#passing-data-to-parent-fragment

public class EditNameDialogFragment extends DialogFragment {
    private EditText mEditText;
    private SuperTabViewModel sTabViewModel;

    public EditNameDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditNameDialogFragment newInstance(String title) {
        EditNameDialogFragment frag = new EditNameDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Pick a style based on the num.
//        int style = DialogFragment.STYLE_NORMAL, theme = 0;
//        switch ((mNum-1)%6) {
//            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
//            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
//            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
//            case 4: style = DialogFragment.STYLE_NORMAL; break;
//            case 5: style = DialogFragment.STYLE_NORMAL; break;
//            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
//            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
//            case 8: style = DialogFragment.STYLE_NORMAL; break;
//        }
//        switch ((mNum-1)%6) {
//            case 4: theme = android.R.style.Theme_Holo; break;
//            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
//            case 6: theme = android.R.style.Theme_Holo_Light; break;
//            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
//            case 8: theme = android.R.style.Theme_Holo_Light; break;
//        }
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_name, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        ((Button) view.findViewById(R.id.btn_tab_name_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTabNameSubmit();
            }
        });

    }

    private void onTabNameSubmit() {
        if (!mEditText.getText().toString().isEmpty()) {
            sTabViewModel = ViewModelProviders.of(this).get(SuperTabViewModel.class);
            final SuperTab superTab = new SuperTab(mEditText.getText().toString().toUpperCase(), new Date().getTime());

            sTabViewModel.isSuperTabAlreadyPresent(superTab).observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer entries) {
                    if (entries == 0)
                        sTabViewModel.insertSuperTab(superTab);
                    else
                        Toast.makeText(getActivity(), "Name already present", Toast.LENGTH_SHORT).show();
                }
            });
//            new SuperTabViewModel(getActivity().getApplication()).isSuperTabAlreadyPresent(new SuperTab(mEditText.getText().toString(), new Date().getTime()));
            dismiss();
        }
        //close dialog on successful submit
    }

    public void onDismiss(View view) {
        dismiss();
    }
}
