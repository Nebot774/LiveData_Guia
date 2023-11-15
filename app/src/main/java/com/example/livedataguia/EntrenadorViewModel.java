package com.example.livedataguia;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.livedataguia.Entrenador;
import com.example.livedataguia.R;

import kotlin.jvm.functions.Function1;

public class EntrenadorViewModel extends AndroidViewModel {
    Entrenador entrenador;

    LiveData<Integer> ejercicioLiveData;
    LiveData<String> repeticionLiveData;

    public EntrenadorViewModel(@NonNull Application application) {
        super(application);

        entrenador = new Entrenador();

        // Inicialización de ejercicioLiveData
        ejercicioLiveData = Transformations.switchMap(entrenador.ordenLiveData, new Function1<String, LiveData<Integer>>() {
            String ejercicioAnterior;

            @Override
            public LiveData<Integer> invoke(String orden) {
                String ejercicio = orden.split(":")[0];



                if (!ejercicio.equals(ejercicioAnterior)) {
                    ejercicioAnterior = ejercicio;
                    int imagen = R.drawable.e1;
                    // Tu lógica de switch aquí...
                    return new MutableLiveData<>(imagen);
                }
                return new MutableLiveData<>(); // Retorna un LiveData vacío en lugar de null
            }
        });

        // Inicialización de repeticionLiveData
        repeticionLiveData = Transformations.map(entrenador.ordenLiveData, new Function1<String, String>() {
            @Override
            public String invoke(String orden) {
                // Tu lógica aquí...
                return orden.split(":")[1]; // Ejemplo
            }
        });
    }

    LiveData<Integer> obtenerEjercicio() {
        return ejercicioLiveData;
    }

    LiveData<String> obtenerRepeticion() {
        return repeticionLiveData;
    }
}

