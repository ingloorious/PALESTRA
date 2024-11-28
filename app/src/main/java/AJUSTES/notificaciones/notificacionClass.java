package AJUSTES.notificaciones;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.example.gimnasio.R;


public class notificacionClass extends Fragment {

    Toolbar barraSup ;

    Switch notificaciones;


    public notificacionClass() {
        // Required empty public constructor
    }


    public static notificacionClass newInstance(String param1, String param2) {
        notificacionClass fragment = new notificacionClass();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificaciones,container, false);
        barraSup = view.findViewById(R.id.toolbar);
        notificaciones = view.findViewById(R.id.switchNotis);

        notificaciones.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                abrirConfiguracionNotificaciones(getContext());
            }
        });





        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(barraSup);

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle("Notificaciones");
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

            // meter icono de la flecha para ir atras
            Drawable arrowDrawable = ContextCompat.getDrawable(activity, R.drawable.ic_flecha);
            activity.getSupportActionBar().setHomeAsUpIndicator(arrowDrawable);
        }

        //volver atras
        barraSup.setNavigationOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        return view;
    }

    private void abrirConfiguracionNotificaciones(Context context) {
        Intent intent = new Intent();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android 8 y superior
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else {
            //anteriores a Android 8
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
        }

        startActivity(intent);
    }

}