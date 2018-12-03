package com.inti.student.cricfeed;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MoreFragment extends Fragment {
    private LinearLayout Settings, Feedback, About, Logout;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_more, null);

        firebaseAuth = FirebaseAuth.getInstance();

        //perform logout
        Logout = v.findViewById(R.id.moreLogout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //redirect to settings page
        Settings = v.findViewById(R.id.moreSettings);
        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        //open feedback dialog
        Feedback = v.findViewById(R.id.moreFeedback);
        Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFeedbackDialog();
            }
        });

        //open about app dialog
        About = v.findViewById(R.id.moreAbout);
        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAboutDialog();
            }
        });
        return v;
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.more_about);
        builder.setView(R.layout.dialog_about);
        builder.setNegativeButton(R.string.dialog_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dismiss dialog
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void showFeedbackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_feedback, null);
        //variables to store user input
        final TextView feedbackName = (TextView) dialogView.findViewById(R.id.feedback_name);
        final TextView feedbackEmail = (TextView) dialogView.findViewById(R.id.feedback_email);
        final TextView feedbackContent = (TextView) dialogView.findViewById(R.id.feedback_content);

        builder.setView(dialogView);
        builder.setTitle(R.string.more_feedback);

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(R.string.dialog_submit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), R.string.toast_feedback, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}
