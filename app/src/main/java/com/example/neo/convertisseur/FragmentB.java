package com.example.neo.convertisseur;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Neo on 22/06/2017.
 */

public class FragmentB extends Fragment {
    //variables de l'app
    private static boolean isColored = false;
    private boolean isHidden = false;
    private ArrayList<String> nbChar = null;
    private String prm_input;
    private char [] pds;
    private int x1;
    private int x2;

    //constructeur newInstance pour créer le fragment avec des arguments
    public static FragmentB newInstance(int page, String title) {
        FragmentB fragB = new FragmentB();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragB.setArguments(args);

        return fragB;
    }

    //store variables d'instance basé sur les arguments
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //inflate la view pour le fragment par rapport au layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_b, container, false);

        /* LABELS + TEXTVIEWS + COMBOBOXES */
        //b signifie "bis" pour la scene2
        final EditText txtF1b = (EditText) view.findViewById(R.id.f1b);
        txtF1b.setSingleLine(true);
        txtF1b.setText(SecondActivity.centre_val);
        txtF1b.setTextColor(Color.WHITE);
        txtF1b.setSelection(3);
        final TextView txtF2b = (TextView) view.findViewById(R.id.f2b);
        final TextView txtF3b = (TextView) view.findViewById(R.id.f3b);
        final TextView txtF5b = (TextView) view.findViewById(R.id.f5b);
        final TextView txtF6b = (TextView) view.findViewById(R.id.f6b);
        final TextView txtF7b = (TextView) view.findViewById(R.id.f7b);

        final AlertDialog dialog1 = new AlertDialog.Builder(getContext()).create();
        dialog1.setTitle("ERREUR");
        dialog1.setMessage("Format PRM incorrect !");
        dialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog1.dismiss();
                    }
                });
        final AlertDialog dialog2 = new AlertDialog.Builder(getContext()).create();
        dialog2.setTitle("ERREUR");
        dialog2.setMessage("Num\u00e9ro de PRM invalide !");
        dialog2.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog2.dismiss();
                    }
                });


        /* BOUTTONS ET LEURS ICONES*/
        final Button convertBtn2 = (Button) view.findViewById(R.id.convertBtn2);
        final Button refreshBtn = (Button) view.findViewById(R.id.refresh_btn);
        final LinearLayout hidextra = (LinearLayout) view.findViewById(R.id.hidextra);
        refreshBtn.setVisibility(view.INVISIBLE);
        refreshBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_toparr, 0);
        hidextra.setVisibility(view.INVISIBLE);
        //convertBtn2.setBackgroundResource(R.mipmap.ic_btn);

        /* ACTION QUAND CLICK SUR BOUTTON CONVERTIR PRM EN PDS */
        convertBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Utility.hideSoftKeyboard(getActivity());
                    //zcount permet de compter le nombre de 0
                    int i=0, zCount=0;
                    pds = new char[9];
                    setPrm(String.valueOf(txtF1b.getText()));
                    //centre prend comme valeur les 3 permiers éléments du champ prm
                    String centre = new String(Character.toString(prm_input.charAt(0))
                            + Character.toString(prm_input.charAt(1))
                            + Character.toString(prm_input.charAt(2)));
                    //controle sur la longeur du prm (14)
                    if(txtF1b.getText().length() == 14) {
                        //controle sur le prm saisi (cf doc pour plus de détails)
                        convertPds((txtF1b.getText().toString().substring(3, 12)).toCharArray());
                        String verify = centre + String.valueOf(pds) + x1 + x2;
                        if(verify.equals(prm_input)) {
                            while(prm_input.charAt(i+3) == '0') {
                                zCount++;
                                i++;
                            }
                            //controle sur la saisie (cf doc pour plus de détails)
                            if(!isColored) {
                                //boucle qui permet de remplir pds (on ne prend pas en compte le num de centre s'ou i+3)
                                for(i=0; i<9; i++) {
                                    if(i == 6) {
                                        String edl = new String(pds);
                                        //enlève les 0 inutiles au début et les 3 derniers elements du pds (lettres + num compteur)
                                        if(edl.substring(0, edl.length()-3).replaceFirst("^0+(?!$)", "").equals("0")) {
                                            Utility.reinitValues(txtF2b, txtF3b, txtF5b, txtF6b, txtF7b);
                                            dialog1.show();
                                            return;
                                        }
                                        txtF3b.setText(edl.substring(0, edl.length()-3).replaceFirst("^0+(?!$)", ""));
                                        if(prm_input.charAt(i+3) == '1' && prm_input.charAt(i+4) == '0') {
                                            pds[i] = 'E';
                                            pds[i+1] = 'C';
                                            txtF6b.setText("Electricit\u00e9");
                                            txtF5b.setText("Consommateur");
                                        }
                                        else if(prm_input.charAt(i+3) == '1' && prm_input.charAt(i+4) == '1') {
                                            pds[i] = 'E';
                                            pds[i+1] = 'P';
                                            txtF6b.setText("Electricit\u00e9");
                                            txtF5b.setText("Producteur");
                                        }
                                        else if(prm_input.charAt(i+3) == '2' && prm_input.charAt(i+4) == '0') {
                                            pds[i] = 'G';
                                            pds[i+1] = 'C';
                                            txtF6b.setText("Gaz");
                                            txtF5b.setText("Consomateur");
                                        }
                                        else if(prm_input.charAt(i+3) == '2' && prm_input.charAt(i+4) == '1') {
                                            pds[i] = 'G';
                                            pds[i+1] = 'P';
                                            txtF6b.setText("Gaz");
                                            txtF5b.setText("Producteur");
                                        }
                                    }
                                    else if(i != 7 && zCount <= 0)
                                        pds[i] = prm_input.charAt(i+3);
                                    zCount--;
                                    //num de compteur
                                    txtF7b.setText(String.valueOf(pds[pds.length-1]));
                                    Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_bot);
                                    slideDown.setDuration(500);
                                    if(hidextra.getVisibility() == view.INVISIBLE && !isHidden) {
                                        hidextra.startAnimation(slideDown);
                                        hidextra.setVisibility(view.VISIBLE);
                                        refreshBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_toparr, 0);
                                    }
                                    if(refreshBtn.getVisibility() == view.INVISIBLE && !isHidden)
                                        refreshBtn.setVisibility(view.VISIBLE);
                                }
                                //num de pds en supprimant les 0 inutiles du début
                                txtF2b.setText(new String(pds).replaceFirst("^0+(?!$)", ""));
                            }
                            else {
                                Utility.reinitValues(txtF2b, txtF3b, txtF5b, txtF6b, txtF7b);
                                dialog1.show();
                            }
                        }
                        else {
                            Utility.reinitValues(txtF2b, txtF3b, txtF5b, txtF6b, txtF7b);
                            dialog2.show();
                        }
                    }
                    else {
                        Utility.reinitValues(txtF2b, txtF3b, txtF5b, txtF6b, txtF7b);
                        dialog1.show();
                    }
                } catch(Exception ex) {
                    Utility.reinitValues(txtF2b, txtF3b, txtF5b, txtF6b, txtF7b);
                    dialog1.show();
                }
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_bot);
                Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_top);
                slideDown.setDuration(500);
                slideUp.setDuration(500);
                if(hidextra.getVisibility() == view.INVISIBLE) {
                    hidextra.startAnimation(slideDown);
                    hidextra.setVisibility(view.VISIBLE);
                    refreshBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_toparr, 0);
                    isHidden = false;
                }
                else {
                    hidextra.startAnimation(slideUp);
                    hidextra.setVisibility(view.INVISIBLE);
                    refreshBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_botarr, 0);
                    isHidden = true;
                }
            }
        });

        /* ACTION QUAND TOUCHE PRESSEE & CONTROLE DE LA SAISIE SUR LE PDS */
        txtF1b.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txtF1b.length() < 3) {
                    txtF1b.setText(SecondActivity.centre_val);
                    txtF1b.setTextColor(Color.WHITE);
                }
                else if(txtF1b.length() >= 3) {
                    String center = txtF1b.getText().toString().substring(0, 3);
                    if(!center.equals(SecondActivity.centre_val)) {
                        txtF1b.setText(SecondActivity.centre_val + txtF1b.getText().toString().substring(3));
                        txtF1b.setTextColor(Color.WHITE);
                    }
                }
                if(txtF1b.getSelectionStart() < 3)
                    txtF1b.setSelection(3);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
                //TODO
            }

            @Override
            public void afterTextChanged(Editable s) {
                int i;
                isColored = false;
                String chars = String.valueOf(txtF1b.getText());
                nbChar = new ArrayList<>(Arrays.asList(chars.split("(?!^)")));
                for(i=0; i<nbChar.size(); i++) {
                    if(!nbChar.get(i).matches("[0-9.]"))
                        isColored = true;
                    if(i == 0 && nbChar.get(0).matches("[0-9.]")
                            &&!nbChar.get(0).equals("7"))
                        isColored = true;
                    if(i == 1 && nbChar.get(1).matches("[0-9.]")
                            && (!nbChar.get(1).equals("5") && !nbChar.get(1).equals("6")))
                        isColored = true;
                    if(i == 2 && nbChar.get(2).matches("[0-9.]")
                            && (!nbChar.get(2).equals("1") && !nbChar.get(2).equals("2")
                            && !nbChar.get(2).equals("3") && !nbChar.get(2).equals("4")
                            && !nbChar.get(2).equals("7")))
                        isColored = true;
                    if(i == 9 && !nbChar.get(9).matches("[1-2.]"))
                        isColored = true;
                    if(i == 10 && !nbChar.get(10).matches("[0-1.]"))
                        isColored = true;
                }
                if (isColored)
                    txtF1b.setTextColor(Color.RED);
                else
                    txtF1b.setTextColor(Color.WHITE);
            }
        });

        txtF1b.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    Utility.hideSoftKeyboard(getActivity());
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    /* ALGO DE CONVERTION PDS -> PRM */
    public void convertPds(char [] pds) {
        // TODO Auto-generated method stub
        int i;
        int x1 = 0, x2 = 0;
        for(i=0; i<pds.length; i++) {
            if(Character.toUpperCase(pds[i]) == 'P' || Character.toUpperCase(pds[i]) == '1')
                x1 += 1;
            else if(Character.toUpperCase(pds[i]) == 'E' || Character.toUpperCase(pds[i]) == '1')
                x1 += 1;
            else if(Character.toUpperCase(pds[i]) == 'G' || Character.toUpperCase(pds[i]) == '2')
                x1 += 2;
            else if(Character.toUpperCase(pds[i]) != 'C')
                x1 += Character.getNumericValue(pds[i]);
        }
        x1 += 2;
        x1 %= 11;
        for(i=0; i<pds.length; i++) {
            if(Character.toUpperCase(pds[i]) == 'C' || Character.toUpperCase(pds[i]) == '0')
                pds[i] = '0';
            else if(Character.toUpperCase(pds[i]) == 'P' || Character.toUpperCase(pds[i]) == '1') {
                x2 += 1*(pds.length-i+1);
                pds[i] = '1';
            }
            else if(Character.toUpperCase(pds[i]) == 'E' || Character.toUpperCase(pds[i]) == '1') {
                x2 += 1*(pds.length-i+1);
                pds[i] = '1';
            }
            else if(Character.toUpperCase(pds[i]) == 'G' || Character.toUpperCase(pds[i]) == '2') {
                x2 += 2*(pds.length-i+1);
                pds[i] = '2';
            }
            else
                x2 += Character.getNumericValue(pds[i])*(pds.length-i+1);
        }
        x2 += 2;
        x2 %= 11;
        if(x1 == 10 && x2 == 10)	x1 = x2 = 9;
        if(x1 == 10 && x2 == 0) {
            x1 = 9;
            x2 = 8;
        }
        if(x1 != 0 && x2 == 10)	x2 = 0;
        if(x1 == 0 && x2 == 10) {
            x1 = 8;
            x2 = 9;
        }
        if(x1 == 10 && x2 != 0)	x1 = 0;
        this.pds = pds;
        this.x1 = x1;
        this.x2 = x2;
    }

    /* GETTERS & SETTERS (Si création d'autres classes à l'avenir) */
    public void setPrm(String prm_input) {
        this.prm_input = prm_input;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }
}
