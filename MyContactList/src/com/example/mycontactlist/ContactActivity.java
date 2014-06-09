package com.example.mycontactlist;

import com.example.mycontactlist.DatePickerDialog.SaveDateListener;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.text.method.KeyListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.os.Build;

public class ContactActivity extends FragmentActivity implements SaveDateListener {

	private Contact currentContact;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initListButton();
        initMapButton();
        initSettingsButton();
        initToggleButton();
        
        initChangeDateButton();
      
        initTextChangedEvents();
        initSaveButton();
        
        setForEditing(false);
        currentContact = new Contact();
        
        String pickColor = getSharedPreferences("MyContactListPreferences",Context.MODE_PRIVATE).getString("pickcolor","Pink");
        if (pickColor.equalsIgnoreCase("Pink")){
  
    		ScrollView sv1 = (ScrollView)findViewById(R.id.scrollView1);
			sv1.setBackgroundColor(getResources().getColor(R.color.pink_background));
			
    	}
    	else{
//jkljkl
    		ScrollView sv1 = (ScrollView)findViewById(R.id.scrollView1);
			sv1.setBackgroundColor(getResources().getColor(R.color.yellow_background));
    	}
    
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact, menu);
        return true;
    }

  

    private void initListButton(){
    	ImageButton list= (ImageButton) findViewById(R.id.imageButtonList);
    	list.setOnClickListener(new View.OnClickListener(){
    		public void onClick (View v){
    			Intent intent = new Intent (ContactActivity.this, ContactListActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(intent);
    		}
    	});
    }
    
    
    private void initMapButton(){
    	ImageButton list= (ImageButton) findViewById(R.id.imageButtonMap);
    	list.setOnClickListener(new View.OnClickListener(){
    		public void onClick (View v){
    			Intent intent = new Intent (ContactActivity.this, ContactMapActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(intent);
    		}
    	});
    }
    
    
    private void initSettingsButton(){
    	ImageButton list= (ImageButton) findViewById(R.id.imageButtonSettings);
    	list.setOnClickListener(new View.OnClickListener(){
    		public void onClick (View v){
    			Intent intent = new Intent (ContactActivity.this, ContactSettingsActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(intent);
    		}
    	});
    }
    	
    
    private void initToggleButton() {
    	final ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
    	editToggle.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick (View arg0) {
    			setForEditing(editToggle.isChecked());
    		}
    	});
    }
    
  
   
   private void initSaveButton() {
	   Button saveButton = (Button) findViewById(R.id.buttonSave);
	   saveButton.setOnClickListener(new View.OnClickListener() {
		   
		   @Override
		   public void onClick(View v) {
			   hideKeyboard();
			   ContactDataSource ds = new ContactDataSource(ContactActivity.this);
			   ds.open();
			    boolean wasSuccessful = false;
			    if(currentContact.getContactID()==-1) {
			    	wasSuccessful = ds.insertContact(currentContact);
			    	int newId = ds.getLastContactId();
			    	currentContact.setContactID(newId);
			    }
			    else {
			    	wasSuccessful = ds.updateContact(currentContact);
			    }
			    ds.close();
			    
			    if (wasSuccessful) {
			    	ToggleButton editToggle = (ToggleButton)findViewById(R.id.toggleButtonEdit);
			    	editToggle.toggle();
			    	setForEditing(false);
			    }
		   }
	   });
   }
   
   
   private void initTextChangedEvents(){
	   final EditText contactName = (EditText) findViewById(R.id.editName);
	   contactName.addTextChangedListener(new TextWatcher(){
		   
		   public void afterTextChanged (Editable s) {
			   currentContact.setContactName(contactName.getText().toString());
		   }
		   public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			   //auto generated method stub - what the heck?
		   
		   }
		   public void onTextChanged(CharSequence s, int start, int before, int count){
			   //auto generated method stub - this again?  what the hay?
		   }
	   });
	   
	   final EditText bff = (EditText) findViewById(R.id.editBFF);
	   bff.addTextChangedListener(new TextWatcher(){
		   
		   public void afterTextChanged (Editable s) {
			   currentContact.setBFF(bff.getText().toString());
		   }
		   public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			   //auto generated method stub - what the heck?
		   
		   }
		   public void onTextChanged(CharSequence s, int start, int before, int count){
			   //auto generated method stub - this again?  what the hay?
		   }
	   });
	final EditText streetAddress = (EditText) findViewById(R.id.editAddress);
	streetAddress.addTextChangedListener(new TextWatcher(){
		public void afterTextChanged(Editable s){
			currentContact.setStreetAddress(streetAddress.getText().toString());
		}
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
			//Auto generated method stub
		}
		public void onTextChanged(CharSequence s, int start, int before, int count){
			//auto generated method stub
		}
	});
	
	
	final EditText city = (EditText) findViewById(R.id.editCity);
	city.addTextChangedListener(new TextWatcher(){
		public void afterTextChanged(Editable s){
			currentContact.setCity(city.getText().toString());
		}
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
			//Auto generated method stub
		}
		public void onTextChanged(CharSequence s, int start, int before, int count){
			//auto generated method stub
		}
	});
	final EditText state = (EditText) findViewById(R.id.editState);
	state.addTextChangedListener(new TextWatcher(){
		public void afterTextChanged(Editable s){
			currentContact.setState(state.getText().toString());
		}
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
			//Auto generated method stub
		}
		public void onTextChanged(CharSequence s, int start, int before, int count){
			//auto generated method stub
		}
	});
	final EditText zipCode = (EditText) findViewById(R.id.editZipcode);
	zipCode.addTextChangedListener(new TextWatcher(){
		public void afterTextChanged(Editable s){
			currentContact.setZipCode(zipCode.getText().toString());
		}
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
			//Auto generated method stub
		}
		public void onTextChanged(CharSequence s, int start, int before, int count){
			//auto generated method stub
		}
	});
	final EditText phoneNumber = (EditText) findViewById(R.id.editHome);
	phoneNumber.addTextChangedListener(new TextWatcher(){
		public void afterTextChanged(Editable s){
			currentContact.setPhoneNumber(phoneNumber.getText().toString());
		}
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
			//Auto generated method stub
		}
		public void onTextChanged(CharSequence s, int start, int before, int count){
			//auto generated method stub
		}
		
	});
	final EditText cellNumber = (EditText) findViewById(R.id.editCell);
	cellNumber.addTextChangedListener(new TextWatcher(){
		public void afterTextChanged(Editable s){
			currentContact.setCellNumber(cellNumber.getText().toString());
		}
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
			//Auto generated method stub
		}
		public void onTextChanged(CharSequence s, int start, int before, int count){
			//auto generated method stub
		}
	});
	final EditText eMail = (EditText) findViewById(R.id.editEMail);
	eMail.addTextChangedListener(new TextWatcher(){
		public void afterTextChanged(Editable s){
			currentContact.setEmail(eMail.getText().toString());
		}
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
			//Auto generated method stub
		}
		public void onTextChanged(CharSequence s, int start, int before, int count){
			//auto generated method stub
		}
	});
	
	phoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
	cellNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
	
   }
   
   
    private void setForEditing(boolean enabled) {
    	EditText editName = (EditText) findViewById(R.id.editName);
    	EditText editBFF= (EditText) findViewById(R.id.editBFF);
    	EditText editAddress = (EditText) findViewById(R.id.editAddress);
    	EditText editCity = (EditText) findViewById(R.id.editCity);
    	EditText editState = (EditText) findViewById(R.id.editState);
    	EditText editZipcode = (EditText) findViewById(R.id.editZipcode);
    	EditText editPhone = (EditText) findViewById(R.id.editHome);
    	EditText editCell = (EditText) findViewById(R.id.editCell);
    	EditText editEmail = (EditText) findViewById(R.id.editEMail);
    	Button buttonChange = (Button) findViewById(R.id.btnBirthday);
    	Button buttonSave = (Button) findViewById(R.id.buttonSave);
    	
    	editName.setEnabled(enabled);
    	editBFF.setEnabled(enabled);
    	editAddress.setEnabled(enabled);
    	editCity.setEnabled(enabled);
    	editState.setEnabled(enabled);
    	editZipcode.setEnabled(enabled);
    	editPhone.setEnabled(enabled);
    	editCell.setEnabled(enabled);
    	editEmail.setEnabled(enabled);
    	buttonChange.setEnabled(enabled);
    	buttonSave.setEnabled(enabled);
    	
    	if (enabled) {
    		editName.requestFocus();
    	}
    	else {
    		ScrollView s = (ScrollView) findViewById(R.id.scrollView1);
    		s.fullScroll(ScrollView.FOCUS_UP);
    		s.clearFocus();
    	}
    }
    
    private void initChangeDateButton() {
 	   Button changeDate = (Button) findViewById(R.id.btnBirthday);
 	   changeDate.setOnClickListener(new OnClickListener() {
 		   
 		   @Override
 		   public void onClick(View v) {
 			   FragmentManager fm = getSupportFragmentManager();
 			   DatePickerDialog datePickerDialog = new DatePickerDialog();
 			   datePickerDialog.show(fm,  "DatePick");
 		   }
 	   });
    }
    
    
    private void hideKeyboard() {
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	EditText editName = (EditText) findViewById(R.id.editName);
    	imm.hideSoftInputFromWindow(editName.getWindowToken(),  0);
    	EditText editBFF = (EditText) findViewById(R.id.editBFF);
    	imm.hideSoftInputFromWindow(editBFF.getWindowToken(),  0);
    	EditText editAddress = (EditText) findViewById(R.id.editAddress);
    	imm.hideSoftInputFromWindow(editAddress.getWindowToken(),  0);
    	EditText editCity = (EditText) findViewById(R.id.editCity);
    	imm.hideSoftInputFromWindow(editCity.getWindowToken(),  0);
    	EditText editState = (EditText) findViewById(R.id.editState);
    	imm.hideSoftInputFromWindow(editState.getWindowToken(),  0);
    	EditText editZipcode = (EditText) findViewById(R.id.editZipcode);
    	imm.hideSoftInputFromWindow(editZipcode.getWindowToken(),  0);
    	EditText editPhone = (EditText) findViewById(R.id.editHome);
    	imm.hideSoftInputFromWindow(editPhone.getWindowToken(),  0);
    	EditText editCell = (EditText) findViewById(R.id.editCell);
    	imm.hideSoftInputFromWindow(editCell.getWindowToken(),  0);
    	EditText editEmail = (EditText) findViewById(R.id.editEMail);
    	imm.hideSoftInputFromWindow(editEmail.getWindowToken(),  0);
    }

	@Override
	public void didFinishDatePickerDialog(Time selectedTime) {
		TextView birthDay = (TextView) findViewById(R.id.textBirthday);
				birthDay.setText(DateFormat.format("MM/dd/yyyy", selectedTime.toMillis(false)).toString());
		currentContact.setBirthday(selectedTime);
		
	}

}
