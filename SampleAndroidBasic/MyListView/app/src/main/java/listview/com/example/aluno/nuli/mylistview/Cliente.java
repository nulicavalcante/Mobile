package listview.com.example.aluno.nuli.mylistview;

/**
 * Created by aluno on 11/11/2015.
 */
public class Cliente {
   private int id;
   private String nome;
   private String telefone;

    public Cliente(int id, String nome, String telefone){
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
