package wordeasy.br.com.wordeasy.activity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import org.w3c.dom.Text;

import java.util.Calendar;

import wordeasy.br.com.wordeasy.util.Mensagem;

public class DateDialog  implements
             DatePickerDialog.OnDateSetListener,
             com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener{

    private Context context;
    private int year,monthOfYear,dayOfMonth;
    private Calendar now;
    private Activity activity;
    private String dateSelecionada;
    TextView  view;

    public DateDialog(Context context, Activity activity){
        this.context = context;
        this.activity = activity;

        now  =  Calendar.getInstance();

    }

    public DatePickerDialog createDateDialog() {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        return dpd;
    }


    private void setValueDate(String dateSelecionada) {
        view.setText(dateSelecionada);
    }

    private String getValuedate() {
        return  view.getText().toString();
    }

    public void clearLembrete() {
        this.year = 0;
        this.monthOfYear = 0;
        this.dayOfMonth = 0;
        now.clear();
    }


    public void createOnlyTimerPickerDialog(View view) {

       this.view =(TextView) view;

       //TIMER
       com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
               this,
               now.get(Calendar.HOUR_OF_DAY),
               now.get(Calendar.MINUTE),
               now.get(Calendar.SECOND),
               false
       );
       tpd.show(activity.getFragmentManager(), "timerDialog");
   }


    //Listener do datePickerDialog
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        int mes = monthOfYear + 1;

       //TIMER
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                false
        );
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = mes;
        this.year = year;
        tpd.show(activity.getFragmentManager(), "timerDialog");
    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        //dateSelecionada =  dayOfMonth + "/" + monthOfYear + "/" + year + " " + hourOfDay + ":" + minute;
        dateSelecionada = hourOfDay+":"+minute;
        setValueDate(dateSelecionada);
    }
}
