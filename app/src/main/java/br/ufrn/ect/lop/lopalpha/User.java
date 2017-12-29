package br.ufrn.ect.lop.lopalpha;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by marcus on 29/12/17.
 */

public class User implements Parcelable {
    private String nome;
    private String email;
    private String matricula;
    //private String[] sigaa;
    //private String[] questoesFavoritas;
    //private String[] listasFavoritas;
    private String idSessao;

    public User() {

    }


    /* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(nome);
        out.writeString(email);
        out.writeString(matricula);
        out.writeString(idSessao);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    User(Parcel in) {
        this.nome = in.readString();
        this.email = in.readString();
        this.matricula = in.readString();
        this.idSessao = in.readString();
    }



    public void setNome(String nome){
        this.nome = nome;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setMatricula(String matricula){
        this.matricula = matricula;
    }
    public void setIdSessao(String idSessao){
        this.idSessao = idSessao;
    }

    public String getNome(){
        return this.nome;
    }
    public String getEmail(){
        return this.email;
    }
    public String getMatricula(){
        return this.matricula;
    }
    public String getIdSessao(){
        return this.idSessao;
    }
}
