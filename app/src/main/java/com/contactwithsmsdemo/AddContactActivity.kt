package com.contactwithsmsdemo

import android.app.Activity
import android.app.ActivityManager
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
import android.widget.Toast
import com.contactwithsmsdemo.utility.GenericTextWatcher
import java.util.regex.Pattern


class AddContactActivity : AppCompatActivity(), View.OnClickListener {

    private var activity: AddContactActivity? = null
    internal lateinit var userImage: ImageView
    internal lateinit var personFirstName: EditText
    internal lateinit var personLastName: EditText
    internal lateinit var personContactNo: EditText
    internal lateinit var personEmailId: EditText
    internal lateinit var addUserContact: Button
    internal var isToast = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this@AddContactActivity
        setContentView(R.layout.activity_add_contact)
        ButterKnife.bind(activity!!)
        init()

    }

    fun init() {
        userImage = findViewById(R.id.iv_profile) as ImageView
        personFirstName = findViewById(R.id.et_first_name) as EditText
        personLastName = findViewById(R.id.et_last_name) as EditText
        personContactNo = findViewById(R.id.et_phone_name) as EditText
        personEmailId = findViewById(R.id.et_email) as EditText
        personEmailId.visibility = View.GONE
        addUserContact = findViewById(R.id.btn_submit) as Button
        addUserContact.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        if (isAllValid()) {
            userData(personFirstName.text.toString(),personLastName.text.toString(),personContactNo.text.toString())
        }

    }

    fun isAllValid(): Boolean {

        if (TextUtils.isEmpty(personFirstName.getText().toString())) {
            setError(activity, personFirstName, getString(R.string.fname_error), false)
            return false
        }

        if (TextUtils.isEmpty(personLastName.getText().toString())) {
            setError(activity, personLastName, getString(R.string.lname_error), false)
            return false
        }

        if (TextUtils.isEmpty(personContactNo.getText().toString())) {
            //UtilityFunctions.showErrorToast(activity, "Please enter contact person mobile number");
            setError(activity, personContactNo, getString(R.string.contact_error), false)
            return false
        }
        if (!isMobileNo(personContactNo.getText().toString())) {
            // Toast.makeText(activity, "Please enter a valid contact person mobile number", Toast.LENGTH_SHORT).show();
            setError(activity, personContactNo, getString(R.string.invalid_format), false)
            return false
        }


      /*  if (TextUtils.isEmpty(email.getText().toString())) {
            UtilityFunctions.setError(activity, email, getString(R.string.email_error_msg), false)
            // UtilityFunctions.showErrorToast(getContext(), getString(R.string.email_error_msg));
            return false
        }
        if (!ValidationUtils.isEmailValid(email.getText().toString())) {
            UtilityFunctions.setError(activity, email, getString(R.string.valid_email_error_msg), false)
            //UtilityFunctions.showErrorToast(getContext(), getString(R.string.valid_email_error_msg));
            return false
        }*/


        return true


    }



    fun userData(fname: String, lname: String, contactNo: String) {
        val addContactsUri = ContactsContract.Data.CONTENT_URI

        // Add an empty contact and get the generated id.
        val rowContactId = getRawContactId()

        val fullName: String = fname.toString() + " " + lname.toString()

        // Add contact phone data.
        val phoneNumber = contactNo.toString()
        val phoneTypeStr: String = "mobile"

        // Add contact name data.
        val displayName = fullName.toString()

        insertContactDisplayName(addContactsUri, rowContactId, displayName, phoneNumber, phoneTypeStr)


        //insertContactPhoneNumber(addContactsUri, rowContactId, phoneNumber, phoneTypeStr)

        // finish()

        activity!!.finish()
    }

    private fun getRawContactId(): Long {
        // Inser an empty contact.
        val contentValues = ContentValues()
        val rawContactUri = activity!!.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues)
        // Get the newly created contact raw id.
        return ContentUris.parseId(rawContactUri)
    }


    // Insert newly created contact display name.
    private fun insertContactDisplayName(addContactsUri: Uri, rawContactId: Long, displayName: String, phoneNumber: String, phoneTypeStr: String) {
        val contentValues = ContentValues()

        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)

        // Put contact display name value.
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName)

        // Put phone number value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)


        // Calculate phone type by user selection.
        var phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME

        if ("home".equals(phoneTypeStr, ignoreCase = true)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME
        } else if ("mobile".equals(phoneTypeStr, ignoreCase = true)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
        } else if ("work".equals(phoneTypeStr, ignoreCase = true)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_WORK
        }
        // Put phone type value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, phoneContactType)

        activity!!.getContentResolver().insert(addContactsUri, contentValues)

    }

    private fun insertContactPhoneNumber(addContactsUri: Uri, rawContactId: Long, phoneNumber: String, phoneTypeStr: String) {
        // Create a ContentValues object.
        val contentValues = ContentValues()

        // Each contact must has an id to avoid java.lang.IllegalArgumentException: raw_contact_id is required error.
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)

        // Put phone number value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)

        // Calculate phone type by user selection.
        var phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME

        if ("home".equals(phoneTypeStr, ignoreCase = true)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME
        } else if ("mobile".equals(phoneTypeStr, ignoreCase = true)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
        } else if ("work".equals(phoneTypeStr, ignoreCase = true)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_WORK
        }
        // Put phone type value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, phoneContactType)

        // Insert new contact data into phone contact list.
        activity!!.getContentResolver().insert(addContactsUri, contentValues)

    }

    fun isNumeric(number: String): Boolean {
        var isValid = false
        // Initialize reg ex for numeric data.
        val expression = "^[-+]?[0-9]*\\.?[0-9]+$"
        val pattern = Pattern.compile(expression)
        val matcher = pattern.matcher(number)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }

    fun setError(activity: Activity?, view: View?, errorMessage: String, hasPicker: Boolean) {
        var errorMessage = errorMessage
        if (view == null || activity == null) {
            return
        }
        if (isEmptyStr(errorMessage)) {
            errorMessage = ""
        }

        if (hasPicker) {
            requestFocus(view, activity)
        } else {
            view.requestFocus()
        }

        if (view is TextView) {
            view.error = errorMessage
            view.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus -> view.error = null }
            removeError(activity, view)
        } else if (view is TextInputLayout) {
            view.error = errorMessage
            view.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus -> view.isErrorEnabled = false }
        } else {
            handleToast(activity, errorMessage)
        }

    }

    fun isEmptyStr(s: String): Boolean {
        return TextUtils.isEmpty(s) || s.trim { it <= ' ' } == ""
    }

    fun requestFocus(view: View?, activity: Activity?) {
        if (view == null) {
            return
        }
        if (activity != null) {
            hideKeyboard(activity)
        }

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.isFocusableInTouchMode = false

    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun removeError(activity: Activity, view: View) {
        if (isRunning(activity)) {
            Handler().postDelayed({
                if (view is TextView) {
                    view.error = null
                } else if (view is TextInputLayout) {
                    view.error = null
                }
            }, 6000)
        }
    }

    fun isRunning(ctx: Context): Boolean {
        val activityManager = ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks = activityManager.getRunningTasks(Integer.MAX_VALUE)

        for (task in tasks) {
            if (ctx.packageName.equals(task.baseActivity.packageName, ignoreCase = true))
                return true
        }

        return false
    }

    fun handleToast(context: Context?, toastText: String?) {

        if (context == null) {
            return
        }

        if (toastText == null) {
            return
        }

        if (!isToast) {
            isToast = true


            if (!isActivityAvailable(context)) {
                isToast = false
                return
            }

            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ isToast = false }, 1200)

        }

    }

    fun isActivityAvailable(activity: Context?): Boolean {

        if (activity == null) {
            return false
        }

        if (activity is Activity) {
            if (activity.isDestroyed) {
                return false
            }

            if (activity.isFinishing) {
                return false
            }
        }

        return true
    }

    fun isMobileNo(mob: String): Boolean {
        var isValid = false
        val mobileNo = if (mob.startsWith(GenericTextWatcher.prefix))
            mob.substring(GenericTextWatcher.prefix.length, mob.length)
        else
            mob
        if (isNumeric(mobileNo)) {
            isValid = isValidMobile(mob) && mobileNo.length == 10
        }
        return isValid
    }

    fun isValidMobile(phone: String): Boolean {

        //^[789]\d{9}$
        val sPattern = Pattern.compile("^[6789]\\d{9}$")
        return sPattern.matcher(phone).matches()
    }


}