package com.demo.app.UI.Document;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.demo.app.R;


public class EnterIdDialogFragment extends DialogFragment {

    interface EnterIdDialogFragmentListener {
        void onEnterIdFragmentFinish(String id);
    }

    EnterIdDialogFragmentListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (EnterIdDialogFragmentListener)context;
        }
        catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement EnterIdDialogFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().setCanceledOnTouchOutside(true);
        return inflater.inflate(R.layout.fragment_enter_id, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText idEt = view.findViewById(R.id.et_fei_id);

        ((Button)view.findViewById(R.id.btn_fei_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idEt.getText().length() > 0) {
                    listener.onEnterIdFragmentFinish(idEt.getText().toString());
                    dismiss();
                }
            }
        });
    }
}
