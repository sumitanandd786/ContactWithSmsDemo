package com.contactwithsmsdemo.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class GenericTextWatcher implements TextWatcher {

    private EditText editText;

    public static String prefix = "+91 ";

    public GenericTextWatcher(EditText view) {
        this.editText = view;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    public void afterTextChanged(Editable s) {


        try {
            String input = s.toString();
            if (!input.startsWith(prefix)) {
                String extra = differencePrefix(prefix, input);
                String newInput = prefix + extra;
                editText.setText(newInput);
                editText.setSelection(newInput.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        if (!s.toString().startsWith("+91")) {
//
//            mobileNumber.setText("+91 " + mobileNumber.getText());
//            Selection.setSelection(mobileNumber.getText(), mobileNumber.getText().length());
//        }

//        if (!s.toString().contains("+91")) {
//            mobileNumber.setText("+91" + s.toString());
//            Selection.setSelection(mobileNumber.getText(), mobileNumber.getText().length());
//            return;
//        }

    }


    public static String differencePrefix(String prefix, String extra) {
        if (extra.length() < prefix.length()) return "";
        StringBuilder sb = new StringBuilder();
        StringBuilder eb = new StringBuilder();
        int p = 0;
        for (int i = 0; i < extra.length(); i++) {
            if (i >= prefix.length()) {
                while (p < extra.length()) {
                    eb.append(extra.charAt(p));
                    p++;
                }
                break;
            }
            if (p >= extra.length()) break;
            char pchar = extra.charAt(p);
            char ichar = prefix.charAt(i);
            while (pchar != ichar) {
                //check if char was deleted
                int c = i + 1;
                if (c < prefix.length()) {
                    char cchar = prefix.charAt(c);
                    if (cchar == pchar) {
                        break;
                    }
                }
                sb.append(pchar);
                p++;
                if (p >= extra.length()) break;
                pchar = extra.charAt(p);
            }
            p++;
        }

        return eb.toString() + sb.toString();
    }
}
