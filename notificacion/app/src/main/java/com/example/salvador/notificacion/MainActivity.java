package com.example.salvador.notificacion;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    protected CalendarView calendar;


    protected ListView assignments;

    protected Calendar c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = (CalendarView)findViewById(R.id.calendarView1);
        assignments = (ListView) findViewById(R.id.AssignListView);
        consultaBD(Calendar.getInstance().getTime().getDate(),Calendar.getInstance().getTime().getMonth());


        initListener();
    }
    public void initListener()
    {
        calendar.setOnDateChangeListener(new OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(CalendarView view,
                                            int year, int month, int dayOfMonth) {

                consultaBD(dayOfMonth,month);

                ;}});
    }
    public void consultaBD(Integer dayOfMonth,Integer month){
        AdminSQLiteOpenHelper dbHandler;
        dbHandler = new AdminSQLiteOpenHelper(MainActivity.this, null, null, 1);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = dbHandler.listarpersonas(String.valueOf(dayOfMonth),month);
        String[] from = new String[]{"hora", "titulo","descripcion"};
        int[] to = new int[]{
                R.id.txtHora,
                R.id.txtNom,
                R.id.txtDes
        };


        final ListView lvlitems = (ListView) findViewById(R.id.AssignListView);
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(MainActivity.this, R.layout.formato_fila, cursor, from, to);


        lvlitems.setAdapter(cursorAdapter);

    }
    public void alta(View v) {
        AdminSQLiteOpenHelper dbHandler;
        dbHandler = new AdminSQLiteOpenHelper(this, null, null, 1);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        dbHandler.addEvento("Dia normal","Salva","Dia x","Aqui","25 de marzo","11:00");


        Toast.makeText(this, "Se cargaron los datos del artículo",
                Toast.LENGTH_SHORT).show();
    }




}
