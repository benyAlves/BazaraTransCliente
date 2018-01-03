package org.bazara.saudigitus.bazaratranscliente.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dalves on 12/13/17.
 */

public class Transportador implements Parcelable {

    private String id;
    private String nome;
    private String telefone;


    public Transportador(Parcel in) {
        id = in.readString();
        nome = in.readString();
        telefone = in.readString();
    }

    public Transportador() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nome);
        dest.writeString(telefone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Transportador> CREATOR = new Creator<Transportador>() {
        @Override
        public Transportador createFromParcel(Parcel in) {
            return new Transportador(in);
        }

        @Override
        public Transportador[] newArray(int size) {
            return new Transportador[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


}
