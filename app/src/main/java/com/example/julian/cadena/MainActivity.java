package com.example.julian.cadena;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    private final String NAMESPACE = "http://sw.arqui/";
    private String URL="http://192.168.1.33:8080/WebService/Compara?WSDL";
    private final String SOAP_ACTION = "192.168.1.33:8080/WebService/";
    String METHOD_NAME="comparar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comparar("hola","hola2");
    }

    public void comparar(final String texto1,final String texto2){

        Thread networkThread = new Thread() {

            @Override public void run() {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("texto1",texto1);
                request.addProperty("texto2",texto2);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                try {
                    androidHttpTransport.call(SOAP_ACTION, envelope); SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
                    final String str = response.toString();
                    runOnUiThread (new Runnable(){ public void run() {

                        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();

                    }});
                }catch (Exception e) {e.printStackTrace();}
            }};
        networkThread.start();
    }
}
