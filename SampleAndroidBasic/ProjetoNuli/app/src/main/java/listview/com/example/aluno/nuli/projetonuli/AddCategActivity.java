package listview.com.example.aluno.nuli.projetonuli;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import listview.com.example.aluno.nuli.projetonuli.modelo.Categoria;
import listview.com.example.aluno.nuli.projetonuli.modelo.CategoriaDAO;

public class AddCategActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categ);


//        btSalvar.setOnClickListener(inserirCategoria());
    }

    private View.OnClickListener inserirCategoria() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edtNomeCateg = (EditText) findViewById(R.id.edtNomeCategoria);
                //Armazenar os valores em um objeto do tipo Usuario
                Categoria cat = new Categoria();
                cat.setNome(edtNomeCateg.getText().toString());

                //Solicitar a inserção do registro por meio do DAO
                CategoriaDAO dao = new CategoriaDAO(getContexto());
                if (dao.inserir(cat) < 0)
                    Toast.makeText(getContexto(), "NÃO FOI POSSÍVEL INSERIR A CATEGORIA", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getContexto(), "CATEGORIA CADASTRADA", Toast.LENGTH_LONG).show();

                }
            }
        };
    }
    private Context getContexto(){
        return this;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_categ, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
