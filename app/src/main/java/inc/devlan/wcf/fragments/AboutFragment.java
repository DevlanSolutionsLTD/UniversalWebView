package inc.devlan.wcf.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.devlan.wcf.R;
import inc.devlan.wcf.activity.MainActivity;

public class AboutFragment extends Fragment {
    public View view;
    private CardView call_card, email_card, dev_card;


    public AboutFragment() {
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActionBar().setTitle("About Us");
        view = inflater.inflate(R.layout.fragment_about, container, false);
        call_card = view.findViewById(R.id.call_card);
        email_card = view.findViewById(R.id.email_card);
        dev_card = view.findViewById(R.id.dev_card);


        call_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + getActivity().getString(R.string.about_us_contact_text)));
                startActivity(intent);
            }
        });

        email_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+ getActivity().getString(R.string.about_us_email_text)));
                intent.setPackage("com.google.android.gm");
                intent.putExtra(Intent.EXTRA_SUBJECT,getActivity().getString(R.string.app_name));
                startActivity(intent);
            }
        });

        dev_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + getActivity().getString(R.string.facebook_page_id)));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getActivity().getString(R.string.facebook_link))));
                }
            }
        });

    return view;
    }

    private ActionBar getActionBar() {
        return ((MainActivity) getActivity()).getSupportActionBar();
    }

}
