package br.ufrn.ect.lop.lopalpha;

/**
 * Created by marcus on 31/12/17.
 */

public class Questao {
    private String _id;
    private String titulo;
    private String dificuldade;
    private String enunciado;

    public Questao(String _id, String titulo, String dificuldade, String enunciado){
        this._id = _id;
        this.titulo = titulo;
        this.dificuldade = dificuldade;
        this.enunciado = enunciado;
    }
    public void set(String _id, String titulo, String dificuldade, String enunciado){
        this._id = _id;
        this.titulo = titulo;
        this.dificuldade = dificuldade;
        this.enunciado = enunciado;
    }
    public Questao(){

    }
    public String getId(){
        return this._id;
    }
    public String getTitulo(){
        return this.titulo;
    }
    public String getDificuldade(){
        return this.dificuldade;
    }
    public String getEnunciado(){
        return this.enunciado;
    }
}
