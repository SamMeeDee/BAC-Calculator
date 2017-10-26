package com.example.samd.baccalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DecimalFormat;

public class MainPage extends AppCompatActivity {

    Editable weight_raw;
    int weight;
    boolean gender;
    int drinkSize;
    double alcPercentage;
    double bacDrinkLevel;
    double totalBAC;
    int totalBACConv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        final TextView percentDyn = (TextView)findViewById(R.id.percent_dyn);
        SeekBar alcPercentInput = (SeekBar)findViewById(R.id.alc_percent_input);
        alcPercentInput.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                Log.d("demo", "prog "+progress);
                double tmp = (double)progress;
                alcPercentage = tmp/100;
                percentDyn.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }


    public void saveHandler (View view)
    {
        ToggleButton gender_input = (ToggleButton)findViewById(R.id.gender);
        EditText weight_input = (EditText)findViewById(R.id.weight_input);

        if(weight_input.getText()!=null)
        {
            weight_raw = weight_input.getText();
            weight = Integer.parseInt(weight_raw.toString());
        }
        else if(weight_input.getText()==null){weight_input.setError("Enter the weight in lbs");}

        gender = gender_input.isChecked();

        if(totalBAC > 0){this.calculation();}
    }

    public void addDrinkHandler (View view)
    {
        RadioGroup drinkSizeInput = (RadioGroup)findViewById(R.id.drink_size_group);
        TextView drinkSizeLabel = (TextView)findViewById(R.id.drin_size);

        if(drinkSizeInput.getCheckedRadioButtonId()==R.id.one)
        {
            drinkSize=1;
            drinkSizeLabel.setError(null);
        }
        else if(drinkSizeInput.getCheckedRadioButtonId()==R.id.five)
        {
            drinkSize=5;
            drinkSizeLabel.setError(null);
        }
        else if(drinkSizeInput.getCheckedRadioButtonId()==R.id.twelve)
        {
            drinkSize=12;
            drinkSizeLabel.setError(null);
        }
        else if(drinkSizeInput.getCheckedRadioButtonId()==-1){drinkSizeLabel.setError("Please select a drink size");}
        Log.d("demo", "drinksize="+drinkSize);

        this.calculation();
    }

    public void calculation()
    {
        Log.d("demo","calc fired");
        ProgressBar bacLevelBar = (ProgressBar)findViewById(R.id.bacLevelBar);
        TextView status = (TextView)findViewById(R.id.status);
        TextView bacLevelLabel = (TextView)findViewById(R.id.bacLevelDyn);
        Log.d("demo",gender+" "+drinkSize+" "+alcPercentage+" "+weight);
        if (!gender) //Male
        {
            bacDrinkLevel = (((drinkSize*alcPercentage)*6.24)/(weight*.68));
        }
        else //Female
        {
            bacDrinkLevel = (((drinkSize*alcPercentage)*6.24)/(weight*.55));
        }

        Log.d("demo","bacDrinkLevel: "+bacDrinkLevel);

        totalBAC += bacDrinkLevel;

        Log.d("demo","totalBAC:"+totalBAC);

        double tmp = totalBAC*100;

        Log.d("demo","tmp: "+tmp);

        totalBACConv = (int)tmp;

        Log.d("demo","totalBACConv:"+totalBACConv);

        DecimalFormat df = new DecimalFormat("#.##");

        if(totalBACConv  < 25)
        {
            bacLevelBar.setProgress(totalBACConv);
            bacLevelLabel.setText(df.format(totalBAC));

            if (totalBACConv <= 8)
            {
                status.setText(R.string.statusGreen);
                status.setBackgroundResource(R.color.statusGreen);
            }
            else if (totalBACConv > 8 | totalBACConv < 20)
            {
                status.setText(R.string.statusOrange);
                status.setBackgroundResource(R.color.statusOrange);
            }
            else
            {
                Log.d("demo","DANGER WILL ROBINSON");
                status.setText(R.string.statusRed);
                status.setBackgroundResource(R.color.statusRed);
            }
        }
        else if(totalBACConv >= 25){this.lockdown();}

    }

    public void lockdown()
    {
        SeekBar alcPercentInput = (SeekBar)findViewById(R.id.alc_percent_input);
        TextView percentDyn = (TextView)findViewById(R.id.percent_dyn);
        TextView per = (TextView)findViewById(R.id.per);
        ToggleButton gender_input = (ToggleButton)findViewById(R.id.gender);
        TextView weight = (TextView)findViewById(R.id.Weight);
        EditText weight_input = (EditText)findViewById(R.id.weight_input);
        TextView alc_percent_label = (TextView)findViewById(R.id.alc_percent_label);
        RadioGroup drinkSizeInput = (RadioGroup)findViewById(R.id.drink_size_group);
        ProgressBar bacLevelBar = (ProgressBar)findViewById(R.id.bacLevelBar);
        TextView status = (TextView)findViewById(R.id.status);
        TextView bacLevelLabel = (TextView)findViewById(R.id.bacLevelDyn);
        TextView drinkSizeLabel = (TextView)findViewById(R.id.drin_size);
        Button save = (Button)findViewById(R.id.save);
        Button add_drink = (Button)findViewById(R.id.add_drink);
        TextView bac_level = (TextView)findViewById(R.id.bac_level);
        TextView yourstatus = (TextView)findViewById(R.id.yourStatus);
        TextView lockdown = (TextView)findViewById(R.id.lockdown_msg);

        alcPercentInput.setVisibility(View.INVISIBLE);
        percentDyn.setVisibility(View.INVISIBLE);
        per.setVisibility(View.INVISIBLE);
        gender_input.setVisibility(View.INVISIBLE);
        weight.setVisibility(View.INVISIBLE);
        weight_input.setVisibility(View.INVISIBLE);
        alc_percent_label.setVisibility(View.INVISIBLE);
        drinkSizeInput.setVisibility(View.INVISIBLE);
        bacLevelBar.setVisibility(View.INVISIBLE);
        status.setVisibility(View.INVISIBLE);
        bacLevelLabel.setVisibility(View.INVISIBLE);
        drinkSizeLabel.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        add_drink.setVisibility(View.INVISIBLE);
        bac_level.setVisibility(View.INVISIBLE);
        yourstatus.setVisibility(View.INVISIBLE);

        lockdown.setVisibility(View.VISIBLE);
    }


    public void resetHandler (View view)
    {
        SeekBar alcPercentInput = (SeekBar)findViewById(R.id.alc_percent_input);
        TextView percentDyn = (TextView)findViewById(R.id.percent_dyn);
        TextView per = (TextView)findViewById(R.id.per);
        ToggleButton gender_input = (ToggleButton)findViewById(R.id.gender);
        TextView weightLabel = (TextView)findViewById(R.id.Weight);
        EditText weight_input = (EditText)findViewById(R.id.weight_input);
        TextView alc_percent_label = (TextView)findViewById(R.id.alc_percent_label);
        RadioGroup drinkSizeInput = (RadioGroup)findViewById(R.id.drink_size_group);
        ProgressBar bacLevelBar = (ProgressBar)findViewById(R.id.bacLevelBar);
        TextView status = (TextView)findViewById(R.id.status);
        TextView bacLevelLabel = (TextView)findViewById(R.id.bacLevelDyn);
        TextView drinkSizeLabel = (TextView)findViewById(R.id.drin_size);
        Button save = (Button)findViewById(R.id.save);
        Button add_drink = (Button)findViewById(R.id.add_drink);
        TextView bac_level = (TextView)findViewById(R.id.bac_level);
        TextView yourstatus = (TextView)findViewById(R.id.yourStatus);
        TextView lockdown = (TextView)findViewById(R.id.lockdown_msg);

        alcPercentInput.setVisibility(View.VISIBLE);
        percentDyn.setVisibility(View.VISIBLE);
        per.setVisibility(View.VISIBLE);
        gender_input.setVisibility(View.VISIBLE);
        weightLabel.setVisibility(View.VISIBLE);
        weight_input.setVisibility(View.VISIBLE);
        alc_percent_label.setVisibility(View.VISIBLE);
        drinkSizeInput.setVisibility(View.VISIBLE);
        bacLevelBar.setVisibility(View.VISIBLE);
        status.setVisibility(View.VISIBLE);
        bacLevelLabel.setVisibility(View.VISIBLE);
        drinkSizeLabel.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        add_drink.setVisibility(View.VISIBLE);
        bac_level.setVisibility(View.VISIBLE);
        yourstatus.setVisibility(View.VISIBLE);
        lockdown.setVisibility(View.INVISIBLE);

        weight_raw=null;
        weight=0;
        gender=false;
        drinkSize=0;
        alcPercentage=0;
        bacDrinkLevel=0;
        totalBAC=0;

        gender_input.setChecked(false);
        weight_input.getText().clear();
        weight_input.setError(null);
        drinkSizeInput.clearCheck();
        alcPercentInput.setProgress(5);
        bacLevelLabel.setText(R.string.bacLevelDefault);
        bacLevelBar.setProgress(0);
        percentDyn.setText("5");
        status.setText(R.string.statusGreen);
        status.setBackgroundResource(R.color.statusGreen);
        drinkSizeLabel.setError(null);
    }
}
