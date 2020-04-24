package com.demo.app.UI.Document;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.demo.app.R;

import java.util.Objects;



public class FinishDialogFragment extends DialogFragment {

    interface FinishDialogFragmentListener {
        void onFinishFragmentFinish();
    }

    private String tid;
    private FinishDialogFragmentListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tid = Objects.requireNonNull(getArguments()).getString("tid");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the FinishDialogFragmentListener
            listener = (FinishDialogFragmentListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement FinishDialogFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().setCanceledOnTouchOutside(false);
        return inflater.inflate(R.layout.fragment_finish, container);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView)view.findViewById(R.id.tv_ff_tid)).setText(String.format("ID %s", tid));
        ((Button)view.findViewById(R.id.btn_ff_finish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFinishFragmentFinish();
                dismiss();
            }
        });
    }
}
