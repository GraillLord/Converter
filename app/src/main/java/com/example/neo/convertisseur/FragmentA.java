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

public class FragmentA extends Fragment {
    //variables de l'app
    private static boolean isColored = false;
    private boolean isHidden = false;
    private ArrayList<String> nbChar = null;
    private String nCentre;
    private String pds_input;
    private char [] prm;
    private char [] pds;
    private int x1;
    private int x2;

    //constructeur newInstance pour créer le fragment avec des arguments
    public static FragmentA newInstance(int page, String title) {
        FragmentA fragA = new FragmentA();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragA.setArguments(args);

        return fragA;
    }

    //store variables d'instance basé sur les arguments
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //inflate la view pour le fragment par rapport au layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_a, container, false);

        if(SecondActivity.centre != null)
            nCentre = SecondActivity.centre;

        /* LABELS + TEXTVIEWS + COMBOBOXES */
        //b signifie "bis" pour la scene2
        final EditText txtF1 = (EditText) view.findViewById(R.id.f1);
        txtF1.setSingleLine(true);
        final TextView txtF2 = (TextView) view.findViewById(R.id.f2);
        final TextView txtF3 = (TextView) view.findViewById(R.id.f3);
        final TextView txtF5 = (TextView) view.findViewById(R.id.f5);
        final TextView txtF6 = (TextView) view.findViewById(R.id.f6);
        final TextView txtF7 = (TextView) view.findViewById(R.id.f7);

        final AlertDialog dialog1 = new AlertDialog.Builder(getContext()).create();
        dialog1.setTitle("ERREUR");
        dialog1.setMessage("Format PDS incorrect !");
        dialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog1.dismiss();
                    }
                });

        /* BOUTTONS ET LEURS ICONES*/
        final Button convertBtn1 = (Button) view.findViewById(R.id.convertBtn1);
        final Button refreshBtn = (Button) view.findViewById(R.id.refresh_btn);
        final LinearLayout hidextra = (LinearLayout) view.findViewById(R.id.hidextra);
        refreshBtn.setVisibility(view.INVISIBLE);
        refreshBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_toparr, 0);
        hidextra.setVisibility(view.INVISIBLE);
        //convertBtn1.setBackgroundResource(R.mipmap.ic_btn);

        /* ACTION QUAND CLICK SUR BOUTTON CONVERTIR PDS EN PRM */
        convertBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Utility.hideSoftKeyboard(getActivity());
                    //count permet de compter le nombre d'éléments du pds saisi
                    int i, count;
                    boolean isCorrect = false;
                    String centre = null, edl = null;
                    pds = new char[9];
                    prm = new char[14];
                    //si l'utilisateur n'a pas selectionné d'élément dans la combobox alors erreur
                    //variable centre prend une valeur en fonction de la selection de la combobox
                    if (nCentre.equals("Guyane"))
                        centre = "764";
                    else if (nCentre.equals("R\u00e9union"))
                        centre = "763";
                    else if (nCentre.equals("Martinique"))
                        centre = "762";
                    else if (nCentre.equals("Guadeloupe"))
                        centre = "761";
                    else if (nCentre.equals("Corse"))
                        centre = "757";
                    if (isColored) {
                        Utility.reinitValues(txtF2, txtF3, txtF5, txtF6, txtF7);
                        dialog1.show();
                        return;
                    }
                    //ajout de 0 au pds jusqu'à avoir une taille de pds max (9)
                    StringBuilder zeros = new StringBuilder();
                    if ((count = txtF1.getText().length()) <= 9) {
                        for (i = 0; i < (9 - count); i++)
                            zeros.append('0');
                        setPds(zeros.toString() + txtF1.getText());
                    }
                    //controle sur la présence du numero de compteur (dernier element)
                    if (Character.toUpperCase(pds_input.charAt(8)) == 'C'
                            || Character.toUpperCase(pds_input.charAt(8)) == 'P') {
                        Utility.reinitValues(txtF2, txtF3, txtF5, txtF6, txtF7);
                        dialog1.show();
                        return;
                    }
                    for (i = 0; i < 9; i++) {
                        //edl = 6 premiers elements du pds (avec 0 inclus)
                        if (i == 6) {
                            edl = new String(pds).replaceFirst("^0+(?!$)", "");
                            if (edl.length() <= 3) {
                                Utility.reinitValues(txtF2, txtF3, txtF5, txtF6, txtF7);
                                dialog1.show();
                                return;
                            }
                        }
                        //remplissage du pds
                        pds[i] = pds_input.charAt(i);
                        if (Character.toUpperCase(pds_input.charAt(i)) == 'E')
                            txtF6.setText("Electricit\u00e9");
                        else if (Character.toUpperCase(pds_input.charAt(i)) == 'G')
                            txtF6.setText("Gaz");
                        if (Character.toUpperCase(pds_input.charAt(i)) == 'C') {
                            txtF5.setText("Consommateur");
                            isCorrect = true;
                        } else if (Character.toUpperCase(pds_input.charAt(i)) == 'P') {
                            txtF5.setText("Producteur");
                            isCorrect = true;
                        }
                    }
                    //controle secondaire sur la saisie avec isCorrect (cf doc pour plus de details)
                    if (isCorrect) {
                        convertPds(pds);
                        for (i = 0; i < 3; i++)
                            prm[i] = centre.charAt(i);
                        for (i = 0; i < 9; i++)
                            prm[i + 3] = pds[i];
                        //remplissage des 2 derniers éléments du tab prm correspondant aux clées
                        prm[12] = Character.forDigit(getX1(), 10);
                        prm[13] = Character.forDigit(getX2(), 10);
                        txtF2.setText(new String(prm));
                        txtF3.setText(edl.replaceFirst("^0+(?!$)", ""));
                        txtF7.setText(String.valueOf(prm[11]));
                        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_bot);
                        slideDown.setDuration(500);
                        if(hidextra.getVisibility() == view.INVISIBLE && !isHidden) {
                            hidextra.startAnimation(slideDown);
                            hidextra.setVisibility(view.VISIBLE);
                            refreshBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_toparr, 0);
                        }
                        if(refreshBtn.getVisibility() == view.INVISIBLE && !isHidden)
                            refreshBtn.setVisibility(view.VISIBLE);
                    } else {
                        Utility.reinitValues(txtF2, txtF3, txtF5, txtF6, txtF7);
                        dialog1.show();
                    }
                } catch (Exception ex) {
                    Utility.reinitValues(txtF2, txtF3, txtF5, txtF6, txtF7);
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
        txtF1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
                //TODO
            }

            @Override
            public void afterTextChanged(Editable s) {
                //isEG, isCP et isCn correspondent aux lettres et au num de compteur
                int i, isEG = 0, isCP = 0, isCn = 0;
                //isFollowed sert de controle à isEG et isCP
                //(true quand isEG=1 && isCP=0 ; false quand isEG=isCP=0 ou isEG=isCP=1)
                boolean isFollowed = false;
                //permet de controller si c'est une lettre autre que e,g,c,p
                boolean isLetter = false;
                //permet de controller si c'est un nombre
                boolean isNumber = false;
                isColored = false;
                //série de controles sur la saisie (soit isColored reste false soit il devient true)
                String chars = String.valueOf(txtF1.getText());
                nbChar = new ArrayList<>(Arrays.asList(chars.split("(?!^)")));
                for (i = 0; i < nbChar.size(); i++) {
                    if (!nbChar.get(i).matches("[0-9.]")) {
                        if (!nbChar.get(i).toUpperCase().matches("[EGCP]"))
                            isLetter = true;
                        if (isEG == 1 && isCP == 1)
                            isCn += 2;
                        isColored = true;
                    } else if (isFollowed)
                        isColored = true;
                    else if (isEG == 1 && isCP == 1)
                        isCn++;
                    else
                        isNumber = true;
                    if (nbChar.size() >= 2 && nbChar.size() < 10 && isNumber) {
                        if (!isLetter && isEG == 0 && nbChar.get(i).toUpperCase().matches("[EG]")) {
                            isColored = false;
                            isLetter = true;
                            isFollowed = true;
                            isEG++;
                        } else if (nbChar.get(i).toUpperCase().matches("[CP]")) {
                            if (nbChar.get(i - 1).toUpperCase().matches("[EG]"))
                                isFollowed = false;
                            isLetter = true;
                            isCP++;
                        }
                        if (isEG == 1 && isCP <= 1 && !isFollowed) {
                            if (isCn <= 1 && !nbChar.get(i).matches("0"))
                                isColored = false;
                            else
                                isColored = true;
                        }
                    }
                }
                if (nbChar.size() > 6
                        && (!nbChar.contains("E") && !nbChar.contains("G"))
                        && (!nbChar.contains("e") && !nbChar.contains("g")))
                    isColored = true;
                //colore le texte en fonction de isColored
                if (isColored)
                    txtF1.setTextColor(Color.RED);
                else
                    txtF1.setTextColor(Color.WHITE);
            }
        });

        txtF1.setOnKeyListener(new View.OnKeyListener() {
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
    public void setPds(String pds_input) {
        this.pds_input = pds_input;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }
}
